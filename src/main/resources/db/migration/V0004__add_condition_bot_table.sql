USE telegram_bot_irida;

DROP TABLE IF EXISTS condition_bot;


CREATE TABLE condition_bot (
    id int primary key auto_increment,
    id_user varchar(100),
    answer_order_status boolean DEFAULT false,
    answer_invoice_status boolean DEFAULT false,
    foreign key (id_user) references user_telegram (chat_id)
);