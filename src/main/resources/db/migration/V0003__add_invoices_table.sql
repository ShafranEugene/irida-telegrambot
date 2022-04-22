USE telegram_bot_irida;

ALTER TABLE orders ADD date_created TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP;

DROP TABLE IF EXISTS invoices;


ALTER TABLE user_telegram ADD user_name varchar (100);
ALTER TABLE user_telegram ADD first_name varchar (100);
ALTER TABLE user_telegram ADD admin boolean DEFAULT false;


CREATE TABLE invoices (
    id_invoice int primary key auto_increment,
    number_invoice varchar(30),
    city varchar(30),
    id_user varchar(100),
    id_order int,
    comment varchar(200),
    date_add TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    foreign key (id_user) references user_telegram (chat_id),
    foreign key (id_order) references orders (id_order)
);



