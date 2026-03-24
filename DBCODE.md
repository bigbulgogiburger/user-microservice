# DBCODE.md — user-microservice

Spring Boot 기반 사용자 마이크로서비스 (Eureka 클라이언트, Spring Cloud Config 적용)

## Commands

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun

# 테스트
./gradlew test

# 단일 테스트 클래스 실행
./gradlew test --tests "com.example.usermicroservice.UserMicroserviceApplicationTests"

# 특정 테스트 메서드 실행
./gradlew test --tests "com.example.usermicroservice.UserMicroserviceApplicationTests.contextLoads"

# JAR 파일로 실행
java -jar build/libs/user-microservice-0.0.1-SNAPSHOT.jar
```

## Architecture

```
UserMicroserviceApplication (entry point)
    ├── @EnableDiscoveryClient → Eureka 등록
    ├── @EnableWebSecurity → Security 활성화
    └── @EnableJpaRepositories → JPA 활성화
    │
    ├── controller/
    │   └── UserController (REST API endpoints)
    │       - POST /users (회원가입)
    │       - GET /users (전체 조회)
    │       - GET /users/{userId} (개별 조회)
    │       - GET /health_check (상태확인)
    │       - GET /welcome (인사)
    │
    ├── service/
    │   ├── UserService (interface)
    │   └── UserServiceImpl (구현)
    │       - createUser(), getUserByAll(), getUserById(), getUserDetailsByEmail(), loadUserByUsername()
    │
    ├── repository/
    │   └── UserRepository (JPA)
    │
    ├── entity/
    │   └── UserEntity (JPA Entity)
    │
    ├── dto/
    │   └── UserDto
    │
    ├── vo/ (Value Objects)
    │   ├── RequestUser, ResponseUser
    │   ├── RequestLogin, ResponseOrder
    │   └── Greeting
    │
    └── security/
        ├── SecurityConfig (IP 기반 접근제어)
        └── AuthenticationFilter (JWT 인증)
```

## Code Style

- **패키지 구조**: `com.example.usermicroservice.{layer}`
- **명명 규칙**: camelCase (변수/메서드), PascalCase (클래스/인터페이스)
- **Lombok**: @Getter/@Setter 사용, @RequiredArgsConstructor (final 필드)
- **ModelMapper**: 매핑 시 MatchingStrategies.STRICT 사용
- **예외 처리**: UsernameNotFoundException 사용

## Development

### 필수 환경 변수

application.yml 또는 Spring Cloud Config 서버에서 설정:
- `spring.datasource.*` - H2 인메모리 DB (테스트용)
- `eureka.client.service-url.defaultZone` - Eureka 서버 주소 (기본: http://localhost:8761/eureka)
- `spring.cloud.config.uri` - Config 서버 주소 (기본: http://localhost:8888)
- `token.secret` - JWT 시크릿 키
- `token.expiration_time` - 토큰 만료 시간 (ms)

### 로컬 개발 환경

1. **필수 서비스**:
   - Eureka Server (localhost:8761)
   - Spring Cloud Config Server (localhost:8888)

2. **개발 시 실행 옵션**:
   - H2 Console: `/h2-console` (application.yml에서 활성화됨)
   - Actuator: `/actuator/refresh` (Config 재로드)

3. **IP 접근제어**: SecurityConfig.java의 `ALLOWED_IP_ADDRESS` 상수를 로컬 IP로 수정 필요

### 빌드 요구사항

- Java 17
- Gradle (gradlew 사용)
- Spring Boot 3.3.1
- Spring Cloud 2023.0.2