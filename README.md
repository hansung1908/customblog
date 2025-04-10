# CustomBlog - 다양한 기술을 접목한 게시판 프로젝트

### 목차
[1. 프로젝트 소개](#프로젝트-소개)

[2. 기술 스택](#기술-스택)

[3. ERD](#ERD)

[3. 주요 기능](#주요-기능)

[4. 성능 최적화 및 트러블 슈팅](#성능-최적화-및-트러블-슈팅)

[5. 프로젝트 구조](#프로젝트-구조)

[6. 실행 방법](#실행-방법)

[7. 개발 과정에서 나온 내용을 정리한 글](#개발-과정에서-나온-내용을-정리한-글)

# 프로젝트 소개
CustomBlog는 Spring Boot 기반의 게시판 서비스로, 다양한 최신 기술을 접목하여 개발한 프로젝트입니다.

사용자 인증부터 게시글 관리, 파일 업로드, 검색 기능까지 실제 서비스에서 필요한 다양한 기능을 구현했으며,

JPA N+1 문제 해결, 보안 강화 등 실무에서 중요시되는 기술적 요소들을 중점적으로 다루었습니다.

본 프로젝트는 단순한 CRUD를 넘어서 성능 최적화, 보안 강화, 사용자 경험 개선에 초점을 맞추어 개발되었습니다.

# 기술 스택

### Backend
- Java 17
- Spring Boot 3.1.0
- Spring Security
- Spring Data JPA
- OAuth2
- AOP
- Validation

### Frontend
- Thymeleaf
- JavaScript
- Bootstrap

### Database
- MySQL

### Tools
- Lombok
- Git

# ERD

![Image](https://github.com/user-attachments/assets/b940d492-44b1-4478-91b2-53d0dfd28bdd)

# 주요 기능

### 사용자 관리
- 회원가입/로그인: Spring Security를 활용한 인증 및 권한 관리
- OAuth2 로그인: 카카오 소셜 로그인 연동
- 자동 로그인: Remember-me 기능을 통한 사용자 편의성 향상
- 회원정보 수정/탈퇴: 사용자 정보 관리 기능

### 게시판 관리
- 게시글 CRUD: 게시글 작성, 조회, 수정, 삭제 기능
- 페이징 처리: 효율적인 데이터 로딩을 위한 페이징 구현
- 조회수 기능: 게시글 조회 시 조회수 증가 기능
- 파일 업로드/다운로드: 게시글에 파일 첨부 및 다운로드 기능

### 검색 및 필터링
- 키워드 검색: 제목 및 작성자 기반 게시물 검색 기능
- 유저네임 중복 체크: 회원가입 시 실시간 중복 확인 기능

### 관리자 기능
- 어드민 페이지: 관리자 전용 대시보드
- 사용자 관리: 전체 사용자 목록 조회 및 관리
- 게시물 관리: 전체 게시물 관리 기능
- 공지사항 관리: 공지사항 작성 및 관리
- 로그 모니터링: 시스템 로그 실시간 모니터링

# 성능 최적화 및 트러블 슈팅 

<details>
    <summary><strong>JPA N+1 문제 + 무한 참조 문제 해결</strong></summary>

### 문제 발생
JPA를 사용하면서 N+1 문제가 발생했습니다. 이 문제는 다음과 같은 상황에서 발생했습니다:

1. 즉시로딩(EAGER)에서의 문제: JPQL이 우선적으로 select 쿼리를 실행한 후, 즉시로딩 설정을 확인하고 추가 쿼리가 발생하여 N+1 문제가 발생했습니다.
2. 지연로딩(LAZY)에서의 문제: 지연로딩된 값을 조회할 때마다 별도의 쿼리가 발생하여 결국 N+1 문제가 발생했습니다.

이로 인해 데이터 100개를 조회할 경우, 연관 엔티티를 가져오기 위해 추가로 100번의 SQL이 실행되는 상황이 발생했습니다.

JPA에서 양방향 관계(@OneToMany + @ManyToOne)를 가진 객체를 직렬화(JSON 등)할 때 무한 참조 문제가 발생했습니다.

### 해결 시도
1. Fetch Join 적용
- 첫 번째 해결책으로 Fetch Join을 시도했습니다.
- 이 방법은 지연로딩의 해결책으로, 사용될 때 확정된 값을 한 번에 join해서 가져옵니다. 그러나 다음과 같은 문제가 발생했습니다.
- 페이지네이션 문제: Fetch Join 사용 시 limit, offset을 통한 쿼리가 아닌 인메모리에 모든 데이터를 가져와 애플리케이션 단에서 처리하여 OOM(Out Of Memory) 발생 위험이 있었습니다.
- 2개 이상의 Collection Join 문제: List 자료구조의 2개 이상 Collection join(~ToMany 관계)에서 Fetch Join 할 경우 MultipleBagFetchException 예외가 발생했습니다.

2. BatchSize 설정
- 두 번째 해결책으로 BatchSize를 시도했습니다.
- 이 방법은 필요 시 배치 쿼리로 원하는 만큼 쿼리를 날려 N번 만큼의 무수한 쿼리는 발생하지 않도록 합니다.
- 그러나, 여전히 쿼리가 추가로 발생한다는 문제가 있었습니다.
- 페이지네이션 문제는 해결할 수 있었으나, 여전히 불필요한 데이터를 가져와 서버 리소스가 낭비되고 있었습니다.
- 추가로, in절로 추가 데이터를 가져오기에 1000개 이상의 항목을 처리할 경우 오히려 성능이 저하되었습니다.

### 최종 해결 방법: Response DTO Projection + JPQL 쿼리 직접 작성
위의 모든 해결책으로는 문제를 말끔히 해결할 수 없다고 판단하여,
최종적으로 Response DTO Projection과 JPQL 쿼리를 직접 작성하는 방식을 채택했습니다.
이 방법의 장점은 다음과 같습니다:

- 페이지네이션 사용 가능: 일반 조인을 사용하여 페이지네이션 문제 해결
- SQL 실행 횟수 최소화: 실행 횟수를 1번으로 줄여 성능 향상
- 필요한 데이터만 반환: 필요한 데이터만 선택적으로 가져와 보안 유지
- 데이터베이스 부담 감소: 불필요한 데이터 조회를 줄여 DB 부하 감소
- 무한 참조 방지: 엔티티 대신 DTO를 사용함으로써 양방향 관계에서 발생할 수 있는 무한 참조 문제를 원천적으로 차단

이 방식은 모든 select문을 사용하는 repository 메소드를 response dto + jpql 쿼리를 사용하여 최적화함으로써,
N+1 문제를 효과적으로 해결하고 성능을 향상시켰습니다.
</details>

<details>
    <summary><strong>부모-자식 엔티티 간 연관 데이터 삭제 문제 해결</strong></summary>

### 문제 상황
- 부모 엔티티(Board)를 삭제할 때, 연관된 자식 엔티티(Reply)는 고아 객체(orphan)로 남아 데이터베이스에 잔존하였습니다.
- 이를 해결하기 위해 수동으로 자식 엔티티를 삭제해야 했으며, 이는 코드 복잡도를 증가시키고 실수로 인해 데이터 불일치 문제가 발생할 가능성이 높았습니다.

```shell
// 설정 전: 수동 삭제 처리
boardRepository.deleteById(boardId);
replyRepository.deleteByBoardId(boardId); // 추가 작업 필요
```
  
### 해결 방법
- Cascade 설정 적용
  - @OneToMany 관계에서 cascade = CascadeType.REMOVE 를 설정하여 부모 엔티티(Board)가 삭제될 때 연관된 자식 엔티티(Reply)도 자동으로 삭제되도록 설정하였습니다.
  - 이를 통해 데이터 일관성을 보장하고 트랜잭션 관리가 간소화되었습니다.

```shell
@OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE) // 캐스케이드 설정
private List<Reply> reply;
```

### 효과 및 개선 사항

##### 1. 데이터 일관성 보장

- 부모 엔티티(Board)가 삭제될 때 JPA는 자동으로 연관된 자식 엔티티(Reply)를 함께 삭제하였습니다.
- 고아 객체 문제가 사라지며, 데이터베이스의 무결성이 유지되었습니다.

```sql
-- 실행 쿼리 예시 (캐스케이드 적용 후)
DELETE FROM reply WHERE board_id = :boardId; -- 자동 실행
DELETE FROM board WHERE id = :boardId;
```

##### 2. 트랜잭션 관리 간소화

- 부모 엔티티와 자식 엔티티의 삭제 작업이 하나의 트랜잭션으로 처리되었습니다.
- 개발자는 부모 엔터티만 삭제하면 되므로 코드가 간결해지고 실수 가능성이 줄어들었습니다.

```shell
// 설정 후: 간단한 삭제 처리
boardRepository.delete(board); // Reply도 함께 삭제됨
```

| **구분** | **설정 전** | **설정 후** |
| --- | --- | --- |
| **삭제 작업** | 부모와 자식을 각각 수동으로 삭제 | 부모만 삭제하면 자식도 자동으로 삭제 |
| **트랜잭션 관리** | 복잡한 트랜잭션 처리 필요 | 단순화된 트랜잭션 처리 |
| **데이터 일관성** | 고아 객체 발생 가능 | 고아 객체 문제 없음 |
| **성능** | 추가 DELETE 쿼리 발생 | 단일 트랜잭션 처리로 성능 향상 |
</details>

<details>
    <summary><strong>파일 업로드 Content-Type 문제 해결</strong></summary>

### 문제 상황
- 멀티파트 폼 데이터(multipart/form-data)를 사용한 파일 업로드 시 적절한 컨버터를 찾지 못하는 오류가 발생했습니다.
- 특히 큰 파일을 업로드할 때 HttpMessageNotReadableException 예외가 발생했습니다.

### 원인 분석
- Spring MVC는 기본적으로 multipart/form-data 요청을 처리하기 위한 HttpMessageConverter를 제공하지만, 특정 상황에서 적절한 컨버터를 찾지 못하는 문제가 있었습니다.
- 파일 업로드 요청의 Content-Type이 multipart/form-data로 설정되었지만, 실제 파일 데이터는 application/octet-stream으로 처리되어야 했습니다.
- 파일 크기가 큰 경우 메모리 부족 문제가 발생했습니다.

### 해결 과정
- 문제 식별: 로그 분석을 통해 Content-Type 불일치 문제를 식별했습니다.
- 컨버터 설정 검토: Spring의 HttpMessageConverter 설정을 검토했습니다.
- 커스텀 컨버터 개발: application/octet-stream을 처리할 수 있는 커스텀 HttpMessageConverter를 개발했습니다.
- 파일 업로드 설정 최적화: 업로드 하는 파일의 크기를 10mb로 설정했습니다.

### 개선 결과
- 다양한 크기와 유형의 파일을 안정적으로 업로드할 수 있게 되었습니다.
- 대용량 파일 처리 시 메모리 사용량이 최적화되었습니다.
- 파일 업로드 관련 예외 발생 빈도가 크게 감소했습니다.
</details>

<details>
    <summary><strong>코드 품질 향상</strong></summary>

- Builder 패턴 도입: 객체 생성의 일관성과 가독성 향상
- 유틸리티 클래스 분리: 재사용 가능한 기능을 별도 클래스로 분리
- 예외 처리 체계화: GlobalExceptionHandler를 통한 일관된 예외 처리
- AOP를 활용한 로깅: 컨트롤러에서 발생하는 에러를 logback을 통해 파일에 저장
</details>

<details>
    <summary><strong>보안 강화</strong></summary>

- CSRF 방어 활성화: 토큰 기반의 Cross-Site Request Forgery 방어 메커니즘 구현
- OAuth2 통합: 카카오 로그인 등 외부 인증 서비스 연동
- 자동 로그인 기능: Remember-me 기능 구현 및 토큰 관리
- DTO 패턴 적용: 필요한 데이터만 전송하여 민감 정보 노출 방지
</details>

# 프로젝트 구조
```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── customblog
│   │           ├── config
│   │           ├── controller
│   │           ├── dto
│   │           ├── handler
│   │           ├── model
│   │           ├── repository
│   │           ├── service
│   │           └── util
│   └── resources
│       ├── static
│       └── templates
└── test
```

# 실행 방법

1. 저장소 클론

```bash
git clone https://github.com/yourusername/customblog.git
```

2. 데이터베이스 설정

```text
# application.yml 파일에서 데이터베이스 설정 변경
```
프로젝트 빌드 및 실행

```bash
./gradlew bootRun
```
웹 브라우저에서 접속

``` text
http://localhost:8080
```

# 개발 과정에서 나온 내용을 정리한 글
<details>
    <summary>기능별 개발 세부 사항</summary>

### 회원가입, 로그인
- 스프링 시큐리티로 로그인 요청을 가로챈 후 로그인을 진행하여 성공하면 UserDetails타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소의 저장
- 그래서 principalDetailService에서 username이 db에 있는지 검증 후 principalDeatils로 return하여 userDetails를 통한 세션을 설정
- 원래 비밀번호 비교를 위해 authenticationManager에 userDetailService와 passwordEncoder를 반환하는 함수를 짜야했는데 최신 버전에선 service와 encoder를 선언만 해줘도 알아서 생성

### 페이징
- 페이징 처리를 위해서 list<?> 대신 page<?> 객체를 통해 리스트 데이터를 처리

### 스프링 시큐리티
- @RequestBody를 통해 json 데이터를 받아옴, 없으면 raw 데이터 받음
- 회원 정보 수정후 바로 수정된 회원정보를 보기 위해서 securityConfig에 authenticationManager를 가져오는 빈을 설정 후
- authenticationManager에 수정된 정보를 담은 토큰을 생성하여 authentication에 저장, 이후 이를 SecurityContextHolder에 설정하여 세션을 등록

### 카카오 로그인(oauth2 x)
- 카카오 로그인 구현시 인가 코드(버튼 클릭 링크)를 통해 인증 처리 후 callback주소를 컨트롤러에서 처리하여 토큰을 가져와야 로그인 가능
- 자동 로그인 기능 구현을 위해 수정된 정보를 바로 갱신할때 쓰던 SecurityContextHolder 코드를 가져와 SecurityContext oauth 로그인 데이터를 추가하여 session에 등록하는 코드로 재구현
- 리다이렉션 uri 작성시 ./login/oauth2/code/.로 고정, 앞은 도메인 주소이며 뒤는 로그인하는 사이트(google, kakao 등)
- spring에 기본적으로 탑재된 jackson 라이브러리는 오브젝트를 json으로 바꿔주는데 이때 model에 저장된 내용을 getter를 통해 가져옴

### jpa
- board 호출시 참조하고 있는 reply가 board를 참조하여 무한 참조 발생시 @jsonignoreproperties({"board"})를 board에 참조하는 reply에 설정하여 reply에서 참조하는 board가 다시 참조되는 것을 막아줌

### jpa n+1
- 즉시로딩
    - jpql을 우선적으로 select하기 때문에 즉시로딩을 이후에 보고 또다른 쿼리가 날아가 N+1
- 지연로딩
    - 지연로딩된 값을 select할 때 따로 쿼리가 날아가 N+1
- fetch join
    - 지연로딩의 해결책
    - 사용될 때 확정된 값을 한번에 join에서 select해서 가져옴
    - Pagination이나 2개 이상의 collection join에서 문제가 발생
- Pagination
    - fetch join 시 limit, offset을 통한 쿼리가 아닌 인메모리에 모두 가져와 application단에서 처리하여 OOM 발생
    - BatchSize를 통해 필요 시 배치쿼리로 원하는 만큼 쿼리를 날림 > 쿼리는 날아가지만 N번 만큼의 무수한 쿼리는 발생되지 않음
- 2개 이상의 Collection join
    - List 자료구조의 2개 이상의 Collection join(~ToMany관계)에서 fetch join 할 경우 MultipleBagFetchException 예외 발생
    - Set자료구조를 사용한다면 해결가능 (Pagination은 여전히 발생)
    - BatchSize를 사용한다면 해결가능 (Pagination 해결)
- 위 모든 해결책으론 말끔히 해결할 수 없다 생각
- response dto projection + jpql 쿼리 직접 작성(일반 조인 사용) 적용
- pagenation 사용, sql의 실행 횟수를 1번으로 최소화, 필요한 데이터만 반환하여 보안 유지

### oauth2
- 각 사이트별로 제공되는 attribute가 다르므로 oauth2userinfo 인터페이스를 만들어 각 사이트의 userinfo가 implements하여 각 사이트가 제공하는 attribute에 맞게 정보를 넣어 저장
- oauth2를 통해 카카오 로그인 서비스를 개발할 때 secret키를 따로 설정하지 않으면 yml에서도 secret키를 제외하고 설정

### aop, validation
- domain에 @notnull, @notblank, @size와 같은 어노테이션을 걸고 만약 해당 조건에 만족하지 못하면 controller에 설정한 @valid bindingresult에 그 정보가 담김
- @configuration은 시작 전 설정과 관련된 정보를 담을 쓰고, 그 외에는 @component를 사용
- BindingAdvice는 validation체크를 할 메소드를 aop를 통해 가로채서 ProceedingJoinPoint를 통해 해당 메소드의 정보를 가져와 처리
- 로그 처리는 aop를 통해 controller에서 발생하는 모든 error를 logback을 사용하여 파일에 저장

### @Controller vs @RestController
- 두 차이점은 크게 응답에 대한 반환값의 차이
- html 뷰 렌더링 vs rest api 생성 + 데이터(보통 json) 반환
- @Controller에서도 데이터를 반환하고 싶으면 메소드에 @ResponseBody 추가

### enum
- 상수 값의 집합, 특정 값만 허용하여 잘못된 값 입력 방지
- db에서 값을 가져와 html에 표시할때 toString을 구현해야 표시됨
</details>

<details>
    <summary>기능 구축 타임라인</summary>

### 조회수 기능 추가
- 게시글에 들어갈 경우 조회수가 증가하는 기능
- 기존에 board 테이블에 있던 count 칼럼을 활용하여 repository에 해당 게시물의 count를 1씩 올리는 countUpById 메소드 제작
- 서비스 레이어에서 게시물 id를 받아 countUpBy 메소드를 호출하는 countUp 메소드 제작, @Transactional을 통해 데이터 무결성 보장
- 게시물을 자세히 보기 위해 get 요청에 id를 보내 게시물 내용을 받아오던 findById 메소드에 countUp을 추가하여 findByIdAndCountUp 메소드로 변경
- 올려진 게시물들을 보는 index.html과 게시물의 자세한 내용을 보는 detail.html에서 board의 count값을 받아와 보여줌

### 자동 로그인 기능 추가
- 로그인 페이지에서 'Remeber me'라는 버튼을 체크하고 로그인하면 다음에 접속할 때 별도의 로그인 없이 바로 로그인된 상태로 접속되는 기능
- loginForm.html에서 나중에 이 기능을 만들기 위해 만들어 놓았던 라디오 버튼의 name, id, for 속성 값을 표준값인 remember-me로 수정
- 토큰에 대한 정보를 저장하는 tokenRepository를 만들어 db 테이블 생성 및 연결
- SecurityConfig의 http 설정 파트에 rememberMe 필터를 추가해 식별키, 토큰을 저장할 repository (tokenRepository), 토큰 유효 기간을 설정
- 로그인시 'Remeber me' 버튼을 누르고 로그인하면 토큰 정보를 저장할 테이블이 만들어지고 해당 로그인에 대한 토큰 정보를 저장
- remember-me라는 이름의 쿠키가 브라우저에 설정
- 재접속을 위해 다시 접속하면 자동 로그인이 됨
---

- 자동 로그인이 되는 과정

1. 접속시 브라우저에 저장된 쿠키를 http 헤더에 담아 서버로 전송
2. SpringSecurity에서 RememberMeAuthenticationFilter가 동작하여 해당 쿠키값를 분석
3. 설정되어 있는 tokenRepository에 데이터와 일치하는 데이터가 있는지 + 유효 기간을 검증
4. 일치하면 authentication 객체를 받아 SecurityContext에 인증된 사용자 정보 저장
5. AuthenticationManager를 통해 인증처리되어 해당 정보로 로그인 실행
---

- 브라우저 설정에서 쿠키를 자동으로 삭제하는지 확인하여 삭제되지 않도록 설정
- 토큰 정보 테이블이 생성되어 있다면 jdbcTokenRepository.setCreateTableOnStartup(false)로 설정해 테이블 중복 생성을 차단

### 파일 업로드 기능 추가
- 게시글 업로드시 게시글과 같이 파일을 업로드하는 기능
- saveForm.html에 파일을 업로드하기 위한 file 타입의 input 버튼 추가
---

- board.js에서 ajax를 통해 입력 데이터를 보낼때 파일도 같이 보내기 위해 content-type을 수정 (multipart/form-data로 설정)
- content type 변경할 때 dto로 받으면 적절한 컨버터를 찾지 못해 'content-type 'application/octet-stream' is not supported'라는 예외를 발생
- application/octet-stream은 특별히 표현할 수 있는 프로그램이 존재하지 않는 데이터의 경우 기본값으로 설정되는 mime 타입
- 해결책으로는 httpmessageconverter에 application/octet-stream 추가하여 일단 역직렬화를 거쳐 서버로 가져와 처리하도록 설정
---

- 파일 정보를 데이터베이스에 저장하기 위해 File (dao) + FileRepository를 만들어 jpa를 통한 테이블 생성
- FileService를 만들어 해당 파일의 복사본을 서버에 저장하고 파일 정보는 db에 저장하는 UploadAndSave 메소드 생성
- BoardService의 게시판 저장 메소드에 파일 관련 데이터를 추가로 받아 UploadAndSave로 넘겨주는 코드 추가

### 파일 다운로드 기능 추가
- 게시글에 첨부된 파일을 클릭하면 다운로드 되도록하는 기능
- 먼저 게시글에 포함된 파일을 표시하기 위해 detail.html에 a 타입의 링크를 설정
- 파일명은 FileService의 findFileName 메소드를 만들어 게시글 id에 맞는 파일을 db에서 가져와 이름만 추출하여 string으로 반환 (없는 경우 null 반환)
- BoardController의 findByIdAndCountUp 메소드에 attribute로 파일명을 추가하여 프론트로 전달
- th:if를 통해 null이 아닐 경우에만 파일 부분이 보이도록 설정
---

- html에 링크 주소는 /file/${boardDetail.id}로 설정하여 게시물에 id값을 첨부
- FileController에서 해당 주소를 받아 다운로드 기능을 처리하기 위해 만든 FileService의 fileDownload 메소드로 id 값 전송
- 이때 다운로드 헤더 설정을 위한 HttpServletResponse도 받아 같이 전송
- 해당 값들을 받은 fileDownload 메소드에서 id 값을 통해 db에서 파일 정보를 가져옴
- 이를 바탕으로 다운로드를 위한 응답 헤더 세팅 + 서버에서 가지고 있던 파일 복사 후 전달 (다운로드)

### 파일 삭제 기능 추가
- 게시글 삭제시 첨부된 파일도 같이 삭제되는 기능
- 같은 게시판 id를 가진 파일 정보 삭제를 위해 FileRepository의 deleteByBoardId 메소드 생성
- 이를 이용해 업로드 해놓은 파일도 삭제하고 파일에 관한 db 데이터도 삭제하는 fileDelete 메소드 제작
- 게시판 삭제를 위해 만들어 놓은 BoardApiController의 deleteById에 fileDelete 메소드 추가
- 이때 File 테이블은 Board를 외래키로 참조하고 있으므로 먼저 삭제

### 검색 기능 추가
- 검색창에 키워드를 입력하면 해당 키워드를 가진 제목이나 작성자와 일치하는 게시물만 따로 가져와 목록으로 보여주는 기능
- 먼저 게시물 전체를 보여주는 index.html에 검색 입력 부분과 게시물의 작성자 표시를 추가
- 작성자는 키워드 검색할 때 작성자도 포함하기에 확인을 위해 표시
- search.js를 만들어 keyword 값을 url에 파리미터로 추가해 ajax로 넘겨줌
---

- BoardRepository에서 제목이나 작성자에 키워드가 포함된 게시물들을 찾는 findBoardByKeyword 메소드 작성
- pageable을 사용하기 위해 jpql을 사용하여 쿼리 생성 + 페이징을 이용하기 위한 page 타입 반환
- BoardService의 boardListByKeyword 메소드를 만들어 keyword와 pageable을 받아 repository로 넘겨줌
- BoardController의 index 메소드에 keyword 값을 추가로 받아 service로 넘기는 코드 추가
---

### 유저네임 중복 체크 기능 추가
- 유저네임 중복 체크를 위한 button 추가
- user.js에 ajax를 통한 유저네임 전달
- 유저 이름을 받기 위한 UserCheckNameRequestDto 생성
---

- 유저 네임을 전달할 checkName 메소드를 UserApiController에 생성
- 이때, 주소는 /auth/joinProc/checkName로 지정하여 spring security에서 정한 주소 내에서 다루도록 설정
- 중복 확인을 안눌렀거나 중복이 존재할 경우 회원가입을 막을 필요가 있음
- 그래서 중복 유무를 판단하는 usernameCheckStatus를 세션에 추가
- 기존에 회원가입을 당담하던 UserApiController의 save 메소드에 세션 판별 코드 추가
- 회원가입시 null이거나 false면 400번 오류를 띄우고, true면 회원가입 통과
---

- UserRepository에 유저네임과 기존 db에 있는 유저네임과 비교하여 유무를 판별하는 existsByName 메소드 생성
- 중복 체크는 db에 같은 유저네임이 하나라도 있다면 성립 불가
- 단순히 where절에 유저네임을 넣어 유무를 판별하거나 count를 사용하여 갯수를 세는 방식은 모든 데이터와 비교하여 비효율
- 그래서 EXISTS를 사용하여 같은 유저네임이 나오면 바로 0을 반환하여 sql 성능 최적화
- controller로부터 유저네임을 받아 0과 1로 중복여부를 반환하는 checkName 메소드를 UserService에 추가

### 어드민 페이지 추가
- 관리자가 직접 유저, 게시물, 공지사항을 관리하는 별도의 페이지 추가
- admin 역할을 가지고 있는 계정으로 로그인하면 어드민 전용 페이지 (admin/dashboard.html)로 이동
- 어드민에 맞게 유저, 게시물, 공지사항 생성 페이지로 이동하는 버튼이 있는 전용 네비게이션 바 설정
- 어드민 페이지를 통해 입력되는 다양한 입력들은 admin.js에서 처리
- adminController, adminApiController를 만들어 어드민 페이지의 뷰 설정과 관리 기능들을 설정
---

- 전체 유저 목록을 보여주고 검색, 삭제하는 버튼이 있는 유저 관리 페이지 생성 (admin/users.html)
- 유저 목록에서 각 유저를 삭제하는 버튼을 만들기 위해 index 파트에 동적 버튼 생성 함수 추가
- 각 버튼 기능을 ajax 통신으로 controller에 전달 (userSearch, userDeleteById)
- UserService (userList, userListByKeyword, delete)와 UserRepository (findUserByKeyword)에 해당 기능들을 구현하기 위한 메소드 생성
- 기존에 유저 페이지에서 구현한 기능을 참고하여 제작
- 게시물 관리 부분도 이와 유사하게 제작 (admin/boards + 기존 기능 활용)
---

- 공지사항 관리도 크게 다르지 않으나 조금씩 다른 부분이 있음
- 어드민 전용 페이지에 공지사항 목록을 구현하고 저장 기능 (admin/noticeSaveForm)은 따로 분리
- 일반 사용자가 최신 공지사항을 볼 수 있도록 유저 메인 페이지 (index.html)에 최신 공지사항 표시 부분 구현
- 공지사항 삭제시 공지사항 제목으로 삭제 (noticeDeleteByTitle)
- 나머지는 유저 관리처럼 기존 기능을 참고하여 제작
---

- 오류 발생시 생기는 로그를 담아두는 logback.log을 어드민 페이지에 표시
- BufferedReader를 통해 해당 파일을 내용을 한줄씩 읽어 list로 구성

### 회원 탈퇴 기능 추가
- 회원 탈퇴 버튼을 누르면 로그아웃과 동시에 회원에 대한 정보를 삭제하는 기능
- 회원 수정 페이지 (user/updateForm.html)에 회원 탈퇴 버튼 추가
- 유저 관련 데이터를 전송하는 기능을 다루는 user.js에서 ajax를 통해 회원 삭제 요청 기능 추가
- UserRepository에 deleteByUsername 메소드를 네이밍 규칙을 통해 생성
- 위 메소드를 호출하는 동일한 이름의 메소드를 UserService에 생성
- UserApiController에서 프론트로부터 회원 삭제 요청을 받으면 서비스에서 회원 삭제 메소드를 호출 + 스프링 시큐리티를 통한 로그아웃하는 delete 메소드 생성

</details>

<details>
    <summary>기능 변경 타임라인</summary>

### lombok 생성자 어노테이션 변경
- lombok 생성자 어노테이션은 크게 @NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor가 있음
- 순서대로 파라미터가 없는 생성자, 특정 파라미터만 있는 생성자, 모든 파라미터가 있는 생성자 생성에 사용
- 코드를 보다 간편하게 만들어 유용하다고 생각했으나 2가지 이유로 직접 선언하기로 결정

1. 어노테이션을 사용하여 코드를 간편화하였지만 오히려 유지보수나 재검토시 생략되는 부분이 많아 분석에 어려움이 있음
2. @RequiredArgsConstructor, @AllArgsConstructor를 사용할 때 파리미터 생성 순서를 변경하면 오류가 발생
    - 정확히는 객체 생성시 파라미터 값이 바꿘 줄 모르고 엉뚱한 값을 넣어서 오류가 발생할 수 있음

### lombok builder 어노테이션 변경
- builder 패턴은 복잡한 파라미터에서 보다 쉽게 객체를 생성할 수 있게 해주는 디자인 패턴
- lombok의 builder 어노테이션을 사용하면 쉽게 구현할 수 있음
- lombok 특성상 코드를 간편하게 만들 수 있으나 2가지 이유로 직접 선언하기로 결정

1. lombok 생성자 어노테이션과 마찬가지로 유지보수나 재검토시 생략되는 부분이 많아 분석에 어려움이 있음
2. PrincipalOauth2UserService의 자동가입 파트에서 기존 유저를 못찾으면 빈 객체를 반환하는 코드를 생성자로 접근하지 않고 빌더에서 처리하는 함수를 만들어야 함

### lombok data 어노테이션 변경
- lombok data 어노테이션은 모든 파라미터의 setter / getter를 생성해주는 어노테이션
- 각각 setter나 getter만 생성할 수 있는 @Setter, @Getter도 있음
- 아래 4가지 이유로 접근 방식을 builder + @Getter로 바꾸기로 결정

1. @Data를 사용하면 불필요한 setter가 생성되어 보안 문제 발생 우려
2. setter 자체도 객체 일관성 유지가 어려워 안정성 보장 x
3. 객체 생성시 가독성이 떨어짐
4. 제작 과정에서 이미 builder를 사용하여 기존에 있던 setter가 필요 없어짐

### BoardSaveRequestDto 생성 + 추가 dto 생성
- dto는 데이터 전송 객체 (data transfer object)로 client(html)에서 controller로 데이터를 넘길 때 사용
- 주로 필요한 데이터만 가져오고 (보안성) 수정하기 용이한 이유로 (유연성) 많이 사용
- 위 장점들을 바탕으로 2가지 이유로 제작을 결정

1. controller부분에서 board 제목과 내용을 받을때 '@RequestBody Board board'로 객체를 생성해서 받아 불필요한 데이터가 노출될 수 있음
2. controller는 그저 요청을 올바른 서비스에 전달해야 하는 입장으로 객체 생성으로 데이터를 다루는 것을 막아 의존성 관리

- BoardUpdateRequestDto, UserSaveRequestDto, UserUpdateRequestDto 추가 생성
- @GeneratedValue, @CreationTimestamp와 같이 자동으로 값을 설정하는 어노테이션은 추가적인 초기화가 필요없어서 빌더를 만들때도 생략 가능
- @PathVariable는 url에 담긴 경로 변수 (예, /api/board/{id})를 변수로 받아오게 하는 어노테이션으로 userId와 같은 민감한 정보를 url에 노출시킬 우려가 있어 dto를 통해 받아옴

### validation 어노테이션 수정
- jakarta.validation.constraints 어노테이션은 클라이언트로부터 받은 데이터의 유효성을 검사하기 위한 어노테이션
- @Email, @NotNull, @NotBlank, @Size 등 다양한 조건들을 원하는 필드에 붙혀서 사용
- 생성한 객체에 @Vaild를 붙혀서 유효성 검사 객체로 설정
- 원래 객체를 생성해 그대로 받아서 domain에 검증 어노테이션을 설정했지만 dto로 교체하면서 dto로 재설정

### 예외 메세지 수정
- 데이터 처리 과정에서 exception이 발생하면 GlobalExceptionHandler으로 오게 설정 (@ControllerAdvice를 설정)
- 예외 처리에서 500번 상태 코드(http 상태 코드)를 보내 오류 메세지를 발신해야 하는데 완료 메세지와 함께 그냥 넘어가는 걸 확인
- js 코드를 수정하여 모든 응답 메세지를 받은 후 상태 코드를 if문으로 분류하여 처리하도록 재작업

### 로그인 실패 메세지 추가
- 로그인 과정에서 아이디나 비밀번호가 틀릴 경우 경고 메시지 없이 로그인 페이지로 돌아온다는 점을 확인
- AuthenticationFailureHandler를 상속받은 커스텀 핸들러로 실패시 session에 실패했다는 메세지가 담긴 errorMessage를 설정
- SecurityConfig의 formLogin 파트에 커스텀한 핸들러가 설정된 failureHandler를 추가
- loginForm.html에 로그인 실패시 세션에 추가된 errorMessage를 보여주는 오류 메세지 알림 파트 추가

### 응답 메세지 수정 + 예외 분리
- 동작에 성공하여 응답 메세지를 보낼때 해당 동작에 대한 구체적인 성공 내용으로 변경
- 성공 내용을 받아 js alert로 출력
- Exception의 하위 클래스인 SQLException, RuntimeException, IOException로 분리하여 어떤 문제가 생겼는지 바로 파악

### 파일 이름 관련 기능 유틸리티 클래스로 분리
- 파일 이름을 조작하는 기능들을 유틸리티 클래스로 분리하여 메소드로 제작
- 파일 관련 기능을 만들면서 중복 코드와 직관적이지 못한 코드들로 분리 결정
- 각 기능마다 알맞는 메소드명을 부여하여 직관성과 사용성을 높임

### csrf 방어 활성화
- Cross-Site Request Forgery
- 사용자가 인증된 세션을 가진 웹사이트에서 악의적인 요청을 보내는 공격 방식
- 이를 방지하기 위해 post, put, delete 요청을 보내는 ajax에 csrf 토큰을 헤더에 추가
- ajax에 csrf 토큰 정보를 전달하기 위해 모든 form에 spring security에서 제공하는 csrf 토큰값 입력
- SecurityConfig에 비활성화되어 있던 csrf 방어 설정 활성화

### 유효성 검사 어노테이션 추가 및 관련 오류 핸들러 추가
- 기능 추가에 따라 @Vaild와 BindingResult를 활용 가능한 메소드에 유효성 검사 + 로깅
- BindingResult가 작동하지 않아 생기는 MethodArgumentNotValidException이 발생하는 경우 존재 (게시물 + 이름 중복)
- 해당 경우에는 GlobalExceptionHandler로 가져와 오류 내용을 추출하여 따로 처리

### 모델 레이어 일부 수정
- 빌더 사용으로 필요없어진 모든 필드를 초기화하는 생성자 삭제
- board와 user 데이터를 업데이트할 때 해당 객체의 정보를 빌더로 불러와서 수정할 수 있는 toBuilder 메소드 추가
- toBuilder 메소드 추가로 기존 서비스 레이어에서 진행하던 데이터 업데이터에서 toBuilder 활용으로 변경

### 검색 기능 오류 수정
- js에서 redirect시 이중 호출로 model 내용이 사라지는 오류 발견
- redirct를 제거하여 해당 에러 해결

### response dto + jpql 쿼리를 사용에 따른 전체 로직 수정
- 모든 select문을 사용하는 repository 메소드를 response dto + jpql 쿼리를 사용하여 최적화
- 쿼리문을 직접 작성하여 n+1 문제 해결과 함께 성능 향상
- 필요한 데이터만 받아 반환하여 보안성 향상 + db 부담 축소
- page 객체 그대로 사용하여 보수유지
</details>