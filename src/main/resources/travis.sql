CREATE USER 'bestuser'@'localhost' IDENTIFIED BY 'bestuser';
GRANT ALL PRIVILEGES ON *.* TO 'bestuser'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE telegram_bot_irida;
select database();
SHOW DATABASES;