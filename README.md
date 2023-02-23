# angadi e commerce application
A simple end to end and fully functional e-commerce web application built using vanilla java script, html, css. For the backend spring boot to generate REST API with spring security for authentication and authorization are used.
Structuring and finalizing the entities and relations between them will be finalized.

Admin, Seller and User are main stakeholders of this application

Admin can perform following tasks => 
1. Manipulate Customer details
2. perform CRUD on categories
3. perform CRUD on products
4. restricting user permissions
5. view all products and its categories and many more features are coming in future.

Seller can perform follwoing =>
1. add his profile
2. add address
3. add wallet
4. create category
5. create products
6. add shipping details

User has the following features => 
1. Register
2. Login
3. Add wallet
4. add cart
5. add addresses to user profile
6. view products and categories
7. add to cart
8. add delivery information
9. make payment (currently supported only COD and Wallet transactions)
10. order confirmation and many more features are coming near future.

Frontend part is still in development
API endpoint documentation will be released soon through Postman

Tools used to build this application
1. IntelliJ IDE
2. Spring Boot
3. Spring Security
4. VS Code
5. Railways.app
6. MySQL
7. Postman

Languages used to build this application
1. Java
2. MySQL
3. HTML
4. JavaScript
5. CSS

Flow of the Application

##User##
1. register
2. login
3. add wallet
4. add cart
5. search through products
6. add product to cart item
7. add order item (user need to bring cart item to order item)
8. save order with above order items
9. add delivery address and payments before saving order
10. make payment to orders

##Admin##
1. register
2. login
3. add category
4. manipulate products
5. manipulate user
6. manipulate seller

##Seller##
1. register
2. login
3. add category
4. add products
5. add shipping details
6. add wallet