# Amazin Bookstore

## Project Description
Bookstore Owner can upload and edit Book information (ISBN, picture, description, author, publisher,...) and inventory. User can search for, and browse through, the books in the bookstore, sort/filter them based on the above information. User can then decide to purchase one or many books by putting them in the Shopping Cart and proceeding to Checkout. The purchase itself will obviously be simulated, but purchases cannot exceed the inventory. User can also view Book Recommendations based on past purchases. This is done by looking for users whose purchases are most similar (using Jaccard distance: Google it!), and then recommending books purchased by those similar users but that the current User hasn't yet purchased.

## Team Members
* Brandon Mercer
* Evan Lloyd
* Kareem El-Hajjar
* Khalid Kana'An
* Mohamed Abdalla

## Building

You can build with maven directly using `mvn clean install package spring-boot:repackage`

You can also build the docker image using the `docker build -t 4806_project .`

## Running with Docker Compose

To run the bookstore application and MySQL database as Docker containers, follow these steps:
1. Install Docker Compose on your system if it's not already installed.
2. Change your current working directory to the root directory of the project.
3. Run the following command to start the app and database containers: `docker-compose up -d`
   
Note: the above command will build and run the bookstore app and database containers in detached mode which means they will continue to run in the background even if you close your terminal. To stop and remove the containers refer to step 6.

4. Verify that the containers are running by running the following command: `docker ps`
5. To access the bookstore app in your web browser visit http://localhost:8080/.
6. To stop and remove the containers use the command: `docker-compose down`

## Running without Docker Compose

You can run the jar file created by Maven or the Docker image separately if you want, make sure you are in the root project directory before executing any of these commands:

  - To run the jar file created by Maven: `java -jar target/4806_project-1.0.jar`
  - To run the MySQL container: `docker run -d -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=book_store -e MYSQL_USER=group22 -e MYSQL_PASSWORD=password -v "$(pwd)/src/main/resources/init:/docker-entrypoint-initdb.d" mysql/mysql-server:latest`
  - To run the bookstore container: `docker run -i -t --publish 127.0.0.1:8080:8080/tcp 4806_project`

## Testing

Tests can be run with `mvn test`

Front-end unit tests can be run by opening `jasmine/SpecRunner.html` in a browser

![Java CI badge not found](https://github.com/Brandon-999/4806_project/actions/workflows/maven-tests.yml/badge.svg)

## UML Diagram (Models Only)

![UML Diagram](https://github.com/Brandon-999/4806_project/blob/main/UML_Diagram_M3.png?raw=true)

## ER Diagram

![ER Diagram](https://github.com/Brandon-999/4806_project/blob/main/ER-Diagram-M3.png?raw=true)


