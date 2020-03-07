# BB Java Developer Test API

> This is a simple web REST service to store/ retrieve products based on event

3 services are implemented

- Store products from a web request
- Retrieve products from a web request
- Retrieve product based on event id

## API Details

API to add event with products.

http://localhost:8083/producerservice/api/v1/products

Json request: 

```

{
  "id": "(string) unique id of the event",
  "timestamp": "(timestamp) utc timestamp of the event",
  "products": [
    {
      "id": "(long) id of the product",
      "name": "(string) name of the product",
      "quantity": "(integer) quantity of the product",
      "sale_amount": "(double) total sale amount"
    }
    
  ]
}

```

API to retrieve event with products based on event id

http://localhost:8083/producerservice/api/v1/product/{event-id}

API to retrieve all event with products

http://localhost:8083/producerservice/api/v1/products

## Features

- Spring WebFlux Non-Blocking endpoint implementation.
- Cucumber Integration Testing (BDD Test cases).
- Cache implementation.
- Coding best practices.
    Programming to interface
    SRP
    Applied SOLID principles
- Java 11 and gradle as build tool.
- Imported verified 3rd party libraries to effective programming.

## How to run

- run 'gradle wrapper'

- then run 'gradle clean build'

- After that 'gradle bootrun'



