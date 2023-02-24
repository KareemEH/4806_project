# Amazin Bookstore

### Building

You can build with maven directly using `mvn clean install package spring-boot:repackage`

You can also create a docker image instead using `docker build -t 4806_project .`

### Running

You can run the jar created by maven by using `java -jar target/4806_project-1.0.jar`

You can run the docker image by using `sudo docker run -i -t --publish 127.0.0.1:8080:8080/tcp 4806_project`

### Testing

Tests can be run with `mvn test`

![Java CI badge not found](https://github.com/Brandon-999/4806_project/actions/workflows/maven-tests.yml/badge.svg)