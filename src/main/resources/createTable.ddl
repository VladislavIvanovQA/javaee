create table USERS
(
	USERNO NUMBER(4) not null
		constraint USERNO
		primary key,
	ULOGIN VARCHAR2(10) default NULL not null,
	UPASSWORD VARCHAR2(10) default NULL not null,
	UIO VARCHAR2(200) default NULL not null,
	UPHONE VARCHAR2(10),
	UEMAIL VARCHAR2(100),
	USEX VARCHAR2(6) default NULL not null
)
/

create table ENUMERATOR
(
	EID NUMBER(4) not null
		constraint FK_USERNO
			references USERS,
	EPUBLICATION NUMBER default 0,
	ESUBSCRIBE NUMBER default 0,
	EFOLLOWERS NUMBER default 0,
	USERNO NUMBER(4) default NULL not null
)
/

create unique index PK_EID
	on ENUMERATOR (EID)
/

create procedure getNameForMaxPhone
	(NameForMaxPhone out varchar)
is
	begin
		select max(UIO) into NameForMaxPhone
		from SCOTT.Users e join (select max(UPHONE) as maxUPHONE from USERS) s
						 on s.maxUPHONE = e.UPHONE;
	end getNameForMaxPhone;
/

create procedure setRandomIOUser(
	newIOUser in varchar)
is
	begin
		UPDATE USERS SET UIO = newIOUser
		where USERNO = (SELECT * FROM (
																	SELECT USERNO from SCOTT.USERS
																	ORDER BY DBMS_RANDOM.VALUE) where rownum < 2);
	end;
/



-- insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)
-- values (1, 'admin', '12345', 'Ivanov Vladislav', '9000000001', 'test@test.ru', 'Male')/
--
-- insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)
-- values (2, 'user1', '12345', 'Ivanov Ivan', '9000000002', 'test1@test.ru', 'Male')/
--
-- insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)
-- values (3, 'test', '12345', 'Ivanov Sergey', '9000000003', 'test2@test.ru', 'Male')/
--
-- insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)
-- values (4, 'tester', '12345', 'Ivanov Valera', '9000000004', 'test3@test.ru', 'Male')/
--
-- insert into USERS(USERNO, ULOGIN, UPASSWORD, UIO, UPHONE, UEMAIL, USEX)
-- values (5, 'mainAdmin', '12345', 'Ivanov Vitaliy', '9000000005', 'test4@test.ru', 'Male')

