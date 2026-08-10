# SE-0313: 액터 격리(Actor Isolation) 제어 개선

- **제안**: [SE-0313](https://github.com/swiftlang/swift-evolution/blob/main/proposals/0313-actor-isolation-control.md)
- **작성자**: Doug Gregor, Chris Lattner
- **리뷰 매니저**: Ted Kremenek
- **상태**: 구현 완료 (Swift 5.5)

---

## 목차

1. [개요](#1-개요)
2. [동기](#2-동기)
3. [제안 내용](#3-제안-내용)
   - 3.1 [`isolated` 파라미터](#31-isolated-파라미터)
   - 3.2 [`nonisolated` 선언](#32-nonisolated-선언)
   - 3.3 [프로토콜 준수](#33-프로토콜-준수)
   - 3.4 [async 이전 비동기 프로토콜 대응](#34-async-이전-비동기-프로토콜-대응)
4. [호환성 및 ABI/API 영향](#4-호환성-및-abiapi-영향)
5. [향후 방향](#5-향후-방향)
6. [고려했던 대안](#6-고려했던-대안)
7. [변경 이력](#7-변경-이력)

---

## 1. 개요

[Swift actors 제안](https://github.com/swiftlang/swift-evolution/blob/main/proposals/0306-actors.md)은 **액터에 격리된(actor-isolated)** 선언이라는 개념을 도입했다. 이는 액터의 격리된 상태(isolated state)에 안전하게 접근할 수 있는 선언을 말한다. 기존 제안에서는 액터 타입의 모든 인스턴스 메서드·프로퍼티·서브스크립트가 자동으로 액터에 격리되며, `self`를 통해 동기적으로 사용할 수 있었다.

이 제안(SE-0313)은 이 개념을 일반화하여 다음을 가능하게 한다.

- 액터 타입의 멤버가 아니어도(예: 최상위 함수) 액터에 격리된 선언을 만들 수 있다.
- 액터 타입의 인스턴스 멤버라도 격리되지 않은(non-isolated) 선언으로 만들 수 있다.

이를 통해 액터 사용을 더 잘 추상화하고, 기존 시스템에서는 안전하게 표현할 수 없었던 연산들을 추가하며, 기존의 동기 프로토콜에 대한 일부 준수(conformance)를 가능하게 한다.

---

## 2. 동기

액터 제안에서 예로 든 `BankAccount` 액터에는 불변 상태와 가변 상태가 섞여 있다.

```swift
actor BankAccount {
  let accountNumber: Int
  var balance: Double

  init(accountNumber: Int, initialDeposit: Double) {
    self.accountNumber = accountNumber
    self.balance = initialDeposit
  }

  func deposit(amount: Double) {
    assert(amount >= 0)
    balance = balance + amount
  }
}
```

이 구조에서는 다음과 같은, 얼핏 당연해 보이는 것들이 불가능하다.

- `deposit(amount:)` 같은 연산을 전역 함수로 분리할 수 없다. 반드시 액터의 멤버로만 작성해야 한다.
- 액터 외부에서 동기적으로 사용 가능한, 계좌 표시용 편의 계산 프로퍼티를 작성할 수 없다.
- `BankAccount`가 `Hashable`을 준수할 방법이 없으므로 `Set<BankAccount>`를 만들 수 없다.

---

## 3. 제안 내용

위 한계들은 모두 액터 타입의 인스턴스 메서드(프로퍼티·서브스크립트 포함)가 **항상** 액터에 격리되어 있다는 점에서 비롯된다. 다른 함수는 액터에 격리될 수 없고, 인스턴스 선언을 격리 해제할 방법도 없다.

이 제안은 액터-격리 함수의 개념을 일반화하여, **어떤 함수든 자신의 액터 파라미터 중 하나를 격리 대상으로 지정**함으로써 액터에 격리될 수 있게 하고, 반대로 **액터의 인스턴스 선언을 아예 격리되지 않게** 만들 수도 있게 한다.

### 3.1 `isolated` 파라미터

함수는 파라미터 중 하나를 `isolated`로 지정함으로써 액터에 격리될 수 있다. 예를 들어 `deposit(amount:)`를 모듈 스코프 함수로 다시 쓰면 다음과 같다.

```swift
func deposit(amount: Double, to account: isolated BankAccount) {
  assert(amount >= 0)
  account.balance = account.balance + amount
}
```

`account` 파라미터가 `isolated`이므로 `deposit(amount:to:)`는 그 `account`에 대해 액터-격리 상태이며, 해당 파라미터의 액터-격리 상태에 직접 접근할 수 있다. 격리 규칙은 기존과 동일하게 적용된다.

```swift
extension BankAccount {
  func giveSomeGetSome(amount: Double, friend: BankAccount) async {
    deposit(amount: amount, to: self)         // self가 격리되어 있으므로 동기 호출 가능
    await deposit(amount: amount, to: friend) // friend는 격리되어 있지 않으므로 비동기 호출 필요
  }
}
```

이로써 액터 타입의 인스턴스 메서드는 더 이상 특별한 존재가 아니게 된다. 사실은 **`self` 파라미터가 `isolated`로 지정된 일반적인 함수**로 표현 가능하다는 것이, 커리 형태의 타입을 보면 드러난다.

```swift
let fn = BankAccount.deposit(amount:)   // fn의 타입: (isolated BankAccount) -> (Double) -> Void
```

단, 하나의 함수는 `isolated` 파라미터를 **두 개 이상 가질 수 없다.**

```swift
func f(a: isolated BankAccount, b: isolated BankAccount) {  // 오류: 함수 f(a:b:)에 isolated 파라미터가 여러 개 있음
  // ...
}

extension BankAccount {
  func quickTransfer(amount: Double, to other: isolated BankAccount) {  // 오류: quickTransfer(amount:to:)에 isolated 파라미터가 여러 개 있음 (self도 암묵적으로 isolated)
    // ...
  }
}
```

### 3.2 `nonisolated` 선언

액터 타입의 인스턴스 선언은 암묵적으로 `isolated self`를 가진다. 하지만 `nonisolated` 키워드로 이 암묵적 동작을 끌 수 있다.

```swift
actor BankAccount {
  nonisolated let accountNumber: Int
  var balance: Double
  // ...
}

extension BankAccount {
  // 마지막 몇 자리만 남기고 나머지를 "X"로 가린, 문서에 표기해도 안전한 계좌번호 문자열을 생성한다.
  nonisolated func safeAccountNumberDisplayString() -> String {
    let digits = String(accountNumber)   // accountNumber도 nonisolated이므로 접근 가능
    return String(repeating: "X", count: digits.count - 4) + String(digits.suffix(4))
  }
}

let fn2 = BankAccount.safeAccountNumberDisplayString   // fn2의 타입: (BankAccount) -> () -> String
```

`self`가 액터에 격리되어 있지 않으므로, `safeAccountNumberDisplayString`은 격리되지 않은 데이터에만 접근할 수 있다. 격리된 선언에 접근하려 하면 오류가 발생하거나 비동기 접근이 요구된다.

```swift
extension BankAccount {
  nonisolated func steal(amount: Double) {
    balance -= amount  // 오류: 액터-격리 프로퍼티 'balance'는 비격리 파라미터 'self'에서 참조할 수 없음
  }
}
```

또한 `nonisolated` 선언에 관련된 모든 타입은 반드시 `Sendable`이어야 한다. `nonisolated` 선언은 어떤 액터에서든, 또는 동시에 실행되는 코드에서든 사용될 수 있기 때문이다. 예를 들어 `nonisolated` 함수는 `Sendable`이 아닌 클래스를 반환할 수 없다.

```swift
class SomeClass { } // Sendable 아님

extension BankAccount {
  nonisolated func f() -> SomeClass? { nil } // 오류: `nonisolated` 선언이 비-Sendable 타입 `SomeClass?`를 반환함
}
```

### 3.3 프로토콜 준수

액터 제안의 규칙에 따르면, 액터-격리 함수는 격리되지도 비동기이지도 않은 프로토콜 요구사항을 만족시킬 수 없다. 그렇게 허용하면 액터 상태에 동기적으로 접근할 길이 열리기 때문이다. 그러나 **비격리(non-isolated) 함수는 액터 상태에 접근할 수 없으므로**, 어떤 종류의 동기 프로토콜 요구사항이든 자유롭게 만족시킬 수 있다.

예를 들어 계좌번호를 기준으로 해시를 계산하도록 하여 `BankAccount`가 `Hashable`을 준수하게 만들 수 있다.

```swift
extension BankAccount: Hashable {
  nonisolated func hash(into hasher: inout Hasher) {
    hasher.combine(accountNumber)
  }
}

let fn = BankAccount.hash(into:) // 타입: (BankAccount) -> (inout Hasher) -> Void
```

마찬가지로 `nonisolated` 계산 프로퍼티를 이용해 `CustomStringConvertible`을 준수시킬 수도 있다.

```swift
extension BankAccount: CustomStringConvertible {
  nonisolated var description: String {
    "Bank account #\(safeAccountNumberDisplayString())"
  }
}
```

### 3.4 async 이전 비동기 프로토콜 대응

비격리 선언은 완료 핸들러(completion handler) 방식으로 표현된 기존 비동기 프로토콜을 액터에 맞게 변환할 때 특히 유용하다. 예를 들어 완료 핸들러를 사용하는 기존의 간단한 "서버" 프로토콜이 있다고 하자.

```swift
protocol OldServer {
  func send<Message: MessageType>(
    message: Message,
    completionHandler: (Result<Message.Reply>) -> Void
  )
}
```

이 프로토콜은 시간이 지나면서 `async` 요구사항을 제공하도록 진화해야 한다. 하지만 그전에도, 분리된(detached) 태스크를 실행하는 비격리 선언을 이용해 액터 타입이 이 프로토콜을 준수하도록 만들 수 있다.

```swift
actor MyActorServer {
  func send<Message: MessageType>(message: Message) async throws -> Message.Reply { ... }  // 우리가 원하는 "진짜" 비동기 구현
}

extension MyActorServer : OldServer {
  nonisolated func send<Message: MessageType>(
    message: Message,
    completionHandler: (Result<Message.Reply>) -> Void
  ) {
    detach {
      do {
        let reply = try await send(message: message)
        completionHandler(.success(reply))
      } catch {
        completionHandler(.failure(error))
      }
    }
  }
}
```

이를 통해 액터는 코드베이스 전체에 `async`를 먼저 도입하지 않고도, 기존 코드에 더 매끄럽게 통합될 수 있다.

---

## 4. 호환성 및 ABI/API 영향

- **소스 호환성**: 이 제안은 순수하게 추가적(additive)이며, 선언 수식어(declaration modifier)라는 새로운 문맥적 키워드가 흔히 도입되는 영역에서 문법을 확장하는 것이므로 소스 호환성에 영향을 주지 않는다.
- **ABI 안정성**: ABI에도 순수하게 추가적이다. 함수 파라미터에 `isolated`를 표시할 수 있게 되며 이는 함수 타입의 일부로 캡처되지만, (다른 파라미터 수식어와 마찬가지로) 기존 ABI에 영향을 주지 않는 추가적 변경이다.
- **API 유연성(resilience)**: 액터 격리 관련 변경은 거의 대부분 **breaking change**다. 액터 격리 규칙은 선언 간 일관성을 요구하기 때문에, 파라미터를 `isolated`와 비-`isolated`(직접적으로든 `nonisolated`를 통해서든) 사이에서 변경하면 API가 깨진다.

---

## 5. 향후 방향

### 5.1 다중 `isolated` 파라미터

현재 제안은 하나의 함수 선언에 `isolated` 파라미터가 두 개 이상 있는 것을 금지한다. 향후 이 제약을 완화할 수도 있다.

```swift
func f(a: isolated BankAccount, b: isolated BankAccount) {
  // ...
}
```

다만 [기본 액터 제안](https://github.com/swiftlang/swift-evolution/blob/main/proposals/0306-actors.md)에서는 한 번에 하나의 액터에서만 실행될 수 있으므로, 이런 함수를 안전하게 호출하는 방법은 동일한 액터를 두 번 넘기는 경우뿐이다.

```swift
extension BankAccount {
  func g() {
    f(a: self, b: self)
  }

  func h(other: BankAccount) async {
    await f(a: self, b: other) // 오류: isolated 파라미터 a와 b에 서로 다른 액터일 수 있는 값이 전달됨
  }
}
```

포인터 타입의 안전하지 않은 캐스팅 같은 비안전 메커니즘을 쓰면 격리된 서로 다른 두 액터를 넘기는 것도 가능하다. [커스텀 executor 제안](https://forums.swift.org/t/support-custom-executors-in-swift-concurrency/44425)은 액터가 실행되는 동시성 도메인을 제어할 수 있게 하는데, 이를 확장하면 동적으로 두 액터가 같은 동시성 도메인에서 실행됨을 보장하거나, 정적으로 특정 액터 집합이 동일한 동시성 도메인을 공유함을 보장하여 `isolated` 파라미터가 여러 개인 함수를 향후 더 유용하게 만들 수도 있다.

### 5.2 격리된 프로토콜 준수 (Isolated protocol conformances)

액터 타입의 프로토콜 준수는 해당 프로토콜의 클라이언트가 액터의 격리 도메인 외부에 있다고 가정한다. 따라서 [프로토콜 준수](#33-프로토콜-준수)는 프로토콜이 `async` 요구사항을 갖거나, 액터가 비격리 멤버를 이용해 준수를 성립시킬 것을 요구한다.

[액터 프로토콜에 대한 타입 시스템 고려사항](https://forums.swift.org/t/exploration-type-system-considerations-for-actor-proposal/44540) 피치는 액터 타입이 "이 준수는 오직 액터의 격리 컨텍스트 안에서만 사용된다"는 전제 하에 프로토콜을 준수할 수 있어야 한다고 주장하며 다음 예시를 든다.

```swift
public protocol DataProcessible {
    var data: Data { get }
}
extension DataProcessible {
  func compressData() -> Data {
    use(data)
    /// 세부 구현 생략
  }
}

actor MyDataActor : DataProcessible {
  // 오류: 격리된 액터 멤버로는 동기 요구사항을 만족시킬 수 없음
  var data: Data

  func doThing() {
    // 전부 동기 호출이며 문제 없음!
    let compressed = compressData()
  }
}
```

해당 피치는 `MyDataActor : DataProcessible` 준수를 허용해야 한다고 제안하며, 액터가 자기 자신의 격리 도메인 안에 있을 때를 표현하기 위한 `@sync` 액터 타입 개념을 도입한다. 구체적으로 `@sync MyDataActor` 타입은 `DataProcessible`을 준수하지만, 격리 도메인 밖의 액터를 나타내는 `@async MyDataActor` 타입은 준수하지 않는다.

이 제안(SE-0313)은 격리된 액터 타입과 비격리 액터 타입을 분리하지 않는 대신, 코드가 실행 중인 액터를 나타내기 위해 `isolated` 파라미터를 사용한다. 동일한 개념을 확장하여 **격리된 프로토콜 준수**, 즉 격리된 값에서만 사용할 수 있는 준수를 도입할 수 있다. 예를 들어 준수 자체에 `isolated`를 붙여 격리된 준수임을 표시할 수 있다.

```swift
actor MyDataActor : isolated DataProcessible {
  var data: Data   // 정상: "data" 요구사항을 만족시킴

  func doThing() {
    // self가 격리되어 있으므로 정상 동작
    let compressed = compressData()
  }

  nonisolated failToDoTheThing() {
    // 오류: 격리된 준수 MyDataActor : DataProcessible은 MyDataActor의 비격리 값이
    // 제네릭 함수에 전달될 때 사용할 수 없음
    let compressed = compressData()
  }
}
```

격리된 프로토콜 준수를 사용하려면, 해당 준수가 액터의 비격리 인스턴스에서 사용되지 않도록 보장하기 위한 여러 추가 제약이 필요하다. 예를 들어 비격리 준수는 동일한 타입에서 `Sendable`과 절대 함께 쓰일 수 없다. 그렇지 않으면 액터의 비격리 인스턴스가, 격리 도메인 안에 있다고 가정하는 프로토콜 준수와 함께 격리 도메인 밖으로 전달될 수 있기 때문이다.

---

## 6. 고려했던 대안

### 6.1 격리 또는 동기 액터 타입 (Isolated or sync actor types)

"격리된 파라미터"라는 개념은, 액터 격리를 `self`에만 국한하지 않고 임의의 파라미터로 일반화한 [이전 제안](https://forums.swift.org/t/exploration-type-system-considerations-for-actor-proposal/44540)에서 발전한 것이다. 그 제안은 격리를 타입 시스템에 직접 모델링하여 `@sync` 액터 타입이라는 새로운 종류의 타입을 도입했다. `@sync` 액터 타입은 자신이 나타내는 액터에 동기적으로 접근할 수 있는 값에 사용된다. 즉, `self`가 타입 `MyActor`의 `isolated` 파라미터라고 말하는 대신, `self`가 `@sync MyActor` 타입을 가진다고 말하는 방식이다.

위 5.2절에서 설명한 "격리된 준수"는 이 `@sync` 액터 타입이 (동기) 프로토콜을 준수할 수 있다는 개념과 유사하며, 실제로 그 개념에서 직접적인 영향을 받았다.

전체적으로 보면 격리된 파라미터·격리된 준수는 `@sync` 타입의 파라미터·`@sync` 타입의 프로토콜 준수와 유사하며 비슷한 범위의 사용 사례를 다룰 수 있다. 그럼에도 이 제안은 `isolated`를 타입이 아니라 **파라미터 수식어**로 다루기로 했는데, 이는 `inout`처럼 유사하게 제약된 구성 요소의 동작과 더 잘 맞아떨어지는, 더 단순하고 값 중심적인(value-centric) 모델을 제공하기 때문이다. `@sync` 타입 접근 방식에는 바람직하지 않게 만드는 여러 비일관성이 있었다.

- **중첩 컨텍스트에서 타입이 바뀜**: 클로저 같은 중첩 컨텍스트 안에서는 액터의 `self` 타입이 `@sync`와 비-`@sync` 사이에서 달라질 수 있다.

  ```swift
  func f<T>(_: T) { }

  actor MyActor {
    func g() {
      f(self) // T = @sync MyActor

      asyncDetached {
        f(self) // T = MyActor
      }
    }
  }
  ```

  일반적으로 Swift에서 변수는 중첩 컨텍스트에서 캡처되든 바깥 컨텍스트에 있든 같은 타입을 유지하며, 이것이 예측 가능성을 제공한다. 위 예시에서는 클로저 안팎에 따라 `f` 호출에 대한 타입 추론이 크게 달라진다. [타입 좁히기(narrowing types)에 관한 포럼 논의](https://forums.swift.org/t/implicit-casts-for-verified-type-information/41035)에서도, 보일러플레이트를 줄일 수 있더라도 중첩 컨텍스트에서 변수의 타입을 바꾸는 아이디어에는 반대 의견이 많았다.

- **암묵적 변환에 대한 과도한 의존**: 이 설계는 `@sync MyActor`에서 `MyActor`로의 암묵적 변환에 크게 의존한다.

  ```swift
  func acceptActor(_: MyActor) { }
  func acceptSendable<T: Sendable>(_: T) { }

  extension MyActor {
    func h() {
      acceptActor(h)  // 정상: @sync MyActor에서 MyActor로의 변환 필요
      acceptSendable(h) // 정상: T=MyActor이며 @sync MyActor에서 MyActor로의 변환 필요
    }
  }
  ```

- **`Sendable` 준수가 일반적인 서브타이핑 규칙을 따르지 않음**: 위 변환 규칙에 따르면 `@sync` 액터 타입은 대응하는 (비-`@sync`) 액터 타입의 서브타입이다. 정의상 서브타입은 슈퍼타입의 모든 준수를 가지며, 물론 능력을 더 추가할 수도 있다. 이는 일반적인 타입 시스템 설계 원칙으로, Swift의 서브클래싱에서도 다음과 같이 나타난다.

  ```swift
  protocol P { }

  class C: P { }
  class D: C { }

  func test(c: C, d: D) {
    let _: P = c   // 정상: C는 P를 준수함
    let _: P = d   // 정상: D는 C의 서브타입이고 C가 P를 준수하므로 D도 P를 준수함
  }
  ```

  그러나 `@sync` 타입은 `Sendable`에 대해서는 이렇게 동작하지 않는다. 비-`@sync` 액터 타입은 `Sendable`을 준수하지만(동시성 도메인 간에 공유해도 안전), 그에 대응하는 `@sync` 서브타입은 `Sendable`을 준수하지 **않는다**. 이 때문에 앞의 예시에서 `acceptSendable` 호출 시 `@sync MyActor`에서 `MyActor`로의 암묵적 변환이 필요했던 것이다.

---

## 7. 변경 이력

승인된 버전에서의 변경 사항:

- `isolated` 캡처(capture) 기능 제거.
- 다중 `isolated` 파라미터 금지.

---

### 참고

- 원문: [SE-0313 (swift-evolution repo)](https://github.com/swiftlang/swift-evolution/blob/main/proposals/0313-actor-isolation-control.md)
- 관련 제안: [SE-0306 Actors](https://github.com/swiftlang/swift-evolution/blob/main/proposals/0306-actors.md)
