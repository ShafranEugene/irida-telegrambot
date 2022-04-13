USE telegram_bot_irida;

DROP TABLE IF EXISTS condition_bot;

ALTER TABLE user_telegram ADD user_name varchar (100);
ALTER TABLE user_telegram ADD first_name varchar (100);
ALTER TABLE user_telegram ADD admin boolean DEFAULT false;


CREATE TABLE condition_bot (
    id int primary key auto_increment,
    id_user varchar(100),
    answer_order_status boolean DEFAULT false,
    answer_invoice_status boolean DEFAULT false,
    foreign key (id_user) references user_telegram (chat_id)
);