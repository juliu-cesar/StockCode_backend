create table;

-- FLY WAY --------------

SELECT * FROM flyway_schema_history;

delete from flyway_schema_history where installed_rank = 8;

delete from flyway_schema_history where success = 0;

-- DESC table --------------

desc product;

desc buy_product;

-- SELECT simples ----------------------------------------------------------------------


SELECT * FROM product;

SELECT * FROM buy_product;

SELECT * FROM transaction;

SELECT * FROM product_return;

SELECT * FROM stock;

-- Resolvendo ERROS nas tabelas e colunas --------------

delete from transaction where id > 0;

update buy_product
	set buy_product.categories_movements = "SALE", buy_product.returned = null, buy_product.description = null
    where buy_product.transaction_id = 6
    and buy_product.product_id = 146285937;
    
ALTER TABLE buy_product RENAME product_transaction;
alter table product_transaction change returned reason_return boolean;


-- Query para buscar lista das transações com valor total --------------

select transaction.id, transaction.date, transaction.client, sum(buy_product.purchase_price * buy_product.amount) as valor_total,transaction.description
	from transaction
    join buy_product on transaction.id = buy_product.transaction_id
    GROUP BY transaction.id;

-- Query para transação detalhada --------------

select product.id, product.product_name, brand.brand_name, buy_product.purchase_price, buy_product.amount, 
		(buy_product.purchase_price * buy_product.amount) as Total, 
        buy_product.returned, buy_product.description
	from product
    join buy_product on product.id = buy_product.product_id
    join transaction on buy_product.transaction_id = transaction.id
    join brand on product.brand_id = brand.id
    where transaction.id = 16
    order by product_name;
    
-- Query para para tabela de devolução --------------

select product.id, product.product_name, brand.brand_name, product_return.price_refunded, product_return.amount_refunded, 
		(product_return.price_refunded * product_return.amount_refunded) as total_refounded, 
        product_return.reason_return, product_return.description
	from product
    join product_return on product.id = product_return.product_id
    join transaction on product_return.transaction_id = transaction.id
    join brand on product.brand_id = brand.id
    where transaction.id = 16
    order by product_name;
    
