create table purchasedItems(
  product_id bigint not null,
  transaction_id bigint not null,
  purchase_price decimal(10,2) not null,
  amount integer not null,
  returned boolean not null,
  description varchar(100),

  primary key(product_id, transaction_id),
  constraint fk_product_transaction_id foreign key(product_id) references product(id),
  constraint fk_transaction_product_id foreign key(transaction_id) references transaction(id)
);