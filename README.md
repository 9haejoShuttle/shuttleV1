# 🚒 구해줘 셔틀





# package shuttleV1.user docs

## /signup	-	회원가입

​	아이디, 비밀번호, 이름 등 유효한 값인지, 최소한의 조건을 만족하는지를 서버에서 Validation한다. 가입에 성공하면 자동으로 로그인한다.

## /login	-	로그인

​	클라이언트에서 CSRF토큰과 계정으로 로그인을 요청하면 스프링 시큐리티가 처리한다. 이 기능을 이용하려면 UserDetailsService의 loadByUsername()메소드를 구현해야 한다. 이 메소드는 데이터베이스에서 유저 정보를 읽어와서 한다. 단순히 SELECT한 결과를 반환하면 될 것 같지만 loadByUsername()메소드의 선언부는 이렇다.   

```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException; 
```

 보다시피 UserDetails라는 타입으로 리턴해야만 한다. 따라서 User엔티티를 UserDetails로 변환하는 작업이 필요하다. 그래서 만든 것이 UserAccount다.

```java
...
import org.springframework.security.core.userdetails.User;
...
@Getter
public class UserAccount extends User implements Serializable {
    private com.shuttle.domain.User user;
    public UserAccount(com.shuttle.domain.User user) {
        super(user.getPhone(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoleKey())));
        this.user = user;
    }
}
```

 UserAccount클래스를 이용해서 직접 만든 shuttleV1.domain.User클래스를 UserDetails타입으로 바꾸어주는 작업을 한다. UserAccount가 상속하는 security.core.userdetails.User는 스프링 시큐리티가 제공하는 클래스다. (두 클래스의 이름이 같아서  패키지명까지 붙여준 것이다)  security.core.userdetails.User클래스는 UserDetails인터페이스를 구현하는 클래스다. 이 클래스를 이용하면 직접 인터페이스를 구현하지 않아도 간단하게 UserDetails타입으로 만들어줄 수 있다.

 이건 security.core.userdetails.User클래스에 정의된 생성자다.

```java
public User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(username, password, true, true, true, true, authorities);
	}
```

 생성자 파라미터를 보면 아이디, 비밀번호, 권한을 받는다. 이 정보를 입력해주면 간단하게 shuttleV1.domain.User클래스를 UserDetails타입으로 변환할 수 있다.

loadByUsername()메소드의 실행 순서를 정리하면 이렇다.

1. 파라미터의 인자로 들어온 username을 가진 유저를 데이터베이스에서 읽어온다.
2. 비활성화된 유저인지 체크한다.
3. DB에서 읽어온 유저 정보를 가지고 UserAccount 오브젝트를 생성해서 반환한다.

 그럼 스프링 시큐리티가 내부적으로 사용자가 입력한 정보와 UserAccount의 정보를 비교해서 로그인을 처리한다.

## /forgotPassword	-	비밀번호 분실

​	비밀번호를 분실한 경우 핸드폰 번호를 입력해서 인증번호를 받는 방식으로 구현하고 싶었으나 무료 SMS API를 찾지 못했다. 간략한 방식으로 구현했다.

 절차

1. 아이디(핸드폰 번호)를 입력하고 인증번호를 요청한다.(/sendToken)
2. 서버에서 유효한 번호인지 검사한다. 
3. 개발자 도구 콘솔에 인증번호를 띄운다.
4. 받은 인증번호를 화면에 입력하고 로그인을 요청한다.
5. 서버에서 보낸 인증번호와 사용자가 입력한 인증 번호가 같은 지 체크한다.(/tokenVerified)

## /mypage/password	-	비밀번호 변경

비밀번호 변경 시 기존 비밀번호를 따로 체크하지 않는다.

1. 새로운 비밀번호를 입력하고 서버로 변경 요청을 보낸다.
2. 서버는 패스워드와 컨펌 패스워드가 일치하는지 확인하고 비밀번호를 변경한다.
3. 성공적으로 변경하면 다시 클라이언트가 logout요청을 보내서 다시 로그인을 할 수 있도록 한다.

- 서버에서 클라이언트의 세션을 지워 로그아웃처리까지 한 번에 해서 결과를 반환하면 3번까지 갈 필요가 없지만 방법을 몰라서 일단 다시 요청하는 방식으로 구현했다.

## /mypage/account	-	회원 탈퇴(비활성화)

 회원이 실제로 탈퇴를 하면 실제 DB에서 데이터를 지우는 것이 아니라 비활성화 하는 방식으로 구현했다. 탈퇴한 계정은 비활성화(disabale=true)되고 그 계정으로는 로그인할 수 없다.

## /mypage/payment	-	결제 목록(미완)

​	아임포트API와 연동하여 결제하거나, 환불할 수 있도록 했다.

1. 아임포트API로 결제 요청을 보낸 다음 돌아오는 결과를 DB에 저장한다.
2. 아임포트API로 환불 요청을 보내서 처리되면 결제 DB를 업데이트해서 환불이 되었다고 명시한다(cancel=true, cancel_date=now())
3. 지난 결제 목록을 모두 확인할 수 있음(취소 내역 포함)

 지금은 결제와 환불 기능만 된다. 실제로 어떤 결제해야 하는 내역서를 만들어서 사용자에게 보내서 결제하도록 유도하는 기능은 구현되지 않았다.

## 자동 로그인

 스프링 시큐리티가 제공하는 remember-me를 기능을 이용하여 자동로그인을 구현했다. `PersistentTokenRepository`를 빈으로 등록하고, `org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl`클래스와 매칭할 테이블 persistent_logins를 만들어주면 자동 로그인 기능을 사용할 수 있다.



## @CurrentUser

`@AuthenticationPrincipal` 애노테이션을 이용해서 현재 로그인 중인 사용자의 정보를 받아올 수 있다. 하지만 그렇게 해서 받아오는 값은 회원 객체가 아닌 단순 문자열이다. 회원에 관한 정보를 좀더 편하게 사용하기 위해서 `@CurrentUser` 애노테이션을 만들었다.

```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")
    public @interface CurrentUser {
    }
```

`@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : user")` 이 부분이 가장 중요한 부분이다. @CurrentUser는 컨트롤러 메서드 파라미터로 User를 선언하고 그 앞에 붙인다.`main(@CurrentUser User user)` 현재 해당 페이지를 요청한 클라이언트가 로그인된 유저가 아니라면 `anonymousUser` 문자열을 받아올 수 있는데, 그럴 떄 User객체는 null이 되고 anonymousUser가 아닌, 로그인 중일 경우에는 user객체로 초기화 한다.

- 사용 예제

```javascript
@GetMapping("/password")
public ResponseEntity passwordForm(@CurrentUser User user)
```

 이런 메서드가 있다고 가정하자. 로그인한 사용자가 이 메소드에 접근하면 User파라미터에 자동으로 로그인된 사용자 정보가 들어온다. 사용하기 전에 반드시 null체크!

