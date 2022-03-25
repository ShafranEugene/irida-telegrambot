FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=irida_recom_test_bot
ENV BOT_TOKEN=5127901141:AAF_5wRZtWTYSZyyJT-5FkBNPQ9hI3ijgro
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}","-jar","app.jar"]