-- 자동 증가 시퀀스 생성
DROP SEQUENCE seq_bread;
DROP SEQUENCE seq_stock_in;
--DROP SEQUENCE seq_stock_out;
DROP SEQUENCE seq_customer;
DROP SEQUENCE seq_order_req;

CREATE SEQUENCE seq_bread 
START WITH 1 
INCREMENT BY 1 
NOCYCLE NOCACHE;

CREATE SEQUENCE seq_stock_in 
START WITH 1 
INCREMENT BY 1 
NOCYCLE NOCACHE;
/*
CREATE SEQUENCE seq_stock_out
START WITH 1 
INCREMENT BY 1 
NOCYCLE NOCACHE;
*/

CREATE SEQUENCE seq_customer 
START WITH 1 
INCREMENT BY 1 
NOCYCLE NOCACHE;

CREATE SEQUENCE seq_order_req 
START WITH 1 
INCREMENT BY 1 
NOCYCLE NOCACHE;

-- bread 데이터 (테스트 쿼리)
insert into bread values(seq_bread.NEXTVAL, '테스트빵1', 5, 1000);
insert into bread values(seq_bread.NEXTVAL, '테스트빵2', 4, 2000);
insert into bread values(seq_bread.NEXTVAL, '테스트빵3', 3, 3000);
insert into bread values(seq_bread.NEXTVAL, '테스트빵4', 2, 4000);
insert into bread values(seq_bread.NEXTVAL, '테스트빵5', 1, 5000);

-- stock_in 데이터 (테스트 쿼리)
INSERT INTO stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category)
    SELECT seq_stock_in.NEXTVAL, 1, '2021-02-11', exp_date+to_date('2021-02-11'), 50, '', 1 from bread where bread_id=1;
INSERT INTO stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category)
    SELECT seq_stock_in.NEXTVAL, 1, '2021-02-23', exp_date+to_date('2021-02-23'), 50, '', 1  from bread where bread_id=1;
INSERT INTO stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category)
    SELECT seq_stock_in.NEXTVAL, 2, '2021-02-23', exp_date+to_date('2021-02-23'), 20, '', 1  from bread where bread_id=2;
INSERT INTO stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category)
    SELECT seq_stock_in.NEXTVAL, 3, '2021-02-24', exp_date+to_date('2021-02-24'), 30, '', 1  from bread where bread_id=3;
INSERT INTO stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category)
    SELECT seq_stock_in.NEXTVAL, 4, '2021-02-25', exp_date+to_date('2021-02-25'), 40, '', 1  from bread where bread_id=4;
INSERT INTO stock_in(stock_id, bread_id, prod_date, exp_date, quantity, out_date, category)
    SELECT seq_stock_in.NEXTVAL, 5, '2021-02-26', exp_date+to_date('2021-02-26'), 50, '', 1  from bread where bread_id=5;
   

   
   
    
COMMIT;