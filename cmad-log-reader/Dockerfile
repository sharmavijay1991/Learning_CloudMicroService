FROM maven:3.6.1-jdk-8-slim
WORKDIR /app
COPY . .
RUN mvn package && \
	mv target/vijay-logreader-service.jar	 /run/logreader.jar && \
	rm -rf /app/*
EXPOSE 8082
CMD java -jar /run/logreader.jar