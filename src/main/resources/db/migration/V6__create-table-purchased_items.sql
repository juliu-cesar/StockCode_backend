create table purchased_items(
  id bigint not null auto_increment,
  transaction_id bigint not null,
  product_id bigint not null,
  product_name varchar(255) not null,
  product_brand varchar(255) not null,
  product_category varchar(255) not null,
  purchase_price decimal(10,2) not null,
  amount integer not null,
  returned boolean not null,
  description varchar(255),

  primary key(id),
  constraint fk_transaction_id_purchased foreign key(transaction_id) references transaction(id)
)auto_increment = 1000000;