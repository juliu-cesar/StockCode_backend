create table product(
    id bigint not null auto_increment,
    product_name varchar(50) not null,
    price decimal(10,2) not null,
    description varchar(50),
    brand_id bigint not null,
    category_id bigint not null,

    primary key(id),
    constraint fk_product_brand_id foreign key(brand_id) references brand(id),
    constraint fk_product_category_id foreign key(category_id) references category(id)
);