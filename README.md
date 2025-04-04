# 🧥 Closetlog

Closetlog는 사용자 맞춤형 옷장 관리와 패션 리뷰를 제공하는 웹 애플리케이션입니다.  
회원가입, 이메일 인증, 로그인 보안은 Spring Security 기반으로 구현되어 있으며,  
추후 사용자 패션 데이터를 활용한 스타일 추천 및 리뷰 기반 커뮤니티 기능이 추가될 예정입니다.

👉 나만의 옷장을 관리하고, 일상 코디를 기록하며 스타일을 정리하고 싶은 사용자에게 유용한 서비스입니다.

---

## 📌 주요 기능

| 기능                     | 설명                                                         | 상태    |
|------------------------|------------------------------------------------------------|--------|
| 회원가입                | 이메일 인증 후 가입 가능, 비밀번호 암호화 저장               | ✅ 완료 |
| 이메일 인증             | 인증 코드 발송 및 검증 (Spring Mail 활용)                    | ✅ 완료 |
| 로그인/로그아웃         | Spring Security 기반, 세션 관리 및 홈 리다이렉션              | ✅ 완료 |
| 로그인 실패 처리        | 실패 시 메시지 출력 및 로그인 폼 유지                         | ✅ 완료 |
| 게시판 기능             | CRUD, 권한 체크, 수정/삭제 알림 처리                         | ✅ 완료 |
| 댓글 기능               | 작성/수정/삭제, 권한 체크 및 리다이렉션 처리                  | ✅ 완료 |
| 홈페이지 기본 구조 제작 | 메인 페이지 및 기본 템플릿 구성                               | 🛠 진행 중 |

---

## 🛠 기술 스택

- **Backend**: Java 17, Spring Boot 3.x, Spring Security, Spring Mail
- **Database**: MySQL
- **Frontend**: Thymeleaf, HTML/CSS *(Tailwind CSS 적용 예정)*
- **Build Tool**: Gradle
- **DevOps**: GitHub, IntelliJ IDEA

---

## 🗂 프로젝트 구조

```
/config → 보안 설정, 인증 서비스 
/controller → 회원가입, 로그인 처리 컨트롤러 
/domain → User, Board, Comment 등 도메인 객체 
/repository → DB 인터페이스 (현재 MySQL) 
/service → 사용자 및 게시판/댓글 서비스 로직 
/resources/templates → HTML 템플릿 (login, register, list, view, write 등) 
```

---

## 💡 향후 계획

- 사용자 옷장 등록 및 관리 기능
- 스타일 추천 알고리즘 적용 
- 옷장 기반 코디 관리 기능
- AWS 배포

## 🔗 관련 링크

- 📌 [1편 - 회원가입/로그인 기능 구현](https://velog.io/@pjw200116/Spring-Boot%EB%A1%9C-%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0-%ED%8C%A8%EC%85%98-%EB%A6%AC%EB%B7%B0-%EA%B2%8C%EC%8B%9C%ED%8C%90-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-1)
- 📌 [2편 - 게시판/댓글 기능 구현](https://velog.io/@pjw200116/ClosetLog-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-2-%EA%B2%8C%EC%8B%9C%ED%8C%90-%EB%B0%8F-%EB%8C%93%EA%B8%80-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84)
