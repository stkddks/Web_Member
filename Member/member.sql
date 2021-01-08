-- 얘는 MySQL

CREATE TABLE member(
   id varchar(10) primary key,
   pw varchar(10),
   addr varchar(100),
   tel varchar(100)
)





insert into member (no, id, pw, addr ,tel) values(no, 'aa', 'bbb', '영등포', '010-1111-2222')
insert into member (no, id, pw, addr ,tel) values(no, 'qq', 'www', '강서구', '010-3333-4444')

select *
from member

--만약을 위한 테이블지우기
drop table member
