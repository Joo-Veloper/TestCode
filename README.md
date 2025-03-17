## 🧪 단위 테스트

### **1. 단위 테스트란?**
- 작은 코드 단위를 **독립적으로 검증**하는 테스트
- 주로 **클래스 & 메서드** 단위로 진행
- 외부 네트워크 등 **외부 환경에 의존하지 않는** 것이 중요
- 빠르고 안정적이라는 장점이 있음

### **2. 테스트 방식**
✅ **수동 테스트**: 사람이 직접 확인하는 테스트  
✅ **자동 테스트**: 기계가 최종적으로 검증하는 테스트

### **[3. JUnit 5](https://junit.org/junit5/docs/current/user-guide/)**
- Java 기반 **단위 테스트 프레임워크**
- XUnit 계열 (Kent Beck)
    - SUnit(Smalltalk), JUnit(Java), NUnit(.NET)

### **[4. AssertJ](https://assertj.github.io/doc/)**
- 테스트 코드 작성을 돕는 **테스트 라이브러리**
- 풍부한 API 제공 & **메서드 체이닝 지원** (가독성 향상)

### 📌 **테스트 케이스 세분화**

테스트를 더욱 철저하게 진행하려면, 다양한 **케이스를 분류하고 경계값을 고려**해야 합니다.  
아래와 같이 **해피 케이스**, **예외 케이스**, **경계값 테스트**를 체계적으로 정리할 수 있습니다.

---

### ✅ **1. 해피 케이스 (Happy Case)**
> **요구 사항을 정상적으로 만족하는 경우**  
> 일반적인 흐름에서 기대한 대로 동작하는지 확인

#### 📍 예시: `add(Beverage beverage, int quantity)`
- **1잔 추가했을 때** 정상적으로 리스트에 들어가는가?
- **여러 잔(2잔 이상) 추가했을 때** 정상적으로 추가되는가?
- **음료 삭제(remove)했을 때** 리스트에서 제거되는가?
- **전체 삭제(clear)했을 때** 리스트가 비어 있는가?

---

### ❌ **2. 예외 케이스 (Error Case)**
> **비정상적인 입력이 주어졌을 때** 적절한 예외가 발생하는지 확인  
> 암묵적(일반적으로 예상되는) 예외와 명시적 예외 모두 고려

### 📍 예시: `add(Beverage beverage, int quantity)`
- **0잔 추가 요청 (`quantity = 0`)** → `IllegalArgumentException` 발생해야 함
- **음수 개수 (`quantity = -1`)** → `IllegalArgumentException` 발생해야 함
- **매우 큰 개수 (`Integer.MAX_VALUE`)** → 성능에 문제가 없는가?
- **NULL 음료 추가** → `NullPointerException` 또는 적절한 예외 발생해야 함
- **존재하지 않는 음료 삭제 (`remove` 호출 시)** → 예외 발생 여부 확인

---

### 🔥 **3. 경계값 테스트 (Boundary Test)**
> **경계에서 동작이 올바르게 수행되는지 확인**  
> 값의 **이상(≥), 이하(≤), 초과(>), 미만(<)** 등의 조건을 테스트

#### 📍 예시: `add(Beverage beverage, int quantity)`
- **최소 주문 가능 개수 (`quantity = 1`)** → 정상 동작해야 함
- **최소보다 1개 적은 경우 (`quantity = 0`)** → 예외 발생해야 함
- **최대 주문 가능 개수 (`quantity = MAX_LIMIT`)** → 정상 동작해야 함
- **최대보다 1개 초과한 경우 (`quantity = MAX_LIMIT + 1`)** → 예외 발생해야 함

---

### 📆 **4. 날짜 및 시간 관련 테스트**
> **시간이 중요한 경우, 특정 시점 및 범위를 테스트**

#### 📍 예시: `order()` 메서드 (영업시간 내 주문 가능 여부)
- **영업 시작 직전 (`오전 9:59`)** → 주문 실패해야 함
- **영업 시작 시간 (`오전 10:00`)** → 주문 성공해야 함
- **영업 종료 직전 (`오후 9:59`)** → 주문 성공해야 함
- **영업 종료 시간 (`오후 10:00`)** → 주문 실패해야 함

---

### 🛠 **5. 범위 테스트 (Range Test)**
> 특정 값이 **범위 내**에 존재하는지 확인  
> 범위(최소 ~ 최대), 특정 구간, 값 포함 여부 등을 테스트

#### 📍 예시: 할인율 검증 (`getDiscountRate()`)
- **할인율이 0% 이상 50% 이하일 때** 정상 동작하는가?
- **할인율이 50%를 초과하면 예외가 발생하는가?**
- **할인율이 음수일 때 예외가 발생하는가?**

---

### 🎯 **6. 테스트 케이스 설계 시 고려할 점**
- **요구사항을 만족하는 정상 동작을 테스트했는가? (해피 케이스)**
- **입력값이 잘못된 경우를 모두 고려했는가? (예외 케이스)**
- **최소, 최대값 등 경계에서의 동작을 확인했는가? (경계값 테스트)**
- **범위, 구간, 날짜와 관련된 조건을 체크했는가? (범위 테스트)**

---
## **🧪 테스트하기 어려운 영역과 분리하는 방법**

소프트웨어 테스트를 수행할 때, 코드 내 특정 요소들이 테스트를 어렵게 만드는 경우가 많습니다. 이러한 요소를 식별하고 분리하면, 테스트의 신뢰성을 높이고 유지보수를 용이하게 할 수 있습니다.

---
### 🚧 1. 테스트하기 어려운 영역
테스트가 어려운 코드는 실행할 때마다 다른 결과를 반환하거나 외부 환경과의 상호작용에 의존하는 코드입니다.

#### 🔍 1) 관측할 때마다 다른 값에 의존하는 코드
- ⏳ 현재 날짜 및 시간 (`LocalDateTime.now()`, `System.currentTimeMillis()` 등)
- 🎲 랜덤 값 (`Math.random()`, `UUID.randomUUID()` 등)
- 🌍 전역 변수 (상태가 변경될 수 있는 변수)
- 🎤 사용자 입력 (`Scanner`, 웹 요청 등)

#### 🌐 2) 외부 세계에 영향을 주는 코드
- 🖨️ 표준 출력 (`System.out.println()`)
- 📩 메시지 발송 (이메일, SMS 등)
- 🗄️ 데이터베이스 기록 (INSERT, UPDATE, DELETE 등)
- 📡 네트워크 요청 (HTTP API 호출 등)

---

### ✅ 2. 테스트하기 쉬운 코드
테스트하기 쉬운 코드는 같은 입력값에 대해 항상 같은 결과를 반환하며, 외부 환경과 단절된 순수한 형태를 가집니다.

#### 🎯 1) 순수 함수
- 같은 입력값에 대해 항상 같은 출력값을 반환
- 외부 상태에 영향을 주지 않음
- 예제:
  ```java
  public int add(int a, int b) {
      return a + b;
  }
  ```

#### 🔄 2) 외부 의존성을 분리
- 현재 시간을 직접 호출하는 것이 아니라, 외부에서 주입받도록 설계
- 랜덤 값 대신 의존성을 주입받아 결정론적 테스트 가능하도록 구현
- 예제:
  ```java
  public class OrderService {
      private final Clock clock;
      
      public OrderService(Clock clock) {
          this.clock = clock;
      }
      
      public LocalDateTime getCurrentTime() {
          return LocalDateTime.now(clock);
      }
  }
  ```
  ```java
  Clock fixedClock = Clock.fixed(Instant.parse("2023-01-01T00:00:00Z"), ZoneId.of("UTC"));
  OrderService orderService = new OrderService(fixedClock);
  LocalDateTime time = orderService.getCurrentTime(); // 항상 동일한 시간 반환
  ```

#### 🏗️ 3) 인터페이스 및 의존성 주입(DI)
- 의존성을 인터페이스로 추상화하여 테스트 시 Mock 객체 사용 가능
- 예제:
  ```java
  public interface TimeProvider {
      LocalDateTime now();
  }

  public class RealTimeProvider implements TimeProvider {
      public LocalDateTime now() {
          return LocalDateTime.now();
      }
  }

  public class OrderService {
      private final TimeProvider timeProvider;
      
      public OrderService(TimeProvider timeProvider) {
          this.timeProvider = timeProvider;
      }
      
      public LocalDateTime getCurrentTime() {
          return timeProvider.now();
      }
  }
  ```
  ```java
  TimeProvider mockTimeProvider = () -> LocalDateTime.of(2023, 1, 1, 0, 0);
  OrderService orderService = new OrderService(mockTimeProvider);
  LocalDateTime time = orderService.getCurrentTime(); // 항상 2023-01-01 00:00 반환
  ```

---

### 🎯 3. 결론
테스트하기 어려운 요소를 분리하여, 예측 가능한 환경에서 테스트를 수행할 수 있도록 설계하는 것이 중요합니다. 이를 위해 다음과 같은 원칙을 적용할 수 있습니다.
1. **🔹 순수 함수 작성** – 같은 입력값에 대해 같은 결과 반환.
2. **🛠️ 의존성 주입(DI) 활용** – 외부 환경과의 직접적인 의존성을 제거.
3. **🎭 Mock 객체 활용** – 외부 시스템과의 상호작용을 테스트 시 시뮬레이션.
4. **⏰ 시간, 랜덤 값 등의 요소를 외부에서 주입** – 결정론적 테스트 가능하도록 설계.

---

## 🧪 단위 테스트 및 TDD
### 1. TDD (Test-Driven Development)란?
TDD(Test-Driven Development, 테스트 주도 개발)는 테스트를 먼저 작성한 후 기능을 구현하는 개발 방식입니다. 즉, 기능을 만들기 전에 먼저 해당 기능을 검증할 테스트 케이스를 정의하는 것이 핵심입니다.

#### TDD 사이클 (Red-Green-Refactor)
1. RED: 실패하는 테스트를 작성한다. (기능이 아직 구현되지 않았기 때문)
2. GREEN: 기능을 최소한으로 구현하여 테스트를 통과시킨다.
3. BLUE(REFACTOR): 중복을 제거하고 코드를 개선(리팩토링)한다.

#### 핵심 가치: 빠른 피드백
- 구현 코드와 테스트 코드에 대해 자주, 빠르게 피드백 받을 수 있음
- 유지보수가 용이하고, 유연한 코드 작성 가능
- 엣지(Edge) 케이스를 놓치지 않도록 보완 가능
---
### 2. TDD의 장점과 문제점
#### ✅ TDD의 장점
- 코드 품질 향상: 테스트가 보장되므로 안정성이 높아짐
- 빠른 피드백: 기능 변경 시 바로 문제점을 파악 가능
- 리팩토링 용이: 기존 기능을 보호하면서 코드 개선 가능
- 유연하고 유지보수가 쉬운 코드: 복잡도가 낮고 명확한 구조 유지 가능
- 엣지 케이스(Edge Case) 검증 가능: 예상치 못한 예외 처리 강화

#### ❌ 선 기능 구현, 후 테스트 작성의 문제점
- 테스트 누락 가능성: 기능 구현 후 테스트를 작성하면 중요한 부분을 놓칠 수 있음
- 특정 케이스만 검증: 해피 케이스만 검증하고 예외 상황을 고려하지 않을 가능성이 있음
- 잘못된 구현을 늦게 발견: 기능이 정상적으로 동작하는지 뒤늦게 파악 가능
---
### 3. TDD (RED-GREEN-REFACTORING) 적용

#### 📌 RED 단계 (실패하는 테스트 작성)
```java

@Test
    void calculateTotalPrice() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        int totalPrice = cafeKiosk.calculateTotalPrice();

        assertThat(totalPrice).isEqualTo(8500);
    }
```
![img_2.png](img_2.png)
- calculateTotalPrice() 메서드가 아직 구현되지 않았으므로 테스트가 실패함.

#### 📌 GREEN 단계 (최소한의 구현 코드 작성)
```java
 public int calculateTotalPrice() {
       
    return 8500;
}
```
![img_2.png](img_2.png)
- 테스트를 통과하도록 가장 간단한 코드로 기능을 구현함.
- 하지만 하드코딩된 값을 사용하여 확장성이 부족함.

#### 📌 REFACTORING 단계 (리팩토링 및 개선)
```java
public int calculateTotalPrice() {
    int totalPrice = 0;

    for (Beverage beverage : beverages) {
        totalPrice += beverage.getPrice();
    }

    return totalPrice;
}
```
![img_2.png](img_2.png)
- Beverage 객체의 getPrice() 메서드를 활용하여 동적으로 총 가격을 계산하도록 개선.
#### 📌 REFACTORING 단계 (최적화 - Stream API 활용)
```java
 public int calculateTotalPrice() {

       return beverages.stream()
               .mapToInt(Beverage::getPrice)
               .sum();
}
```
- Stream API를 활용하여 더 간결하고 가독성이 좋은 코드로 리팩토링함.

### 4. 효과적인 TDD 실천 방법

1) 작은 단위부터 테스트하라
- 처음부터 큰 기능을 테스트하지 말고 작은 단위부터 점진적으로 확장하라.
- 예: add() → remove() → calculateTotalPrice() 순으로 테스트 작성
2) 모든 주요 케이스를 고려하라
- 해피 케이스 (정상적인 입력)
- 예외 케이스 (비정상적인 입력)
- 경계값 테스트 (최소/최대값, 초과/미만 등)
3) 테스트 코드를 유지보수 가능한 구조로 작성하라
- 가독성이 높은 테스트 네이밍 (should_동작_설명)을 사용
- 중복을 줄이고 공통된 설정(setup)은 @BeforeEach 등을 활용하여 정리
4) 외부 의존성을 분리하라
- 테스트하기 어려운 요소(시간, 랜덤 값, DB, 네트워크 요청 등)는 Mocking을 사용하여 분리하라.

---

## 💻 DisplayName
### ✅ 테스트 케이스 작성 가이드

📌 **문장형 기술 원칙**
- 명사의 나열이 아닌 **완전한 문장**으로 표현  
  ❌ `음료 1개 추가 테스트` → ✅ `음료를 1개 추가할 수 있다.`

📌 **결과까지 기술**
- 테스트 **행위의 결과**까지 포함  
  ❌ `음료를 1개 추가할 수 있다.`  
  ✅ `음료를 1개 추가하면 주문 목록에 담긴다.`

📌 **논리적인 조건 표현**
- 단순한 조건이 아닌, **정확한 논리**를 반영  
  ❌ `A이면 B이다.`  
  ✅ `A이면 B가 아니고 C다.`

📌 **도메인 용어 활용**
- 메서드 중심이 아닌 **도메인 정책 관점**에서 서술  
  ❌ `특정 시간 이전에 주문을 생성하면 실패한다.`  
  ✅ `영업 시작 시간 이전에 주문을 생성할 수 없다.`

---
### 🌟 BDD (Behavior-Driven Development, 행위 주도 개발)

BDD는 🛠**TDD**(Test-Driven Development, 테스트 주도 개발)에서 파생된 개발 방법으로,  
단순히 함수 단위의 테스트를 작성하는 것이 아니라, **📖 시나리오 기반의 테스트 케이스** 자체에 집중하는 방식입니다.

📌 **비개발자도 이해할 수 있는 테스트 코드 작성**을 목표로 하며, 테스트의 가독성과 협업 효율성을 높이는 데 초점을 맞춥니다.

---

### 🏗 BDD의 테스트 구조: **GIVEN-WHEN-THEN**

✅ **GIVEN (준비 🏁)**
- 시나리오 진행을 위한 **모든 준비 과정**을 설정합니다.
- 예) "👤 사용자가 로그인되어 있고, 🛒 장바구니에 상품이 담겨 있다."

✅ **WHEN (실행 🚀)**
- 특정 **행동(액션)**을 수행합니다.
- 예) "🖱 사용자가 결제 버튼을 클릭한다."

✅ **THEN (검증 ✅)**
- 기대하는 결과를 명시하고 검증합니다.
- 예) "💰 결제가 완료되었다는 메시지가 표시된다."

---

### 🎯 BDD의 장점

✔ **👥 비즈니스 및 비개발자와의 원활한 소통**  
✔ **👀 테스트의 명확성과 가독성 향상**  
✔ **📌 요구사항 기반의 개발 진행 가능**


---
## 📌 `@SpringBootTest` vs `@DataJpaTest`

### 1️⃣ `@SpringBootTest` 🏗️
`@SpringBootTest`는 **Spring Boot 애플리케이션 전체 컨텍스트를 로드**하여 테스트를 실행할 때 사용합니다.  
즉, **모든 빈(bean)을 로드**하여 통합 테스트를 수행할 수 있습니다.

#### ✅ 특징
- 애플리케이션의 **전체 컨텍스트**를 로드
- **모든 빈**이 활성화되므로 무거운 테스트가 될 수 있음
- 데이터베이스, 서비스, 컨트롤러 등 **전체적인 동작 테스트** 가능
- `webEnvironment` 옵션을 사용해 웹 서버를 실행할 수도 있음

#### ✅ 사용 예제
```java
@SpringBootTest
class MyApplicationTests {

    @Test
    void contextLoads() {
        // 애플리케이션 컨텍스트가 정상적으로 로드되는지 확인
    }
}
```

#### ⚙️ `webEnvironment` 옵션
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHomePage() {
        String body = this.restTemplate.getForObject("/", String.class);
        assertThat(body).contains("Welcome");
    }
}
```
- `WebEnvironment.RANDOM_PORT` → 랜덤 포트로 서버 실행
- `WebEnvironment.MOCK` → 내장 톰캣 없이 Mock 환경에서 실행

---

### 2️⃣ `@DataJpaTest` 🗄️
`@DataJpaTest`는 **JPA 관련 컴포넌트만 로드하여 테스트**할 때 사용합니다.  
즉, **Repository 계층 테스트에 최적화**되어 있습니다.

#### ✅ 특징
- **Entity, Repository만 로드**
- **데이터베이스 관련 기능만 테스트**
- 기본적으로 **H2** 같은 **임베디드 데이터베이스를 사용**
- **트랜잭션을 자동 롤백**하여 테스트 후 데이터가 남지 않음

#### ✅ 사용 예제
```java
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFind() {
        User user = new User("spring", "spring@example.com");
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("spring@example.com");
        assertThat(foundUser).isNotNull();
    }
}
```

#### ⚙️ `@AutoConfigureTestDatabase` 옵션
기본적으로 **H2 데이터베이스를 사용**하지만, **실제 DB를 테스트하려면 설정 변경이 필요**합니다.
```java
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용
class RealDatabaseTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testWithRealDB() {
        // MySQL, PostgreSQL 등 실제 DB에서 테스트
    }
}
```

---

## 🎯 정리
| 어노테이션 | 테스트 대상 | 특징 |
|-----------|-----------|------|
| `@SpringBootTest` 🌍 | 전체 애플리케이션 | 모든 빈 로드, 무거운 테스트, 통합 테스트 |
| `@DataJpaTest` 🗄️ | JPA 관련 클래스 (Repository) | JPA만 로드, 빠른 테스트, 기본 H2 DB 사용 |

✅ **`@SpringBootTest`는 전체적인 통합 테스트에 사용**하고,  
✅ **`@DataJpaTest`는 JPA 관련 테스트에 최적화**된 어노테이션입니다.

---

## CQRS와 JPA 최적화를 위한 @Transactional 활용법 🔥
### 📌 읽기 전용 설정 (readOnly = true)
  - ✅ CRUD에서 CUD 동작 X → 오직 읽기만 가능 (Create, Update, Delete 불가)
  - 🚀 JPA 성능 향상 → CUD 스냅샷 저장❌, 변경 감지❌ → 불필요한 연산 감소

### 📌 CQRS 패턴
  - Command 와 Query 분리 → 서로 책임을 분리하여 독립적으로 운영

### 📌 트랜잭션 관리 방법
  - @Transactional(readOnly = true) → 클래스 전체 적용
  - CUD 메서드에는 @Transactional을 개별 적용 → 명확한 트랜잭션 관리 가능

  이렇게 설정하면 성능 최적화 & 유지보수 용이! 🚀