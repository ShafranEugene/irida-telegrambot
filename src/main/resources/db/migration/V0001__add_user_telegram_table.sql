USE telegram_bot_irida;
-- ensure that the table with this name is removed before creating a new one.
DROP TABLE IF EXISTS user_telegram;

-- Create user_telegram table
CREATE TABLE user_telegram (
    chat_id varchar(100),
    active BOOLEAN,
    primary key (chat_id)
);