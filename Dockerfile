FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV SPRING_DATABASE_URL=jdbc:mysql://iridaDB:3306/telegram_bot_irida
ENV BOT_NAME=irida_recom_test_bot
ENV BOT_TOKEN=5127901141:AAF_5wRZtWTYSZyyJT-5FkBNPQ9hI3ijgro
ENV BOT_ADMIN=ZloyShafran
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.datasource.url=${SPRING_DATABASE_URL}","-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}","-Dbot.admin=${BOT_ADMIN}","-jar","app.jar"]