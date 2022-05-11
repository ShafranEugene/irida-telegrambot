<h1 style="text-align: center;">Irida Telegram Bot</h1>

Telegram bot for saving and managing orders.

## Idea
The main idea is to getting practical experience in the development of first project and creating a telegram bot which will help with the work of a logistician.

## MVP Scope
In logistician's job, I need often to handle orders my company's branches. These orders contain products that branches need to transport from other branches by creating an invoice. 

# How it works
Based on MVP Scope, in bot have been implemented:
- Saving orders and invoices.
- Closing orders when creating the corresponding invoice.
- Showing all active orders.
- Showing details and deleting any orders and invoices.
- The bot has a security system - when new user wants to work with the bot, bot sends request to an admin to get access for new user.

The manual is in English, users can get it using the /guide command in the bot.

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