/* 재고 */
DROP TABLE stock_in 
	CASCADE CONSTRAINTS;

/* 손님 */
DROP TABLE customer 
	CASCADE CONSTRAINTS;

/* 주문 */
DROP TABLE order_req 
	CASCADE CONSTRAINTS;

/* 빵 */
DROP TABLE bread 
	CASCADE CONSTRAINTS;

/* 재고 */
CREATE TABLE stock_in (
	stock_id NUMBER NOT NULL, /* 재고ID */
	bread_id NUMBER DEFAULT 1 NOT NULL, /* 빵 분류 ID */
	prod_date DATE DEFAULT SYSDATE NOT NULL, /* 제조일 */
	exp_date DATE NOT NULL, /* 유통기한 */
	quantity NUMBER DEFAULT 0, /* 수량 */
	out_date DATE, /* 출고일 */
	category NUMBER DEFAULT 1 NOT NULL /* 분류 */
);

CREATE UNIQUE INDEX PK_stock_in
	ON stock_in (
		stock_id ASC,
		bread_id ASC
	);

ALTER TABLE stock_in
	ADD
		CONSTRAINT PK_stock_in
		PRIMARY KEY (
			stock_id,
			bread_id
		);

/* 손님 */
CREATE TABLE customer (
	customer_id NUMBER NOT NULL, /* 고객번호 */
	price_sum NUMBER, /* 구매금액 */
	pur_date DATE DEFAULT SYSDATE /* 주문일 */
);

CREATE UNIQUE INDEX PK_customer
	ON customer (
		customer_id ASC
	);

ALTER TABLE customer
	ADD
		CONSTRAINT PK_customer
		PRIMARY KEY (
			customer_id
		);

/* 주문 */
CREATE TABLE order_req (
	order_id NUMBER DEFAULT 1 NOT NULL, /* 주문번호 */
	bread_id NUMBER DEFAULT 1 NOT NULL, /* 빵 분류 ID */
	customer_id NUMBER NOT NULL, /* 고객번호 */
	order_quan NUMBER, /* 구매수량 */
	sell_check NUMBER(1) /* 판매확인 */
);

CREATE UNIQUE INDEX PK_order_req
	ON order_req (
		order_id ASC
	);

ALTER TABLE order_req
	ADD
		CONSTRAINT PK_order_req
		PRIMARY KEY (
			order_id
		);

/* 빵 */
CREATE TABLE bread (
	bread_id NUMBER DEFAULT 1 NOT NULL, /* 빵 분류 ID */
	name VARCHAR2(40) NOT NULL, /* 빵 이름 */
	exp_date NUMBER NOT NULL, /* 유통기한 */
	price NUMBER DEFAULT 0 /* 가격 */
);

CREATE UNIQUE INDEX PK_bread
	ON bread (
		bread_id ASC
	);

ALTER TABLE bread
	ADD
		CONSTRAINT PK_bread
		PRIMARY KEY (
			bread_id
		);

ALTER TABLE stock_in
	ADD
		CONSTRAINT FK_bread_TO_stock_in
		FOREIGN KEY (
			bread_id
		)
		REFERENCES bread (
			bread_id
		);

ALTER TABLE order_req
	ADD
		CONSTRAINT FK_customer_TO_order_req
		FOREIGN KEY (
			customer_id
		)
		REFERENCES customer (
			customer_id
		);

ALTER TABLE order_req
	ADD
		CONSTRAINT FK_bread_TO_order_req
		FOREIGN KEY (
			bread_id
		)
		REFERENCES bread (
			bread_id
		);
COMMIT;