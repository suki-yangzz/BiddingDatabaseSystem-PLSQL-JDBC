----drop all the tables
drop table Customer cascade constraints;
drop table Administrator cascade constraints;
drop table Product cascade constraints;
drop table Bidlog cascade constraints;
drop table Category cascade constraints;
drop table BelongsTo cascade constraints;
drop table OurDate cascade constraints;

----create tables
create table Customer 
(
	login varchar2(10),
	password varchar2(10),
	name varchar2(20),
	address varchar2(30),
	email varchar2(20),
	constraint pk_customer primary key (login) initially deferred deferrable
 );

create table Administrator 
(
	login varchar2(10),
	password varchar2(10),
	name varchar2(20),
	address varchar2(30),
	email varchar2(20),
	constraint pk_administrator primary key (login) initially deferred deferrable
);

create table Product
(
	auction_id int,
	name varchar2(20),
	description varchar2(30),
	seller varchar2(10),
	start_date date,
	min_price int,
	number_of_days int,
	status varchar2(15) not null,
	buyer varchar2(10),
	sell_date date,
	amount int,
	constraint pk_product primary key (auction_id) initially deferred deferrable,
	constraint fk_product_seller foreign key (seller) references Customer(login) initially deferred deferrable,
	constraint fk_product_buyer foreign key (buyer) references Customer(login) initially deferred deferrable
);

create table Bidlog
(
	bidsn int,
	auction_id int,
	bidder varchar2(10),
	bid_time date,
	amount int,
	constraint pk_bidlog primary key (bidsn) initially deferred deferrable,
	constraint fk_bidlog_auctionid foreign key (auction_id) references Product(auction_id) initially deferred deferrable,
	constraint fk_bidlog_bidder foreign key (bidder) references Customer(login) initially deferred deferrable
);

create table Category
(
	name varchar2(20),
	parent_category varchar2(20),
	constraint pk_category primary key (name) initially deferred deferrable,
	constraint fk_category foreign key (parent_category) references Category(name) initially deferred deferrable
);

create table BelongsTo
(
	auction_id int,
	category varchar2(20),
	constraint pk_belongsto primary key (auction_id, category) initially deferred deferrable,
	constraint fk_belongsto_auctionid foreign key (auction_id) references Product(auction_id) initially deferred deferrable,
	constraint fk_belongsto_category foreign key (category) references Category(name) initially deferred deferrable
);

create table OurDate
(
	c_date date
);



----insert data
insert into Administrator values('admin', 'root', 'administrator', '6810 SENSQ', 'admin@1555.com');

insert into Customer values('user0', 'pwd', 'user0', '6810 SENSQ', 'user0@1555.com');
insert into Customer values('user1', 'pwd', 'user1', '6811 SENSQ', 'user1@1555.com');
insert into Customer values('user2', 'pwd', 'user2', '6812 SENSQ', 'user2@1555.com');
insert into Customer values('user3', 'pwd', 'user3', '6813 SENSQ', 'user3@1555.com');
insert into Customer values('user4', 'pwd', 'user4', '6814 SENSQ', 'user4@1555.com');

insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status, buyer, sell_date, amount) 
	values(0, 'Database', 'SQL ER-design', 'user0', to_date('04-dec-2012/00:00:00','dd-mon-yyyy/HH24:MI:SS'), 50, 2, 'sold', 'user2', to_date('06-dec-2012/00:00:00','dd-mon-yyyy/HH24:MI:SS'),  53);
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status, buyer, sell_date, amount) 
	values(1, '17 inch monitor', '17 inch monitor', 'user0',to_date('06-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 100, 2, 'sold', 'user4', to_date('08-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 110);
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status) 
	values(2, 'DELL INSPIRON 1100', 'DELL INSPIRON notebook', 'user0', to_date('07-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 500, 7, 'underauction');
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status, buyer, sell_date, amount) 
	values(3, 'Return of the King', 'fantasy', 'user1', to_date('07-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 40, 2, 'sold', 'user2', to_date('09-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 40);
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status, buyer, sell_date, amount) 
	values(4, 'The Sorcerer Stone', 'Harry Porter series', 'user1', to_date('08-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 40, 2, 'sold', 'user3', to_date('10-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 40);
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status) 
	values(5, 'DELL INSPIRON 1100', 'DELL INSPIRON notebook', 'user1',to_date('09-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 200, 1, 'withdrawn');
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status, amount) 
	values(6, 'Advanced Database', 'SQL Transaction index', 'user1', to_date('10-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 50, 2, 'underauction', 55);
insert into Product(auction_id, name, description, seller, start_date, min_price, number_of_days, status, amount) 
	values(7, 'Database Management', 'SQL Introduction Book', 'user1', to_date('10-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI;SS'), 50, 30, 'underauction', 55);

insert into Bidlog values(0, 0, 'user2', to_date('04-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 50);
insert into Bidlog values(1, 0, 'user3', to_date('04-dec-2012/09:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 53);
insert into Bidlog values(2, 0, 'user2', to_date('05-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 60);
insert into Bidlog values(3, 1, 'user4', to_date('06-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 100);
insert into Bidlog values(4, 1, 'user2', to_date('07-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 110);
insert into Bidlog values(5, 1, 'user4', to_date('07-dec-2012/09:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 120);
insert into Bidlog values(6, 3, 'user2', to_date('07-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 40);
insert into Bidlog values(7, 4, 'user3', to_date('09-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 40);
insert into Bidlog values(8, 6, 'user2', to_date('07-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 55);
insert into Bidlog values(9, 7, 'user4', to_date('11-dec-2012/08:00:00', 'dd-mon-yyyy/HH24:MI:SS'), 55);

insert into Category(name) values('Books');
insert into Category(name, parent_category) values('Textbooks', 'Books');
insert into Category(name, parent_category) values('Fiction books', 'Books');
insert into Category(name, parent_category) values('Magazines', 'Books');
insert into Category(name, parent_category) values('Computer Science', 'Textbooks');
insert into Category(name, parent_category) values('Math', 'Textbooks');
insert into Category(name, parent_category) values('Philosophy', 'Textbooks');
insert into Category(name) values('Computer Related');
insert into Category(name, parent_category) values('Desktop PCs', 'Computer Related');
insert into Category(name, parent_category) values('Laptops', 'Computer Related');
insert into Category(name, parent_category) values('Monitors', 'Computer Related');
insert into Category(name, parent_category) values('Computer books', 'Computer Related');

insert into BelongsTo values(0, 'Computer Science');
insert into BelongsTo values(0, 'Computer books');
insert into BelongsTo values(1, 'Monitors');
insert into BelongsTo values(2, 'Laptops');
insert into BelongsTo values(3, 'Fiction books');
insert into BelongsTo values(4, 'Fiction books');
insert into BelongsTo values(5, 'Laptops');
insert into BelongsTo values(6, 'Computer Science');
insert into BelongsTo values(6, 'Computer books');
insert into BelongsTo values(7, 'Computer books');

insert into OurDate values (to_date('11-dec-2012/00:00:00', 'dd-mon-yyyy/HH24:MI:SS'));


----alter date type
alter session set nls_date_format='yyyy-mm-dd hh24:mi:ss';

----enable dbms_output
set serveroutput on;

----customer interface
---a browse products
--a1 list rootcategory
create or replace procedure List_RootCategory (listroot_rc out sys_refcursor) as 
begin
	open listroot_rc for
		select name
		from category
		where parent_category is null;
end;
/

--a2 has leaf
create or replace function Has_Leaf (catelog in category.name%type) 
	return int as

	result int;
	cate_num int;

begin
	select count(*) into cate_num
	from category
	where parent_category = catelog;

	if(cate_num = 0) then result := 0;
	else result := 1;
	end if;

	return(result);

end;
/

--a3 list subcategory
create or replace procedure List_SubCategory (catein in category.parent_category%type, listSub_rc out sys_refcursor) as 
begin
	open listSub_rc for
		select name
		from category
		where parent_category = catein ;
end;
/

--a5 brow product
create or replace procedure Brow_Product 
	(sorttype in varchar2, catein in category.name%type, browproduct_rc out sys_refcursor) as 

	invalid_sort_error exception;

begin
	if sorttype='1' then
		open browproduct_rc for select auction_id, name, description, seller, start_date, min_price, number_of_days, status, sell_date, amount
					from (select p.*, b.category from product p, belongsto b where p.auction_id=b.auction_id)
					where category=catein and status='underauction'
					order by amount desc;
	elsif sorttype='2' then
		open browproduct_rc for select auction_id, name, description, seller, start_date, min_price, number_of_days, status, sell_date, amount
					from (select p.*, b.category from product p, belongsto b where p.auction_id=b.auction_id)
					where category=catein and status='underauction'
					order by name asc;
	else
		raise invalid_sort_error;

	end if;

exception
	when invalid_sort_error then
		dbms_output.put_line('not a valid sort type.');

end;
/


---b search product by text
create or replace procedure Search_Product (keyword1 in varchar2, keyword2 in varchar2, searchproduct_rc out sys_refcursor) as
begin
	open searchproduct_rc for
	    select auction_id, name, description, seller, start_date, min_price, number_of_days, status, sell_date, amount
	    from product
	    where (regexp_like(description, keyword1, 'i') and regexp_like(description, keyword2, 'i')) or (regexp_like(description, keyword1, 'i') and keyword2 is null);
end;
/


---c put product for auction
create or replace procedure Put_Auction
	(name in product.name%type, description in product.description%type, seller in product.seller%type, min_price in product.min_price%type, category in belongsto.category%type, number_of_days in product.number_of_days%type) 
	as

	maxnumber int;
	currentdate date;
	not_leafcategory exception;

begin
	if(Has_Leaf(category)=0) then
	select max(rownum) into maxnumber from product;

	select c_date into currentdate from ourdate;

	insert into product(auction_id, name, description, seller, start_date, min_price, number_of_days, status)
		values(maxnumber, name, description, seller, currentdate, min_price, number_of_days, 'underauction');

	insert into belongsto(auction_id, category)
		values(maxnumber, category);

	else
		raise not_leafcategory;

	end if;
exception
	when not_leafcategory then
		dbms_output.put_line('please enter the category to the smallest one.');

end;
/

--d): bidding on products
--Display all the products that is underauction;
create or replace procedure bidproduct(product_id in Bidlog.auction_id%type, bid_amount in Bidlog.amount%type, 
	                                  bidder_name in Bidlog.bidder%type, decide out int) as
current_amount product.amount%type;
current_bidsn Bidlog.bidsn%type;
previous_bidsn Bidlog.bidsn%type;
bid_time bidlog.bid_time%type;
state product.status%type;

auction_fail_amount exception;
auction_fail_status exception;

begin 

	select c_date into bid_time
	from ourdate
	where rownum = 1;

	select amount into current_amount
	from product
	where product.auction_id = product_id;

	select status into state
	from product
	where product.auction_id = product_id;

	select count(bidsn) into previous_bidsn
	from Bidlog;
	current_bidsn := previous_bidsn;

	if (state = 'underauction') then
		if (current_amount < bid_amount) then 
			insert into Bidlog (bidsn, auction_id, bidder, bid_time, amount) values (current_bidsn, product_id, bidder_name, bid_time, bid_amount);
			decide := 1;
		elsif (current_amount is null) then
			insert into Bidlog (bidsn, auction_id, bidder, bid_time, amount) values (current_bidsn, product_id, bidder_name, bid_time, bid_amount);
			decide := 1;
		else
			decide := 0;
			raise auction_fail_amount;
			
		end if;
	else 
		decide := 2;
		raise auction_fail_status;
		
	end if;
	
	exception 
		when auction_fail_amount then 
		dbms_output.put_line('failed');
		decide := 0;
		when auction_fail_status then
		dbms_output.put_line('failed');
		decide := 2;
end;
/
variable a number;
execute bidproduct(2,600,'user1',:a);
print a;

--trigger: tri_bidTimeUpdate
create or replace trigger tri_bidTimeUpdate
after insert on Bidlog
begin 
	update ourdate
	set c_date = to_date(to_char(c_date, 'dd-mon-yyyy hh24:mi') || ':' || (to_char(c_date, 'ss')+5),'dd-mon-yyyy hh24:mi:ss')
	where rownum = 1;
end;
/

--trigger： tri_updateHighBid
create or replace trigger tri_bidHighUpdate
after insert on Bidlog
for each row
begin
	update product 
	set amount = :new.amount
	where auction_id = :new.auction_id;
end;
/

--e): Selling products
create or replace procedure showUnderauction (owner in product.seller%type, c1 out sys_refcursor) as 
begin
	open c1 for 
	select auction_id, name, min_price
	from product
	where seller = owner and status = 'underauction';
end;
/

--functon: display the second highest bid price for the seller
create or replace procedure display_price (product_id in int, bid_price out int) as
	numb integer;
	nobid exception;
begin
	select count(auction_id) into numb
	from Bidlog
	where auction_id = product_id;

	if (numb = 1) then 
		select amount into bid_price
		from (select amount,rownum as snum
				from(
					select *
					from Bidlog 
					where auction_id = product_id
					order by amount desc))
		where snum = 1;
	elsif (numb >1) then
		select amount into bid_price
		from (select amount,rownum as snum
				from(
					select *
					from Bidlog 
					where auction_id = product_id
					order by amount desc))
		where snum = 2;
	else 
		raise nobid;
	end if;

	exception
		when nobid then 
		bid_price := 0;
end;
/

--procedure: ender the selecton of the seller and update the table product
create or replace procedure update_status (product_id in int, price_record in int, desicion in product.status%type) as
begin
	if (desicion = 'sell') then
		update product
		set amount = price_record, status = 'sold'
		where auction_id = product_id;
	elsif (desicion = 'withdraw') then 
		update product
		set amount = null, status = 'withdrawn'
		where auction_id = product_id;
	end if;
end;
/


----execute update_status (2,'sell');

--f) : Suggestion
create or replace procedure Suggestion (user_id in Customer.login%type, pointer out sys_refcursor) as
--define a record type
begin 
	open pointer for 
	select popularity, bid_id, product.name
	from (
		  select count(auction_id) as popularity, auction_id as bid_id
		  from Bidlog 
		  where bidder in (select bidder as bidding_friend
		  				from Bidlog
		  				where auction_id in (select auction_id 
		  									 from Bidlog
		  									 where bidder = user_id))
		  group by auction_id
		  order by popularity desc), product
	where bid_id = auction_id
	order by popularity desc;
end;
/
----execute Suggestion('user2')

----administrator interface
----a) new customer registration
----a1 Is an administrator or nor
create or replace function Is_Admin (admin_in in administrator.login%type) 
	return int as

	isadmin int;
	existadmin number;

begin
	select count(*) into existadmin
	from administrator
	where admin_in in (select login
						from administrator);

	if(existadmin = 0) then isadmin := 0;
	else isadmin := 1;
	end if;

	return(isadmin);

end;
/

----a2 Is an already existed customer or nor
create or replace function Is_Customer (customer_in in customer.login%type) 
	return int as

	iscustomer int;
	existcustomer number;

begin
	select count(*) into existcustomer
	from customer
	where customer_in in (select login
						from customer);

	if(existcustomer = 0) then iscustomer := 0;
	else iscustomer := 1;
	end if;

	return(iscustomer);

end;
/

----a3 Check new customer
create or replace procedure Check_Customer 
	(new_customer in Customer.login%type, new_pwd in Customer.password%type, new_name in Customer.name%type, new_address in Customer.address%type, new_email in Customer.email%type) as

begin
		insert into customer
			values(new_customer, new_pwd, new_name, new_address, new_email);

end;
/

--b): Update system date
create or replace procedure DateUpdate (Currentdate in product.status%type) as
begin 
	Update ourdate
	set c_date = to_date(Currentdate,'dd-mon-yyyy hh24:mi:ss')
	where rownum = 1;
end;
/
--execute DateUpdate ('12-Aug-2013 00:06:00');


--c): Product statistics
--function: count date from all users
create or replace procedure count_all (pointer out sys_refcursor) as
begin 
	open pointer for 
	select product.auction_id,name,seller,status,buyer,product.amount as final_amount,bidlog.amount as bid_amount,bidder 
	from product, Bidlog 
	where (product.auction_id = Bidlog.auction_id and product.amount=Bidlog.amount) or product.amount = null;
end;
/

--function: count data for specific user
create or replace procedure count_specified (customer in product.seller%type, pointer out sys_refcursor) as
begin 
	open pointer for 
	select product.auction_id,name,seller,status,buyer,product.amount as final_amount,bidlog.amount as bid_amount,bidder 
	from product, Bidlog 
	where ((product.auction_id = Bidlog.auction_id and product.amount=Bidlog.amount) or product.amount = null) and seller = customer;
end;
/

----d statistics
----1)product_count
create or replace function product_count (x_month in int, c_cate in belongsto.category%type)
	return int as

	sold_total int :=0;
	monthnum int;

	cursor c1 is
		select p.auction_id, p.start_date, b.category
		from product p, belongsto b
		where p.status='sold' and p.auction_id=b.auction_id and b.category=c_cate;		
	c1_rec c1%rowtype;

begin
	open c1;

	loop
	fetch c1 into c1_rec;
	exit when c1%notfound;
	select months_between(c_date, c1_rec.start_date) into monthnum
	from ourdate
	where rownum=1;
	if(monthnum <= x_month) then
		sold_total := sold_total+1;
	end if;
	end loop;

	close c1;

	return(sold_total);
end;
/


----d1 top k highest in leaf category
create or replace procedure topk_leafcate (top_k in int, x_month in int, topkleaf_rc out sys_refcursor) as

	sold_num int;

begin
	open topkleaf_rc for
		select name, sold_num
		from (select name, product_count(x_month, name) as sold_num
			from category
			where name in (select name from category where has_leaf(name)=0)
			order by product_count(x_month, name) desc)
		where rownum<=top_k;

end;
/


----execute topk_leafcate(3, 2);
----execute topk_leafcate(20, 2);

----d2 top k highest in root category
----find root category
create or replace function Find_Root (catelog in category.name%type) 
	return category.name%type as

	root_cate category.name%type;
	current_cate category.name%type;

begin
	root_cate := catelog;
	current_cate := catelog;
	while (root_cate is not null) loop
		current_cate := root_cate;		
		select parent_category into root_cate
		from category
		where name=current_cate;
	end loop;

	return(current_cate);
end;
/

----select name, Find_Root(name) from category where name='Computer Science';

----top k highest in root category
create or replace procedure topk_rootcate (top_k in int, x_month in int, topkroot_rc out sys_refcursor) as

	sold_num int;
	total_sum int :=0;


begin

	open topkroot_rc for
		select root_name, sum(sold_num) as total_sum
		from (select name, product_count(x_month, name) as sold_num, Find_Root(name) as root_name 
			from category)
		where rownum<=top_k
		group by root_name
		order by total_sum desc;

end;
/

--2) Bid_Count
create or replace function Bid_Count (x_month in int, user in Bidlog.bidder%type)
	return int as 
	total int := 0;
cursor c1 is 
	select auction_id, bidder, bid_time
	from Bidlog
	where bidder = user;
c1_rec c1%rowtype;
monthnum int;
begin
	open c1;
	loop
	fetch c1 into c1_rec;
	exit when c1%notfound;
	select months_between(c_date, c1_rec.bid_time) into monthnum
	from ourdate
	where rownum = 1;
	if(monthnum < x_month) then 
		total := total + 1;
	end if;
	end loop;

	return(total);
end;
/

--3) Buying_Amount
create or replace function Buying_Amount (x_month in int, user in product.buyer%type)
	return int as 
total int := 0;
cursor c1 is 
	select buyer, sell_date, amount
	from product
	where buyer = user;
c1_rec c1%rowtype;
monthnum int;
begin 
	open c1;
	loop
	fetch c1 into c1_rec;
	exit when c1%notfound;
	select months_between(c_date, c1_rec.sell_date) into monthnum
	from ourdate
	where rownum = 1;

	if (monthnum <= x_month ) then
		total := total + c1_rec.amount;
	end if;
	end loop;
	return(total);
end;
/
--d3 the top k most active bidders(highest count of bids placed)
create or replace procedure active_bidders (x_month in int, k in int, c1 out sys_refcursor) as
begin 
	open c1 for 
	select bidder, Bid_Count(x_month,bidder) as Num_Bid
	from Bidlog
	where rownum <= k
	order by Num_Bid desc;
end;
/


--d4 the top k most active buyers (highest total dollar amount spent)
create or replace procedure active_buyers (x_month in int, k in int, c1 out sys_refcursor) as 
begin
	open c1 for
	select buyer, Buying_Amount(x_month,buyer) as Sum_Amount
	from product
	where rownum <= k and buyer is not null
	order by Sum_Amount desc;
end;
/

----additional requirements
create or replace function Check_loginpwd (login_name in customer.login%type, pwd in customer.password%type)
	return int as

	result int := 0;
	customer_pwd varchar2(10);
	admin_pwd varchar2(10);

begin
	if(Is_Customer(login_name)=1) then
		select password into customer_pwd
		from customer
		where login=login_name;
		if(customer_pwd=pwd) then
			result := 1;
		end if;
	elsif(Is_Admin(login_name)=1) then
		select password into admin_pwd
		from administrator
		where login=login_name;
		if(admin_pwd=pwd) then
			result := 2;
		end if;
	else 
		result := 0;
	end if;

	return(result);
end;
/

----b trigger CloseAuctions
create or replace trigger CloseAuctions
after update of c_date on ourdate 
declare
monthnum number;
begin
		select months_between(c_date, p.start_date) into monthnum
		from ourdate, product p
		where rownum = 1;

		update product
		set status='close'
		where monthnum >= (number_of_days/31) and status = 'underauction';
end;
/
