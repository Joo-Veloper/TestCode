### 🧪 단위 테스트

#### **1. 단위 테스트란?**
- 작은 코드 단위를 **독립적으로 검증**하는 테스트
- 주로 **클래스 & 메서드** 단위로 진행
- 외부 네트워크 등 **외부 환경에 의존하지 않는** 것이 중요
- 빠르고 안정적이라는 장점이 있음

#### **2. 테스트 방식**
✅ **수동 테스트**: 사람이 직접 확인하는 테스트  
✅ **자동 테스트**: 기계가 최종적으로 검증하는 테스트

#### **[3. JUnit 5](https://junit.org/junit5/docs/current/user-guide/)**
- Java 기반 **단위 테스트 프레임워크**
- XUnit 계열 (Kent Beck)
    - SUnit(Smalltalk), JUnit(Java), NUnit(.NET)

#### **[4. AssertJ](https://assertj.github.io/doc/)**
- 테스트 코드 작성을 돕는 **테스트 라이브러리**
- 풍부한 API 제공 & **메서드 체이닝 지원** (가독성 향상)

### 📌 **테스트 케이스 세분화**

테스트를 더욱 철저하게 진행하려면, 다양한 **케이스를 분류하고 경계값을 고려**해야 합니다.  
아래와 같이 **해피 케이스**, **예외 케이스**, **경계값 테스트**를 체계적으로 정리할 수 있습니다.

---

## ✅ **1. 해피 케이스 (Happy Case)**
> **요구 사항을 정상적으로 만족하는 경우**  
> 일반적인 흐름에서 기대한 대로 동작하는지 확인

### 📍 예시: `add(Beverage beverage, int quantity)`
- **1잔 추가했을 때** 정상적으로 리스트에 들어가는가?
- **여러 잔(2잔 이상) 추가했을 때** 정상적으로 추가되는가?
- **음료 삭제(remove)했을 때** 리스트에서 제거되는가?
- **전체 삭제(clear)했을 때** 리스트가 비어 있는가?

---

## ❌ **2. 예외 케이스 (Error Case)**
> **비정상적인 입력이 주어졌을 때** 적절한 예외가 발생하는지 확인  
> 암묵적(일반적으로 예상되는) 예외와 명시적 예외 모두 고려

### 📍 예시: `add(Beverage beverage, int quantity)`
- **0잔 추가 요청 (`quantity = 0`)** → `IllegalArgumentException` 발생해야 함
- **음수 개수 (`quantity = -1`)** → `IllegalArgumentException` 발생해야 함
- **매우 큰 개수 (`Integer.MAX_VALUE`)** → 성능에 문제가 없는가?
- **NULL 음료 추가** → `NullPointerException` 또는 적절한 예외 발생해야 함
- **존재하지 않는 음료 삭제 (`remove` 호출 시)** → 예외 발생 여부 확인

---

## 🔥 **3. 경계값 테스트 (Boundary Test)**
> **경계에서 동작이 올바르게 수행되는지 확인**  
> 값의 **이상(≥), 이하(≤), 초과(>), 미만(<)** 등의 조건을 테스트

### 📍 예시: `add(Beverage beverage, int quantity)`
- **최소 주문 가능 개수 (`quantity = 1`)** → 정상 동작해야 함
- **최소보다 1개 적은 경우 (`quantity = 0`)** → 예외 발생해야 함
- **최대 주문 가능 개수 (`quantity = MAX_LIMIT`)** → 정상 동작해야 함
- **최대보다 1개 초과한 경우 (`quantity = MAX_LIMIT + 1`)** → 예외 발생해야 함

---

## 📆 **4. 날짜 및 시간 관련 테스트**
> **시간이 중요한 경우, 특정 시점 및 범위를 테스트**

### 📍 예시: `order()` 메서드 (영업시간 내 주문 가능 여부)
- **영업 시작 직전 (`오전 9:59`)** → 주문 실패해야 함
- **영업 시작 시간 (`오전 10:00`)** → 주문 성공해야 함
- **영업 종료 직전 (`오후 9:59`)** → 주문 성공해야 함
- **영업 종료 시간 (`오후 10:00`)** → 주문 실패해야 함

---

## 🛠 **5. 범위 테스트 (Range Test)**
> 특정 값이 **범위 내**에 존재하는지 확인  
> 범위(최소 ~ 최대), 특정 구간, 값 포함 여부 등을 테스트

### 📍 예시: 할인율 검증 (`getDiscountRate()`)
- **할인율이 0% 이상 50% 이하일 때** 정상 동작하는가?
- **할인율이 50%를 초과하면 예외가 발생하는가?**
- **할인율이 음수일 때 예외가 발생하는가?**

---

## 🎯 **6. 테스트 케이스 설계 시 고려할 점**
- **요구사항을 만족하는 정상 동작을 테스트했는가? (해피 케이스)**
- **입력값이 잘못된 경우를 모두 고려했는가? (예외 케이스)**
- **최소, 최대값 등 경계에서의 동작을 확인했는가? (경계값 테스트)**
- **범위, 구간, 날짜와 관련된 조건을 체크했는가? (범위 테스트)**

---
### **🧪 테스트하기 어려운 영역과 분리하는 방법**

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
