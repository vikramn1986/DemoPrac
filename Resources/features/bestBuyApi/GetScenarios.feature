Feature: Best Buy API Product Info 
	This feature is to validate all the GET Requests for Products

@get 
Scenario: Get All Products 
	Given Best Buy API is up and running 
	When I hit url with query parameter as "all" 
	Then API returns response with status code as 200 
	And all the products will be returned 
	
@get 
Scenario: Get all products, sort by highest price (descending) 
	Given Best Buy API is up and running 
	When I hit url with query parameter as "$sort[price]=-1" 
	Then API returns response with status code as 200 
	And products prices will be returned in descending order	
	
@get
Scenario: Get all products less than or equal to price $1.00
	Given Best Buy API is up and running
	When I hit url with query parameter as "price[$lte]=1"
	Then API returns response with status code as 200
	And products with price less than or equal to "$1.00" will be displayed