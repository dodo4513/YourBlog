tag : java

----

### YourBlog
블로그를 개발하자!

###### 일정
1. 스펙정의 18.11.01 ~
2. 1차 프로토타입 개발(ASAP)
    
###### 개발환경
* Spring Boot 2.*
* Spring JPA, JOOQ, Mybatis(TBD)
* H2, MySQL 
* openjdk + docker

###### 기능?특징?
* 설치형 블로그
    * 기본 프론트 템플릿도 하나 있어야겠지?
* 마크다운 기반 에디터
    * TUI editor http://ui.toast.com/tui-editor/
* SEO
* 포스트
    * 시리즈
    * 카테고리
    * 태그
    * 검색
    * 백업
* 플러그인(?, 확장 가능성..)
* 소통
    * 댓글
    * 알림
        * email ?
* 프로필
* 다국어
* 로그
    * google analysis
    * today
    
###### 리서치
* 설치형 블로그 (https://goo.gl/5cRW1A)
    * 워드프레스 => php
* 설치형 노트 (http://jupyter.org/)
    * Jupyter notebook
* 마크다운 기반 에디터  React + GraphQL (https://goo.gl/4x8XtZ)
* blog : medium(https://medium.com/), hashnode(https://hashnode.com/)

###### 고민

> 차주 회의

* DB 자료 import, export 방식에 대해 논의 되어야함. 

- - -

> 18.12.06 회의

* 카테고리, 태그을 어떻게 활용하면 좋을까

* 디비가 필요한가? - 거의다 읽는 데이터 일거같은데.. -> 차후 들어갈 기능을 생각하면 디비 쓰는게 좋을듯
* 어드민 사이트가 필요하구나, 단순히 글 쓰는 것 뿐 아니라 태그, 카테고리 등을 관리할 페이지
    * 기획을?
    * 어드민용 API 는(글, 프로필 등록, 태그 수정 등) 어떻하지? 프론트가 바라보는 API 처럼 만들어야할까 
    -> 프론트에서 어떤 기능을 쓸지 모르니 다 지원하는게 좋을듯 
* 프론트는 다른 프로젝트로 만들어야할 것 같다. 한 다음 만남 이후 부터 조금씩? -> 다음 만남부터 조금씩?

- - -

