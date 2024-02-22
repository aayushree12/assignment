# For Java 17,
FROM openjdk:17-oracle
WORKDIR /opt/app/car-parking-system
COPY ./target/*.jar /opt/app/car-parking-system/car-parking-system.jar
ENTRYPOINT ["java","-jar","car-parking-system.jar"]