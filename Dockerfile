FROM ubuntu

WORKDIR home

RUN mkdir src

COPY src ./src

COPY pom.xml ./pom.xml

RUN apt-get update
RUN apt-get install maven -y

RUN mvn clean install package spring-boot:repackage

EXPOSE 8080

ENTRYPOINT java -jar target/4806_project-1.0.jar