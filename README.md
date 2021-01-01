# FRMS-Java-JavaFX-MySQL
Flight Inventory Management App in Java and JavaFX

To run this application:


Requirements:

MySQL 8+
JavaFX 15
Connector J
JDK 11+ 

Setup:

1) Create a database name "frms"
2) Import the provided sql tables

   IF having problem in import then we have provided pic of schema

	a. make db with name frms
	b. create one table name with flights and one with passengers
	c. create same tables with same table name and
	same column name, types and primary key and everything pic has in db folder...


3) Change the user,password & port of MySQL in database/DataSource.java if changed
4) Include Connector J library and JavaFX 15 libraries in intellij project, 
provided in frms/lib folder

finally run! 

Features:

Used Own Made Custom Linked List Data Structure

Flight Inventory

CRUD operations on Flights

Book Passengers

Search Flight with origin and destination

<h2> Main </h2>

<img src="https://raw.githubusercontent.com/smyaseen/pics/main/frms/1.png" />

<h2> Flight Inventory </h2>

<img src="https://raw.githubusercontent.com/smyaseen/pics/main/frms/2.png" />

<h2> Add Passenger  </h2>

<img src="https://raw.githubusercontent.com/smyaseen/pics/main/frms/3.png" />

<h2> Show Passengers </h2>

<img src="https://raw.githubusercontent.com/smyaseen/pics/main/frms/4.png" />

<h2> Update Flight </h2>

<img src="https://raw.githubusercontent.com/smyaseen/pics/main/frms/5.png" />
