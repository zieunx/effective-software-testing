# 이펙티브 소프트웨어 테스팅


![](https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791192469744.jpg)


# 2장 명세 기반 테스트

### 2.4 현업에서의 명세 테스트

명세 테스트는 어느정도로 수행해야 하는가?

- 실패의 위험성을 이해해야 함. 실패하면 발생하는 비용은? 비용이 높으면 테스트에 많이 투자 필요.

# 3장 구조적 테스트와 코드 커버리지

## 3.4.2 MC/DC를 달성하는 테스트 스위트 작성

MC: 변형 조건 (Modified Condition)

DC: 결정 커버리지 (Decision Coverage)

MC/DC는 커버리지 기준 중 하나. 각 조건이 전체 결정(결과)에 어떻게 영향을 미치는지 검증.

> ### 조건
> 📏 A && (B || C)

위의 조건이 있을 때, 각 조건은 true/false 두 가지로 표현할 수 있기 때문에 총 2의 3승개로 8개가 나온다.

| 테스트 케이스 | isLetter | last == s | last == r | 판정 |
| --- | --- | --- | --- | --- |
| T1 | true | true | true | true |
| T2 | true | true | false | true |
| T3 | true | false | true | true |
| T4 | true | false | false | false |
| T5 | false | true | true | false |
| T6 | false | true | false | false |
| T7 | false | false | true | false |
| T8 | false | false | false | false |

MC/DC 달성 조건은 **각 조건이 결과에 독립적으로 영향을 미치는지** 확인하는 것이다.

예를 들어, A 조건은 바꾸고 B와 C의 조건은 고정하고 판정(결과)가 바뀌는 경우가 해당된다.

> A의 조건이 결과에 독립적인 영향을 끼치는 경우
> 

| 테스트 케이스 | isLetter | last == s | last == r | 판정 | 구분 |
| --- | --- | --- | --- | --- | --- |
| T1 | **true** | true | true | **true** | 🍎 |
| T2 | **true** | true | false | **true** | 🍌 |
| T3 | **true** | false | true | **true** | 🥦 |
| T4 | true | false | false | false |  |
| T5 | **false** | true | true | **false** | 🍎 |
| T6 | **false** | true | false | **false** | 🍌 |
| T7 | **false** | false | true | **false** | 🥦 |
| T8 | false | false | false | false |  |

> B의 조건이 결과에 독립적인 영향을 끼치는 경우
> 

| 테스트 케이스 | isLetter | last == s | last == r | 판정 | 구분 |
| --- | --- | --- | --- | --- | --- |
| T1 | true | true | true | true |  |
| T2 | true | **true** | false | **true** | 🫐 |
| T3 | true | false | true | true |  |
| T4 | true | **false** | false | **false** | 🫐 |
| T5 | false | true | true | false |  |
| T6 | false | true | false | false |  |
| T7 | false | false | true | false |  |
| T8 | false | false | false | false |  |

> C의 조건이 결과에 독립적인 영향을 끼치는 경우
> 

| 테스트 케이스 | isLetter | last == s | last == r | 판정 | 구분 |
| --- | --- | --- | --- | --- | --- |
| T1 | true | true | true | true |  |
| T2 | true | true | false | true |  |
| T3 | true | false | **true** | **true** | 🥦 |
| T4 | true | false | **false** | **false** | 🥦 |
| T5 | false | true | true | false |  |
| T6 | false | true | false | false |  |
| T7 | false | false | true | false |  |
| T8 | false | false | false | false |  |


정리해보면,

- A: 1+5, 2+6, 3+7
- B: 2+4
- C: 3+4

각 변수마다 쌍이 하나만 있으면 충분하다. 즉, B와 C의 2,3,4 테스트케이스는 꼭 필요하다고 판단하는거다.

A에서 독립쌍을 구할 때는 테스트케이스를 줄일 수 있도록 이미 필요하다고 판단한 2,3,4 중 포함된 것을 고르는것이 효율적으로 판단된다. 2+6, 3+7 중 한 가지를 고르면 N+1개의 테스트케이스만으로 꼭 필요한 테스트케이스를 구현할 수 있다.

| 테스트 케이스 | isLetter | last == s | last == r | 판정 |
| --- | --- | --- | --- | --- |
| T1 | true | true | true | true |
| T2 | true | true | false | true |
| T3 | true | false | true | true |
| T4 | true | false | false | false |
| T5 | false | true | true | false |
| T6 | false | true | false | false |
| T7 | false | false | true | false |
| T8 | false | false | false | false |

## 3.10 현업에서의 구조적 테스트

### 3.10.1 왜 사람들은 코드 커버리지를 싫어할까?

‘단언문 없이 테스트 케이스만 작성해도 커버리지를 100% 달성할 수 있어요. 하지만 아무것도 테스트하지는 않죠!’

필자는 이는 최악의 시나리오라고 표현.

사람들은 커버리지 숫자를 무작정 보면 안된다고 한다.

전적으로 동감하지만, **코드 커버리지를 바라보는 방식**에 문제가 있다고 판단.

구조적 테스트와 코드 커버리지 과정을 통해 명세를 강화하고 테스트 스위트가 수행하지 않는 코드 부분을 재빨리 찾아내며, 명세 기반 테스트를 수행할 때 놓쳤던 구획을 찾을 수 있다.

코드 줄을 수행하지 않고 내버려 두었다는 것은 다른 의미로 수행할 지 고민 후 수행하지 않기로 결정했다는 뜻이기도 함.

→ 정리하자면, 쉽게 말해 테스트 과정에서 구멍난 부분을 채울 수 있는 좋은 수단.

코드 커버리지를 지지하는 증거 및 주장들

- 허찬스(Hutchins)와 동료들의연구: 90% 이상 커버리지를 달성한 테스트는 무작위로 선택한 비슷한 크기의 테스트에 비해 훨씬 나은 에러 방지 효과가 있음. 그 효과는 90%에서 100%으로 증가할 때 더 크게 개선됨. 그치만 무작정 100%를 달성하는 것에는 큰 의미가 없음.

### 3.10.4 표현식이 너무 복잡해서 단순화할 수 없다면 MC/DC 를 고려하자

### 3.10.6 무엇을 수행하지 말아야 할까?

```java
public static String resourceFolder(String path) {
    try {
        return Paths.get(ResourceUtils.class.getResource("/").toURI()).toString() + path;
    } catch (URISyntaxException e) {
        throw new RuntimeException(e);
    }
}
```

위의 코드는 checked exception을 감싸서 unchecked exception 으로 던지는 메소드임.

이 메소드의 예외를 일부러 터트려서 테스트함으로써 우리가 가져가는 이점이 명확하지 않다.

차라리 `resourceFolder` 가 `RuntimeException` 를 던졌을 때 시스템에 끼치는 영향을 테스트 하는 것이 더 의미가 있겠음.

작가의 의견:

코드 커버리지를 100%를 목표로 시작하되, 불필요한 부분이 보이면 예외를 두는 방식이 버그를 막기에 더 의미있다고 생각.

## 3.11 돌연변이 테스트

https://pitest.org/

자바에서 돌연변이  테스트를 수행해주는 도구

테스트 도구는 코드를 돌연변이로 바꾸기만 할 뿐이라, 돌연변이 중에 불필요한 돌연변이를 걸러내야함

# 4. 계약 설계

## 4.1 사전 조건과 사후 조건

- 사전조건: 메서드가 제대로 실행되기 위한 조건
    - 예) 입력인자가 양수여야한다, DB 커넥션이 정상적으로 되어있어야한다, http connection이 되어야 한다 등..
- 사후조건: 메서드가 제대로 결과를 수행한 결과
    - 예) 특정테이블에 로우가 1개 추가된다, 리턴값이 양수여야한다, DB에 저장된 값이 리턴된다 등…

저자는 일단 무조건 문서로 기제해둬야한다고 함.

자바로 치면 메서드 위 자바독 형식으로 @param과 @return을 작성.

경우에 따라, 무조건 예외를 던지지 않고 어느정도 허용할 수 있다. 

예외를 던져서 흐름을 중단시키는 것을 ‘강한 조건’, 의도한 결과가 안 날수도 있지만 허용하고보는 ‘약한 조건’

예외를 내기도, 그냥 흘러보내기도 애매하다면 기본값 혹은 에러값을 리턴하는것도 방법

### 4.2 불변식

사전 조건과 사후 조건을 구현하고 나면, 절대 변하지 않는 조건이 생긴다.

예를들어, 상품을 장바구니에 넣고 빼도 총액은 음수가 될 수 없다.

이 변하지 않는 조건을 불변식 이라고 함.

상품을 넣고 빼는 각 메서드 마지막에 총액은 음수가 될 수 없음을 추가하면 불변식 검증 가능

### 4.3 계약 변경과 리스코프 치환 법칙

**계약은 약화(느슨)되면 영향을 끼치지 않지만, 강화 되면 영향을 끼칠 수 있다.**

예) 만약 value ≥ 0 조건의 사전 조건이 value ≥ 100 조건으로 변경된다면?

해당 함수로 50이나 0값을 넘기던 연관 클래스입장에서는 예상치 못한 예외가 발생할 수 있다.

나의 느낀점: 처음부터 강한 제약을 걸고 요구사항이 변경되거나 명확해 질 때 약화시키는 방법이 좋을 수 있겠다.

### 4.3.1 상속

자바에서 상속했다고 가정했을 때,

자식 클래스는 부모 클래스의 사전 조건보다 같거나 약해야 함. (더 넓은 데이터 수용)

자식 클래스는 부모클래스의 사후 조건보다 같거나 강해야 함. (더 한정된 데이터 리턴)

클라이언트는 부모 클래스를 기준으로 사전 조건과 사후 조건을 알 수 있기 때문이다.

리스코프 치환 법칙 = 시스템에 기대하는 동작을 깨트리지 않고 자식 클래스를 부모 클래스로 치환할 수 있는 개념

## 4.4 계약에 의한 설계가 테스트와 어떤 관련이 있는가?

1. 단언문을 통해 제품 코드에서 버그를 일찍 발견할 수 있다.
2. 사전 조건, 사후 조건, 불변식은 개발자에게 테스트 대상을 제공한다.
3. 명시적인 계약은 소비자가 편함.
