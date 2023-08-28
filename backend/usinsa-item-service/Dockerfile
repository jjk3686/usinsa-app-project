FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/item-service-1.0.jar ItemService.jar
ENTRYPOINT ["java", "-jar", "ItemService.jar"]