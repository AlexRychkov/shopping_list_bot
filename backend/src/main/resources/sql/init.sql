create table if not exists shopping_list (
    creator varchar(20) not null,
    name varchar(100) not null,
    primary key (creator, name)
)