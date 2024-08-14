# customblog
- 다양한 기술을 접목한 게시판 기반 프로젝트

### java 17, spring boot 3.1.0
- spring web
- jpa
- thymeleaf
- spring security
- oauth2-client
- aop
- validation
- lombok
- mysql

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

### oauth2
- 각 사이트별로 제공되는 attribute가 다르므로 oauth2userinfo 인터페이스를 만들어 각 사이트의 userinfo가 implements하여 각 사이트가 제공하는 attribute에 맞게 정보를 넣어 저장
- oauth2를 통해 카카오 로그인 서비스를 개발할 때 secret키를 따로 설정하지 않으면 yml에서도 secret키를 제외하고 설정

### aop, validation
- domain에 @notnull, @notblank, @size와 같은 어노테이션을 걸고 만약 해당 조건에 만족하지 못하면 controller에 설정한 @valid bindingresult에 그 정보가 담김
- @configuration은 시작 전 설정과 관련된 정보를 담을 쓰고, 그 외에는 @component를 사용
- BindingAdvice는 validation체크를 할 메소드를 aop를 통해 가로채서 ProceedingJoinPoint를 통해 해당 메소드의 정보를 가져와 처리
- 로그 처리는 aop를 통해 controller에서 발생하는 모든 error를 logback을 사용하여 파일에 저장

### 변경 사항

##### lombok 생성자 어노테이션 변경
- lombok 생성자 어노테이션은 크게 @NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor가 있음
- 순서대로 파라미터가 없는 생성자, 특정 파라미터만 있는 생성자, 모든 파라미터가 있는 생성자 생성에 사용
- 코드를 보다 간편하게 만들어 유용하다고 생각했으나 2가지 이유로 직접 선언하기로 결정

1. 어노테이션을 사용하여 코드를 간편화하였지만 오히려 유지보수나 재검토시 생략되는 부분이 많아 분석에 어려움이 있음
2. @RequiredArgsConstructor, @AllArgsConstructor를 사용할 때 파리미터 생성 순서를 변경하면 오류가 발생
   - 정확히는 객체 생성시 파라미터 값이 바꿘 줄 모르고 엉뚱한 값을 넣어서 오류가 발생할 수 있음

##### lombok builder 어노테이션 변경
- builder 패턴은 복잡한 파라미터에서 보다 쉽게 객체를 생성할 수 있게 해주는 디자인 패턴
- lombok의 builder 어노테이션을 사용하면 쉽게 구현할 수 있음
- lombok 특성상 코드를 간편하게 만들 수 있으나 2가지 이유로 직접 선언하기로 결정

1. lombok 생성자 어노테이션과 마찬가지로 유지보수나 재검토시 생략되는 부분이 많아 분석에 어려움이 있음
2. PrincipalOauth2UserService의 자동가입 파트에서 기존 유저를 못찾으면 빈 객체를 반환하는 코드를 생성자로 접근하지 않고 빌더에서 처리하는 함수를 만들어야 함

##### lombok data 어노테이션 변경
- lombok data 어노테이션은 모든 파라미터의 setter / getter를 생성해주는 어노테이션
- 각각 setter나 getter만 생성할 수 있는 @Setter, @Getter도 있음
- 아래 4가지 이유로 접근 방식을 builder + @Getter로 바꾸기로 결정

1. @Data를 사용하면 불필요한 setter가 생성되어 보안 문제 발생 우려
2. setter 자체도 객체 일관성 유지가 어려워 안정성 보장 x
3. 객체 생성시 가독성이 떨어짐
4. 제작 과정에서 이미 builder를 사용하여 기존에 있던 setter가 필요 없어짐

##### BoardSaveRequestDto 생성 + 추가 dto 생성
- dto는 데이터 전송 객체 (data transfer object)로 client(html)에서 controller로 데이터를 넘길 때 사용
- 주로 필요한 데이터만 가져오고 (보안성) 수정하기 용이한 이유로 (유연성) 많이 사용
- 위 장점들을 바탕으로 2가지 이유로 제작을 결정

1. controller부분에서 board 제목과 내용을 받을때 '@RequestBody Board board'로 객체를 생성해서 받아 불필요한 데이터가 노출될 수 있음
2. controller는 그저 요청을 올바른 서비스에 전달해야 하는 입장으로 객체 생성으로 데이터를 다루는 것을 막아 의존성 관리

- BoardUpdateRequestDto, UserSaveRequestDto, UserUpdateRequestDto 추가 생성
- @GeneratedValue, @CreationTimestamp와 같이 자동으로 값을 설정하는 어노테이션은 추가적인 초기화가 필요없어서 빌더를 만들때도 생략 가능
- @PathVariable는 url에 담긴 경로 변수 (예, /api/board/{id})를 변수로 받아오게 하는 어노테이션으로 userId와 같은 민감한 정보를 url에 노출시킬 우려가 있어 dto를 통해 받아옴

##### validation 어노테이션 수정
- jakarta.validation.constraints 어노테이션은 클라이언트로부터 받은 데이터의 유효성을 검사하기 위한 어노테이션
- @Email, @NotNull, @NotBlank, @Size 등 다양한 조건들을 원하는 필드에 붙혀서 사용
- 생성한 객체에 @Vaild를 붙혀서 유효성 검사 객체로 설정
- 원래 객체를 생성해 그대로 받아서 domain에 검증 어노테이션을 설정했지만 dto로 교체하면서 dto로 재설정

##### 예외 메세지 수정
- 데이터 처리 과정에서 exception이 발생하면 GlobalExceptionHandler으로 오게 설정 (@ControllerAdvice를 설정)
- 예외 처리에서 500번 상태 코드(http 상태 코드)를 보내 오류 메세지를 발신해야 하는데 완료 메세지와 함께 그냥 넘어가는 걸 확인
- js 코드를 수정하여 모든 응답 메세지를 받은 후 상태 코드를 if문으로 분류하여 처리하도록 재작업

##### 로그인 실패 메세지 추가
- 로그인 과정에서 아이디나 비밀번호가 틀릴 경우 경고 메시지 없이 로그인 페이지로 돌아온다는 점을 확인
- AuthenticationFailureHandler를 상속받은 커스텀 핸들러로 실패시 session에 실패했다는 메세지가 담긴 errorMessage를 설정
- SecurityConfig의 formLogin 파트에 커스텀한 핸들러가 설정된 failureHandler를 추가
- loginForm.html에 로그인 실패시 세션에 추가된 errorMessage를 보여주는 오류 메세지 알림 파트 추가

##### 응답 메세지 수정 + 예외 분리
- 동작에 성공하여 응답 메세지를 보낼때 해당 동작에 대한 구체적인 성공 내용으로 변경
- 성공 내용을 받아 js alert로 출력
- Exception의 하위 클래스인 SQLException, RuntimeException, IOException로 분리하여 어떤 문제가 생겼는지 바로 파악