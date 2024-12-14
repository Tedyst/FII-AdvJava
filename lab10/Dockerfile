FROM maven:3-amazoncorretto-11
WORKDIR /usr/src/app

COPY pom.xml /usr/src/app/
RUN mvn -B dependency:go-offline

COPY src /usr/src/app/src

RUN mvn clean package

FROM amazoncorretto:11-alpine3.18

COPY --from=0 /usr/src/app/target/lab10-microbundle.jar /usr/app/lab10-microbundle.jar

CMD ["java", "-jar", "/usr/app/lab10-microbundle.jar"]