- POST - USER 테이블 관계를 맺어주지 않으면 Post에 저장할 때 SELECT를 2번 날려야 하는 거 아닌가? USER를 먼저 조회한 다음, 그 ID를 POST INSERT 시에 넣어줘야 하는 거 아닌가.

- CATEGORY에서 POST를 조회해야 할까? CATEGORY별로 보여준다면 카테고리 테이블이 자신을 사용하는 POST테이블을 조회할 수도 있어야 하는 거 아닐까?
 