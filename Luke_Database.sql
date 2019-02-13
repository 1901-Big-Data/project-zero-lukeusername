
CREATE TABLE registered_user (
    user_id number(10) PRIMARY KEY,
    username varchar2(255) not null UNIQUE,
    pass varchar2(255) not null,
    is_admin number(1) not null,
    last_name varchar2(255) not null,
    first_name varchar2(255) not null,
    email_address varchar2(255) not null,
    physical_address varchar2(255) not null,
    usa_state varchar2(255) not null,
    city varchar2(255) not null
);

CREATE TABLE open_accounts (
    account_id number(10) PRIMARY KEY,
    user_id number(10) not null, 
    account_name varchar2(255) not null,
    balance number(11,2) CHECK (balance >= 0),
    FOREIGN KEY (user_id) REFERENCES registered_user (user_id) ON DELETE CASCADE
);

CREATE SEQUENCE registered_user_seq
    start with 1
    increment by 1;

CREATE OR REPLACE TRIGGER registered_user_trig
BEFORE INSERT ON registered_user
FOR EACH ROW
BEGIN 
    IF :new.user_id IS NULL THEN
    SELECT registered_user_seq.nextval INTO :new.user_id from dual;
    END IF;
END;    
/

CREATE SEQUENCE account_id_seq
    start with 1
    increment by 1;
    
CREATE OR REPLACE TRIGGER account_trig
BEFORE INSERT ON open_accounts
FOR EACH ROW
BEGIN 
    IF :new.account_id IS NULL THEN
    SELECT account_id_seq.nextval INTO :new.account_id from dual;
    END IF;
END;    
/

CREATE OR REPLACE PROCEDURE withdraw(account_id_in number, with_amount_in IN number, new_bal_out OUT number) IS
BEGIN 
    UPDATE open_accounts
    SET balance = (balance - with_amount_in)
    WHERE account_id = account_id_in;
    
    SELECT balance INTO new_bal_out
    FROM open_accounts 
    WHERE account_id = account_id_in;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE deposit(account_id_in number, dep_amount_in IN number, new_bal_out OUT number) IS
BEGIN 
    UPDATE open_accounts
    SET balance = (dep_amount_in + balance)
    WHERE account_id = account_id_in;
    
    SELECT balance INTO new_bal_out
    FROM open_accounts 
    WHERE account_id = account_id_in;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE createAccount(user_id_in number, account_name_in IN varchar2) IS
temp_balance number;
BEGIN   
temp_balance := 0;
    INSERT INTO open_accounts (user_id, account_name, balance) 
    VALUES(user_id_in, account_name_in, temp_balance);
    commit;
END;
/
    
CREATE OR REPLACE PROCEDURE addUser(username_in IN varchar2, password_in IN varchar2, 
    is_admin_in number, last_name_in IN varchar2, first_name_in IN varchar2, email_address_in IN varchar2, 
    physical_address_in IN varchar2, usa_state_in IN varchar2, city_in IN varchar2, success_out out number) IS
BEGIN   
    INSERT INTO registered_user (username, pass, is_admin, last_name, 
        first_name, email_address, physical_address, usa_state, city) 
    VALUES(username_in, password_in, is_admin_in, last_name_in, first_name_in, email_address_in, physical_address_in, 
        usa_state_in, city_in);
    success_out := 1;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE login(username_in IN varchar2, password_in IN varchar2, user_id_out out number, 
    is_admin_out out number, last_name_out out varchar2, first_name_out out varchar2, email_address_out out varchar2, 
    physical_address_out out varchar2, usa_state_out out varchar2, city_out out varchar2) IS
    temp_password varchar2(200);
    temp_user_id number(10);
    temp_is_admin number(10);
BEGIN
    select pass, user_id, is_admin into temp_password, temp_user_id, temp_is_admin from registered_user where username = username_in;
    if temp_password = password_in then 
        select user_id, is_admin, last_name, first_name, email_address, physical_address, usa_state, city 
        into user_id_out, is_admin_out, last_name_out, first_name_out, email_address_out, physical_address_out, usa_state_out, city_out  
        from registered_user where username = username_in;
    else
        user_id_out := 0;
        is_admin_out := 0;
        last_name_out  := 'empty';
        first_name_out  := 'empty';
        email_address_out  := 'empty';
        physical_address_out  := 'empty';
        usa_state_out  := 'empty';
        city_out  := 'empty';
    end if;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE getUsername(username_in IN varchar2, available out number) IS
BEGIN
    SELECT COUNT(1)
        into available
    FROM registered_user
    WHERE username = username_in;
    commit;
END;
/
DELETE FROM registered_user WHERE user_id = 666;
INSERT INTO registered_user 
VALUES(1234, 'superuser', 'supersafe', 1, 'user', 'super', 'admin@power.com', 'everywhere', 'all', 'cityville');
select * from registered_user;
select * from open_accounts;
SELECT * FROM open_accounts JOIN registered_user on registered_user.user_id = open_accounts.user_id;
DELETE FROM registered_user WHERE user_id = 50; 
