release: ./mvnw flyway:migrate
web: java $JAVA_OPTS -Dserver.port=$PORT -jar target/irida-telegrambot-0.7.0-SNAPSHOT.jar --port $PORT --spring.datasource.url=${JDBC_DATABASE_URL}