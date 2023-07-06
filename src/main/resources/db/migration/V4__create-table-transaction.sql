create table transaction(
  id bigint not null auto_increment,
  movement varchar(50) not null,
  date datetime not null,
  total_price decimal(10, 2) not null,
  description varchar(50),
  primary key(id)
);