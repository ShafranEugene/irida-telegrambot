release: ./mvnw flyway:migrate
web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/irida-telegrambot-0.7.0-SNAPSHOT.jar --spring.datasource.url=${JDBC_DATABASE_URL}