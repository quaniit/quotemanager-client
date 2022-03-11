# QuotemanagerClient
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Project status](#project-status)
* [Setup](#setup)
* [Functionalites](#functionalites)

## General-info
Demo Project for Matter Product Studio. And maybe teach myself angular along the way.

## Technologies
* Angular 13.2.6
* Angular Material 13.2.6
* Spring boot 2.5.10
* Apache Commons lang 3.8.1
* Apache Commons Collections 4.4

## Project-status
* Services are working and available via localhost:8080
* Angular app displays preloaded list and is able to delete from that list
* Documentation still needs refinement

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

## Functionalites
### Quote manager service
#### Get All
Retrieves all quotes currently saved
##### Request
GET http://localhost:8080/api/quotes
##### Request parameters
##### Response: 200 OK
    {
      type: array,
      items: {
       title: QuoteImpl,
       type: object,
       properties: {
         id: string,
         symbol: string,
         price: double,
         availableVolume: int,
         expirationDate: string
    }
 #### Get By Symbol
Retrieves all quotes currently saved under the symbol provided
##### Request
GET http://localhost:8080/api/quotes/symbol?symbol={symbol}
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| symbol | string | true |
##### Response: 200 OK
    {
      type: array,
      items: {
       title: QuoteImpl,
       type: object,
       properties: {
         id: string,
         symbol: string,
         price: double,
         availableVolume: int,
         expirationDate: string
       }
      }
    }
 #### Get Best Quote
Returns the best (i.e. lowest) price in the symbol's book that still has available volume.
##### Request
GET http://localhost:8080/api/quotes/best?symbol={symbol}
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| symbol | string | true |
##### Response: 200 OK
    {
      title: QuoteImpl,
       type: object,
       properties: {
         id: string,
         symbol: string,
         price: double,
         availableVolume: int,
         expiration: string
       }
    }
#### Add Quotes
Adds provided
##### Request
POST http://localhost:8080/api/quotes
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| id | string | false --Providing an id will just function as an add |
| symbol | string | true |
| price | string | true |
| availableVolume | string | true |
| expiration | string | true |

    Default - json
    {
      "symbol": "BA",
      "price": "2.00",
      "availableVolume":"100",
      "expiration": "2023-01-01"
    }
##### Response: 201 Created
    Quote for [symbol supplied] successfully created
 #### Execute Trade
Request that a trade be executed. Assume that the trade is a request to BUY, not sell.
##### Request
POST http://localhost:8080/api/quotes/trade?symbol={symbol}&volumeRequested={volumeRequested}
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| symbol | string | true |
| volumeRequested | string | true |
##### Response: 200 OK
    {
      title: TradeResultImpl,
       type: object,
       properties: {
         id: string,
         symbol: string,
         volumeWeightedAveragePrice: double,
         volumeRequested: int,
       }
    }
#### Update Quote
Updates provided
##### Request
PUT http://localhost:8080/api/quotes
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| id | string | false --Not providing an id will just function as an add |
| symbol | string | true |
| price | string | true |
| availableVolume | string | true |
| expiration | string | true |

    Default - json
    {
      "id": "f27d50ce-a154-11ec-b909-0242ac120002"
      "symbol": "BA",
      "price": "2.00",
      "availableVolume":"100",
      "expiration": "2023-01-01"
    }
##### Response: 200 OK
Quote for [symbol supplied] successfully updated
#### Delete Quote
Deletes provided
##### Request
DELETE http://localhost:8080/api/quotes/id?id={id}
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| id | string | true |
##### Response: 200 OK
Quote for [id supplied] successfully deleted
##### Response: 200 OK
Quote for [symbol supplied] successfully updated
#### Delete Quotes
Deletes all by provided symbol
##### Request
DELETE http://localhost:8080/api/quotes/symbol?symbol={symbol}
##### Request parameters
| Name | Type | Required |
| --- | --- | --- |
| symbol | string | true |
##### Response: 200 OK
Quotes for [symbol supplied] successfully deleted

* PUT updateQuote() - "/api/quotes"
* DELETE deleteQuote() - "/id/{id}"
* DELETE deleteQuotesBySymbol() - "/symbol/{symbol}"

