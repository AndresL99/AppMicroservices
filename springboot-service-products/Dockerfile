FROM openjdk:12
VOLUME /tmp
ADD ./target/springboot-service-products-0.0.1-SNAPSHOT.jar service-products.jar
ENTRYPOINT ["java", "-jar","/service-products.jar"]