FROM ubuntu

WORKDIR home

RUN mkdir src

COPY src ./src

COPY pom.xml ./pom.xml

RUN apt-get update
RUN apt-get install maven -y

RUN mvn clean install package spring-boot:repackage

RUN apt-get install -y wget && \
    wget https://github.com/vishnubob/wait-for-it/raw/master/wait-for-it.sh -O /wait-for-it.sh && \
    chmod +x /wait-for-it.sh

EXPOSE 8080

ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "target/4806_project-1.0.jar"]