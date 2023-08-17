create table stock(
  id bigint not null auto_increment,
  product_id bigint not null,
  amount bigint not null,
  location varchar(6) not null,

  primary key(id),
  constraint fk_stock_product_id foreign key(product_id) references product(id)
);