## 최소 MVP 테이블 구조


### 1. users  - 사용자

- **인증 방식 Oauth 2.0** 사용

<br>

### 전체 흐름

**시나리오: 구글 OAuth 로그인 + JWT + 이후 API 호출**

1. **프론트에서 로그인 버튼 클릭**
  - 사용자가 https://localhost:8080/oauth2/authorization/google 로 이동
  - 이 URL은 Spring Security가 자동으로 제공하는 OAuth2 로그인 엔드포인트

<br> 

2. **Spring Security가 구글 로그인 페이지로 리다이렉트**
   - Security 필터 체인이 요청을 가로채고,
   - 내부 OAuth2 필터가 구글 로그인 URL로 보낸다.

<br>

3. **사용자가 구글 로그인 성공 → 구글이 서버 콜백 호출**
   - 구글이 http://localhost:8080/login/oauth2/code/google 로 code 를 넘겨서 다시 돌려줌
   - 이 URL도 우리가 컨트롤러로 만든 게 아니라, Spring Security가 처리

<br>

4. **Spring Security 내부에서 토큰 교환 → 사용자 정보 조회**
   - 구글에서 Access Token을 받고,
   - `DefaultOAuth2UserService.loadUser(...)` 를 호출하는데,
   - 우리가 확장한 `CustomOAuth2UserService.loadUser(...)` 가 여기서 실행

<br>

5. **CustomOAuth2UserService 역할**
   - provider(`"google"`) + providerId(`sub`) 로 DB에서 `UserEntity` 조회
   - `없다` → `회원가입` (`register(...)`)
   - `있다` → `마지막 로그인 시간 갱신`
   - 그 결과를 `CustomOAuth2User` 로 감싸서 `Spring Security` 에게 돌려줌

<br>

6. **로그인 성공 시: OAuth2AuthenticationSuccessHandler 동작**
   - `Authentication` 안에 있는 `CustomOAuth2User` 를 꺼낸다.
   - `JwtProvider` 이용해서 `AccessToken + RefreshToken` 생성
   - `app.oauth2.redirect-uri` (예: `http://localhost:3000/oauth2/redirect`) 로
   - `?accessToken=...&refreshToken=...` 형태로 리다이렉트

<br>

7. **프론트가 토큰 받음**
   - 쿼리스트링의 accessToken / refreshToken 을 파싱해서 저장 (localStorage 등)
   - 이후 API 요청마다 `Authorization: Bearer {accessToken}` 헤더 포함해서 백엔드 호출

<br>

8. **이후 모든 API 요청은 JwtAuthenticationFilter가 책임**
   - Spring Security 필터 체인에서 컨트롤러 호출 전에 `JwtAuthenticationFilter` 실행
   - Authorization 헤더에서 JWT 추출
   - `JwtProvider.validate(token)` 검증
   - 유효하면 `getUserId(token)` 으로 userId 꺼내고, `DB`에서 `UserEntity` 조회
   - `SecurityContextHolder` 에 인증 객체(`UsernamePasswordAuthenticationToken`) 세팅
   - 그 다음에야 컨트롤러 로직이 실행

<br>

9. **컨트롤러에서는 @AuthenticationPrincipal 등으로 현재 사용자 접근**
   - `예: /api/v1/auth/me` 에서 현재 로그인한 사용자 정보 리턴 가능

<br>

10. **로그인 실패 시**
    - `OAuth2AuthenticationFailureHandler` 가 호출됨
    - `app.oauth2.redirect-uri` 로 `?error=...` 붙여서 리다이렉트

---

### 2. menu   - 판매 가능한 메뉴

---

### 3. Orders - 주문 정보

---

### 4. Wallet - 결제

---

### 5. Coupon - 쿠폰


