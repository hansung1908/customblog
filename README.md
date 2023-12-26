# customblog
- 다양한 기술을 접목한 블로그 기반 프로젝트

### spring boot
- jpa, spring security, oauth2 client, aop, validation, lombok

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
