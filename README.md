### YourBlog
YourBlog는 유용한 블로그의 기능들을 모아 REST API 를 제공하는 서버입니다. 이를 지원하는 테마(프론트)를 사용하거나, 직접 개발해 자신만의 블로그를 빠르게 만들 수 있습니다.

#### 특징
* 설치형 자바 블로그
* REST API 기반으로 블로그에 특화된 기능 제공
* 블로그 관리를 위한 Admin 사이트
* 지속적인 포스팅 동기 부여를 위한 요소
-  - - 
#### 프로젝트 목표 일정

| - | 일시 | 목표 | 비고 | 
| --- | --- | --- | --- |
| 1 | 18.11.01 ~ 19.01.31  (15주) | 블로그 스펙 정의 및 간단한 프로토타입 구현 |* API(포스트, 태그, 카테고리), Admin 페이지 구현 </br> * 기존 Jekyll 블로그의 간단한 기능들 우선 개발</br>* Google cloud platform 환경 세팅 |
| 2 | ~ 19.03. 31 (9주)| *  기본 블로그 테마(프론트) 기획 및 개발  | * Vue.js |
| 3 | ~ 19.04.30 (4주) | * SEO, 테스트 등 블로그 오픈 준비 | * SPA로 SEO를 깔끔하게 적용할 수 있을까 |
| 4 | 19.05.01 | YourBlog 1.0 블로그 서비스 시작 |
| 5|  ~ | Admin 추가 기능 개발 | 이후 일정은 추후 기획
- - -    
#### 환경
* Spring Boot 2.*, JPA , Mysql
* Docker 
* Admin page template: [ElaAdmin](https://github.com/puikinsh/ElaAdmin) [DEMO](https://colorlib.com/polygon/elaadmin/index.html)
- - -
#### 테마(프론트) 개발
함께 테마를 개발할분을 찾고 있습니다.  YourBlog REST API으로 자신의 블로그 프론트 만든다고 생각하면 됩니다. 언어나 환경은 무엇이든 좋습니다. 메일로 문의 부탁드립니다.

* 누구나 사용할 수 있도록 Github Repository 는 공개해야합니다.
* README.md에 친절한 설치 가이드 작성을 부탁드립니다.
* 전체 사이트 하단에 YourBlog Repository uri 를 삽입해야합니다.
- - -
#### 이슈
* email 발송 기능
* CDN
* SQL 튜닝(우선 인덱스...)
- - -
* 카테고리 no 유지하기
* 카테고리에 글 매핑하기
* 카테고리 저장 API 배열로 받도록
* 카테고리 저장된거 로드 하기 개발해야해!
