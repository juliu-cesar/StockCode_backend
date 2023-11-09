create table refound_product(
  purchased_items_id bigint not null,
  transaction_id bigint not null,
  price_refunded decimal(10,2) not null,
  amount_refunded integer not null,
  reason_return varchar(50) not null,
  description varchar(255),

  primary key(purchased_items_id, transaction_id),
  constraint fk_refound_product_purchased_items foreign key(purchased_items_id) references purchased_items(id),
  constraint fk_refound_product_transaction foreign key(transaction_id) references transaction(id)
);