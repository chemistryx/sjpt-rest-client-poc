# sjpt-rest-client-poc

세종대학교 학사정보시스템([sjpt.sejong.ac.kr](https://sjpt.sejong.ac.kr)) REST 클라이언트 POC

## 흐름

1. 포탈 로그인 → ssotoken 발급
2. SSO 연동 → JSESSIONID 발급
3. 사용자 정보 초기화
4. 메뉴 불러오기
5. 프로그램 Role 등록
6. 데이터 조회

## 실행

```bash
export SJPT_ID=<포털 아이디>
export SJPT_PASSWORD=<포털 비밀번호>
./gradlew run
```
