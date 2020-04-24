create sequence if not exists shopping_list_id_seq;

create table if not exists shopping_list (
    id bigint default nextval('shopping_list_id_seq'),
    creator varchar(20) not null,
    name varchar(100) not null,
    primary key (id)
);

create sequence if not exists list_item_id_seq;

create table if not exists list_item (
    id bigint default nextval('list_item_id_seq'),
    list_id bigint not null,
    name varchar(100) not null,
    comment varchar(128),
    price numeric(19,2),
    mark varchar(10) not null,
    primary key (id)
);