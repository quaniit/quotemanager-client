# QuotemanagerClient
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General-info
Demo Project for Matter Product Studio. And maybe teach myself angular along the way.

## Technologies
* Angular 13.2.6
* Angular Material 13.2.6
* Spring boot 2.5.10
* Apache Commons lang 3.8.1
* Apache Commons Collections 4.4

## Setup
To run both the API services and the Angular application, two consoles are required. Navigate to the directory of the project and enter the following commands:
### API server - Terminal 1
```
mvnw clean install
mvnw spring-boot:run
```
### Node Build - Terminal 2
```
ng build
npm start
```

## Project Status
Services are working and available via localhost:8080
Angular app displays preloaded list and is able to delete from that list
