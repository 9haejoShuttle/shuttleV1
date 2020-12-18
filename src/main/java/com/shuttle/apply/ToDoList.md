# **Apply To Do List**

## 화면
- [ ] 디자인 적용
- [ ] List 페이지
- [ ] 지도에서 검색 이식
- [ ] List에 삭제버튼 추가

## ApplyController
- [ ] list를 10개 단위로 sort하여 보여주기
- [ ] CRUD 작업 / Update 작업은 필요하지 않음
- [X] 삭제 조건을 검사해야 함.

## ApplyDTO 
-[ ] ApplyDTO dataToDomain의 ArrivalTime조정해주기 
    - 방법은 두가지가 있는데
        1. new Time 메서드의 Deprecated 생성자를 이용해주거나(하지만 맘에들지 않아...)
        2. Time so 다른 처리를 통하여 바로 Time 생성이 적용될 수 있도록 하거나(java.sql.Time 메서드 까봐야함)

### 12월 18일 작업할 내용
- [x] 서버 단에서 Remove Test까지 끝내기
- [x] Controller에서 신청된 노선 목록보여주기위한 pageable 만들어 줄 것...

### 12월 19일 작업할 내용
- [ ] pageable 만든 페이지 테스트


## 현재 고민 중인 것
register에서 post작업 후에 redirect 해주는 것
