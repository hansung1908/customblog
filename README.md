# customblog
- 다양한 기술을 접목한 블로그 기반 프로젝트
- 스프링 시큐리티로 로그인 요청을 가로챈 후 로그인을 진행하여 성공하면 UserDetails타입의 오브젝트를 스프링 시큐리티의 고유한 세션 저장소의 저장
- 그래서 principalDeatils를 만들어서 userDetails를 implements하여 설정
