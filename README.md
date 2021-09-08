### Spring Batch Example

#### 목표
가입한 회원 중 1년이 지나도록 상태 변화가 없는 회원을 휴면 회원으로 전환하는 배치 작성

#### 작업 순서
1. DB에 저장된 데이터 중 1년간 업데이트 되지 않은 사용자를 찾는 itemReader를 구현한다.
2. 대상 사용자 데이터의 상탯값을 휴면으로 전환하는 프로세스로 itemProcessor를 구현한다.
3. 상태값이 변환된 휴면회원을 DB에 저장하는 ItemWriter를 구현한다.

#### 참고자료
https://cheese10yun.github.io/spring-batch-basic/
https://spring.io/guides/gs/batch-processing/
https://github.com/jojoldu/spring-batch-in-action/blob/master/2_Job생성.md