ALTER TABLE product_transaction RENAME buy_product;
alter table buy_product add returned boolean;
alter table buy_product add description varchar(100);
update buy_product set returned = false where product_id > 0;
alter table buy_product change returned returned boolean not null;