FROM maven:3.6.1-jdk-8-slim
WORKDIR /app
COPY . .
RUN mvn package && \
	mv target/vijay-logger-service.jar /run/logreader.jar && \
	rm -rf /app/*
EXPOSE 8081
CMD java -jar /run/logreader.jar