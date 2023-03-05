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

You can also create a docker image instead using `docker build -t 4806_project .`

## Running

You can run the jar created by maven by using `java -jar target/4806_project-1.0.jar`

You can run the docker image by using `docker run -i -t --publish 127.0.0.1:8080:8080/tcp 4806_project`

## Testing

Tests can be run with `mvn test`

![Java CI badge not found](https://github.com/Brandon-999/4806_project/actions/workflows/maven-tests.yml/badge.svg)


