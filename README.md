<h1 align="center">Irida Telegram Bot</h1>

Telegram bot for saving and managing orders.

## Idea
The main idea is to get practical experience in the development first full project and create a telegram bot which will help with the work of a logistician.

## MVP Scope
In my work in logistics, I need often to handle orders from branches of my company. These orders contain products that these branches need and need to be transported from other branches by creating an invoice. Bot created for management orders and invoices, show all active orders, save and close them.

# How it works 
Based on MVP Scope, in bot have been implemented:
- Saving orders and invoices.
- Close orders when creating the corresponding invoice.
- Show all active orders.
- Show details and delete any orders and invoices.
- The bot has a security system, when a new user wants to work with the bot, the bot sends admins to request to get for new user access.

The manual is in Russian, users can get it using the /guide command in the bot.

## Deployment
Deployment process as easy as possible:
Required software:
- terminal for running bash scripts
- docker
- docker-compose

to deploy application run bash script:

$ bash start.sh ${BOT_NAME} ${BOT_TOKEN} ${BOT_ADMIN}

Where:
- ${BOT_NAME} - name your bot
- ${BOT_TOKEN} - token your bot
- ${BOT_ADMIN} - telegram user's name of first admin

## Technological stack
- Spring Boot as a skeleton framework
- Telegram-bot Spring Boot starter
- Spring Boot Starter Data JPA
- MySQL database
- Flyway database migration tool
- Jackson Databind for some callback buttons
- JUnit Jupiter API and Mockito Core for tests

## License
This project is Apache License 2.0 - see the [LICENSE](LICENSE) file for details