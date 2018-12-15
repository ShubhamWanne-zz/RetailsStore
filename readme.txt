###### Retails Store ######

*** Please follow below steps to build / deploy application ; 
1) Import this project as maven project into Spring Tool Suit / Eclipse / Editor of your choice
2) Change configuration of POSTGreSQL database from application.properties;
	1) Enter username
	2) Enter password
3) If application and database are pefectly configured, you should be able to use localhost:8080/

*** Path information;
URI								RequestType	Description
localhost:8080/customers					GET		To retrive list of customers.
localhost:8080/customers					POST		To add customer into database.
localhost:8080/customers/{customerID}				PUT		To update customer into database.
localhost:8080/customers/{customerID}/products			GET		To get list of products from customer's cart.
localhost:8080/customers/{customerID}/products			POST		To add products into customer's cart.
localhost:8080/customers/{customerID}/products/{product_id} 	GET		To get details of particular product.
localhost:8080/customers/{customerID}/products/{product_id}	PUT		To update partcular product
-------------------------------------------------------------------------------------------------------------
localhost:8080/customers/{customer_id}/bill/checkout		GET		To get final checkout itemized bill.


*** UML diagrams for model class
_________________________	_________________________
|	Customer 	|	|	Bill 		|
|-----------------------|	|-----------------------|
|	id (PK)		|*    1	|	id (PK)		|
|	name		|-------|	customerID(FK)	|
|	created_at	|	|	created_at	|
|	updated_at	|	|	updated_at	|
|_______________________|	|_______________________|
	|1
	|
________|*_______________
|	Product	 	|
|-----------------------|
|	id(PK)		|
|	name		|
|	category	|
|	cost		|
|	quantity	|
|	customerID(FK)	|
|	created_at	|
|	updated_at	|
|_______________________|


*** Security
To enable basic authentication, uncomment "spring-boot-starter-security" dependency from pom.xml
For each request add "username" : "user" and password(as shown when spring application gets booted) in the authorisation header.
