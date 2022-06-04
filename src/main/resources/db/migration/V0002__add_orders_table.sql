DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
    id_order int primary key auto_increment,
    number_order varchar(30),
    city varchar(30),
    id_user varchar(100),
    status boolean,
    foreign key (id_user) references user_telegram (chat_id)
);