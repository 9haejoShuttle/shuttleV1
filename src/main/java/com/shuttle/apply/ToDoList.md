# **Apply To Do List**

## 화면

- [ ] 디자인 적용
- [ ] List 페이지
- [ ] 지도에서 검색 이식
- [ ] List에 삭제버튼 추가
- [ ] post작업 후에 redirect 해주는 것

## ApplyController

- [ ] list를 10개 단위로 sort하여 보여주기
- [ ] CRUD 작업 / Update 작업은 필요하지 않음
- [X] 삭제조건 검색 필요 없었다...
- [ ] 리스트 조회의 기본은 우선 날짜 역순인데 조건을 줄 수 있도록 수정할 예정

## ApplyDTO

-[X] ApplyDTO dataToDomain의 ArrivalTime조정해주기
    - 방법은 두가지가 있는데
        1. new Time 메서드의 Deprecated 생성자를 이용해주거나(하지만 이 방법은 맘에 들지 않다...)
        2. java.sql.Time 내의 다른 처리를 통하여 바로 Time 생성이 적용될 수 있도록 하자.(이쪽을 나는 좀더 권장)
    -상위 목록에서 2번으로 성공 java.sql.Time.valueOf 메서드 참고

### 12월 18일 작업할 내용

- [x] 서버 단에서 Remove Test까지 끝내기
- [x] Controller에서 신청된 노선 목록보여주기위한 pageable 만들어 줄 것...

### 12월 19일 작업할 내용

- [x] pageable 만든 페이지 테스트

### 12월 21일 작업할 내용
- [x] userId로 select해오는 페이지 만들기 및 테스트
- [X] ApplyDTO dataToDomain의 ArrivalTime조정해주기
## 현재 내가 하고 싶은 것

- [ ] Rest 방식으로 Apply Module 변경하기
    1. 일단 뷰와 연결시키기 위해 ApplyController 에 뷰와 연결해줄 페이지 단 하나만 존재하도록 하고
    2. 나머지는 이전에 댓글과 연결시키던 방식을 적용하여 페이지를 되도록 한 페이지에서 보여줄 수 있도록 구성해볼 것
    3. 모바일에서 볼 때는 리스트를 기본으로 보여주고 신청하면 신청페이지를 띄워주는 형식이 될 듯. 
    4. 기본적으로 보여주는 것은 list 지만 내가 원하는 노선이 없을 때 노선 신청을 하면 좌측으로 뷰를 밀어주고 
       우측에 신청을 위한 뷰를 보여줄 것(댓글 작성 버튼을 눌러야 댓글 칸이 생기는 것과 같은 원리)