Feature: As an user, I want an API to add product information so that I can save all product information related to each event.

  @positiveAddProduct
  Scenario Outline: api is able to add product details related to particular event
    Given user want to save products which belong to the particular event "<id>" "<timestamp>" "<product_id>" "<product name>" "<quantity>" "<sale_amount>"
    When POST request trigger to add product request using API "/api/v1/products"
    Then User receives asynchronous http 200 as a response for add product call
    Examples:
      | id      | timestamp                        | product_id      | product name | quantity | sale_amount |
      | EVENT_1 | 2020-03-03T23:55:53.111032+08:00 | 881210223567890 | PRODUCT_1    | 2        | 35.48       |

  @positiveAllGetProduct
  Scenario Outline: api is able to get all event and product details
    Given user want to save products which belong to the particular event "<id>" "<timestamp>" "<product_id>" "<product name>" "<quantity>" "<sale_amount>"
    When POST request trigger to add product request using API "/api/v1/products"
    Then User receives asynchronous http 200 as a response for add product call
    When User trigger GET request trigger to retrieve products using API "/api/v1/products"
    Then User receives asynchronous http 200 as a response for get all product call
    Examples:
      | id      | timestamp                        | product_id      | product name | quantity  | sale_amount   |
      | EVENT_1 | 2020-03-03T23:55:53.111032+08:00 | 881210223567890 | PRODUCT_1    | 2         | 35.48         |
      | EVENT_2 | 2020-03-04T03:24:11.111032+08:00 | 881210223567891 | PRODUCT_2    | 10        | 160.48        |



  @positiveGetProduct
  Scenario Outline: api is able to get all event and product details
    Given user want to save products which belong to the particular event "<id>" "<timestamp>" "<product_id>" "<product name>" "<quantity>" "<sale_amount>"
    When POST request trigger to add product request using API "/api/v1/products"
    Then User receives asynchronous http 200 as a response for add product call
    Given User want to retrieve products which belong to the particular event "<id>"
    When User trigger GET request trigger to retrieve product based on event id using API "/api/v1/product/"
    Examples:
      | id      | timestamp                        | product_id      | product name | quantity  | sale_amount |
      | EVENT_1 | 2020-03-03T23:55:53.111032+08:00 | 881210223567890 | PRODUCT_1    | 2         | 35.48       |

   #TODO - Negative test cases need to be implemented which cover all negative and exception scenarios.





