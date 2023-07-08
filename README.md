# customblog
- 다양한 기술을 접목한 블로그 기반 프로젝트
- 스프링 시큐리티로 로그인 요청을 가로챈 후 로그인을 진행하여 성공하면 UserDetails타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소의 저장
- 그래서 principalDetailService에서 username이 db에 있는지 검증 후 principalDeatils로 return하여 userDetails를 통한 세션을 설정
- 원래 비밀번호 비교를 위해 authenticationManager에 userDetailService와 passwordEncoder를 반환하는 함수를 짜야했는데 최신 버전에선 service와 encoder를 선언만 해줘도 알아서 생성
- 페이징 처리를 위해서 list<?> 대신 page<?> 객체를 통해 리스트 데이터를 처리
- @RequestBody를 통해 json 데이터를 받아옴, 없으면 raw 데이터 받음
- 회원 정보 수정후 바로 수정된 회원정보를 보기 위해서 securityConfig에 authenticationManager를 가져오는 빈을 설정 후
- authenticationManager에 수정된 정보를 담은 토큰을 생성하여 authentication에 저장, 이후 이를 SecurityContextHolder에 설정하여 세션을 등록
- 카카오 로그인 구현시 인가 코드(버튼 클릭 링크)를 통해 인증 처리 후 callback주소를 컨트롤러에서 처리하여 토큰을 가져와야 로그인 가능
- 자동 로그인 기능 구현을 위해 수정된 정보를 바로 갱신할때 쓰던 SecurityContextHolder 코드를 가져와 SecurityContext oauth 로그인 데이터를 추가하여 session에 등록하는 코드로 재구현
- 리다이렉션 uri 작성시 ./login/oauth2/code/.로 고정, 앞은 도메인 주소이며 뒤는 로그인하는 사이트(google, kakao 등)
- spring에 기본적으로 탑재된 jackson 라이브러리는 오브젝트를 json으로 바꿔주는데 이때 model에 저장된 내용을 getter를 통해 가져옴
- board 호출시 참조하고 있는 reply가 board를 참조하여 무한 참조 발생시 @jsonignoreproperties({"board"})를 board에 참조하는 reply에 설정하여 reply에서 참조하는 board가 다시 참조되는 것을 막아줌
