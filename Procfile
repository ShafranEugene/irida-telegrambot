release: ./mvnw flyway:migrate
web: java $JAVA_OPTS -jar -Dserver.port=$PORT target/irida-telegrambot-0.7.0-SNAPSHOT.jar -Dserver.port=$PORT --spring.datasource.url=${JDBC_DATABASE_URL}