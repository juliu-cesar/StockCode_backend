create table transaction(
  id bigint not null auto_increment,
  date datetime not null,
  client varchar(100),
  description varchar(50),
  primary key(id)
);