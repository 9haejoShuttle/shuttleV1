# User 도메인 개발 순서

## 1. User엔티티 생성

## 2. Security설정

## 3. PasswordEncoder Bean 등록

SecurityConfig에 PasswordEncoder Bean을 등록했을 때는 정상 작동하지 않았다.

## 4. UserController 생성

- 회원가입, 로그인 처리하는 메서드 생성

    GET - login(), GET/POST - signup()

    로그인 POST는 설정만 해두면 시큐리티에서 진행한다.

## 5. UserSignupRequestDto - 회원가입 시 필요한 데이터만을 받아오는 Dto

`org.hibernate.validator.constraints`

`javax.validation.constraints`

두 개의 패키지를 이용해서 최소 조건 설정

## 6. SignupValidator

`org.springframework.validation.Validator`인터페이스를 구현해서 이미 존재하는 아이디인지, 입력한 비밀번호와 비밀번호 Confirm에 입력한 값이 같은지 체크한다. 만약 에러가 있다면 Errors객체에 담아서 View로 보내서 어떤 에러인지 확인할 수 있도록 한다. Controller에서 
```@InitBinder```를 이용해  SignupRequestDto객체를 받아올 때마다 이 Validator가 작동하도록 만들었다.

## 7. UserService - 유저 관련된 비즈니스 로직을 처리

- 회원가입, 로그인 비즈니스 로직 처리 메서드 생성

    회원가입 시, 패스워드 이코딩 진행

    회원가입을 완료하면 자동으로 로그인 되도록 설정(UsernamePasswordAuthenticationToken)

- 시큐리티 로그인 POST처리를 위한 UserDetailsService인터페이스 구현

## 8. UserRepository

- JpaRepository를 상속
- 회원가입 시 아이디 중복 체크를 위한 existsByPhone() 메서드 선언
- 로그인 시 아이디 체크를 위한 findByPhone() 선언

## 9. @CurrentUser, UserAccount 현재 사용자 참조


```@AuthenticationPrincipal``` 애노테이션을 이용해서 현재 로그인 중인 사용자의 정보를 받아올 수 있다. 
하지만 그렇게 해서 받아오는 값은 회원 객체가 아닌 단순 문자열이다. 회원에 관한 정보를 좀더 편하게 사용하기 위해서 
```@CurrentUser``` 애노테이션을 만들었다.

```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
    public @interface CurrentUser {
    }

```

```@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")``` 이 부분이 가장 중요한 부분이다. @CurrentUser는 컨트롤러 메서드 파라미터로 User를 선언하고 그 앞에 붙인다.```main(@CurrentUser User user)``` 현재 해당 페이지를 요청한 클라이언트가 로그인된 유저가 아니라면 `anonymousUser` 문자열을 받아올 수 있는데, 그럴 떄 User객체는 null이 되고 anonymousUser가 아닌, 로그인 중일 경우에는 user객체로 초기화 한다.

### 10. UserAccount

위 @CurrentUser를 사용하려면 스프링 시큐리티가 제공하는 User와 매칭하는 클래스가 필요하다.
rg.springframework.security.core.userdetails패키지가 제공하는 User클래스를 상속 받아서 UserAccount클래스를 만들었다. UserAccount는 우리 유저 도메인 shuttle.User와 스프링 시큐리티가 제공하는 userdetails.User 사이에서 서로를 연결하는 일종의 어댑터다. @CurrentUser 애노테이션을 사용할 때, 실제로 User객체에 들어오는 것이 이 UserAccount다.

## 11. Remeber-me 자동 로그인

- SecurityConfig에 자동 로그인을 설정한다.
- 아래 메서드를 빈으로 등록

```java
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
```

- JdbcTokenRepositoryImpl에서 요구하는 persistent_logins 테이블에 맞는 엔티티를 생성한다.

- 화면에 체크박스로 remember-me 추가해주면 끝.
