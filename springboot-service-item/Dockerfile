FROM openjdk:12
VOLUME /tmp
EXPOSE 8002
ADD ./target/springboot-service-item-0.0.1-SNAPSHOT.jar service-item.jar
ENTRYPOINT ["java", "-jar","/service-item.jar"]