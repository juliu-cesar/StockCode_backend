create table transaction(
  id bigint not null auto_increment,
  date datetime not null,
  client varchar(255),
  description varchar(255),
  primary key(id)
)auto_increment = 100000;