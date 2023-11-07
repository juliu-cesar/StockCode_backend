create table product_return(
  product_id bigint not null,
  transaction_id bigint not null,
  price_refunded decimal(10,2) not null,
  amount_refunded integer not null,
  reason_return varchar(50) not null,
  description varchar(255),

  primary key(product_id, transaction_id),
  constraint fk_refound_product_id foreign key(product_id) references product(id),
  constraint fk_refound_transaction_id foreign key(transaction_id) references transaction(id)
);