# README #

This README attempts to document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* This is the Java Middleware that acts as a REST interface to the neo4j database.
* Version 0.15
* [Learn Markdown](https://bitbucket.org/tutorials/markdowndemo)

### How do I get set up? ###

* Summary of set up
Minimum requirements for compile are:
Java 1.8
Maven 3

Then use mvn spring-boot:run to execute it in situ.
Alternatively mvn package will create a war file that can be deployed.

* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions

### Contribution guidelines ###

Structure

com.eulersbridge.iEngage
---Application.java
---iEngageWebXml.java

...config
	CoreConfig
	EmailConfig
	SecurityConfig ***

.core.domain
	not really used currently.  Probably should refactor database.domain into here.

.core.events
	App is event driven as a bit of technical insurance for future.  Eventually these events could be
	messaged across different servers to deal with load.  Each different Domain object has a number of events
	mapping the CRUD possibilities.  There is also a Details object that maps the details of the CORE/REST objects
	and is used to convert between the representations.

.core.services
	This is where the services interfaces and the EventHandler implementations of them are located.

.database.domain
	where the DATA objects that are reflected into the database are defined.

.database.repository
	the interfaces defining the Neo4j repositories.

.email
	where email specific code is located

.rest.controller
	where the REST controllers for the various endpoints are located.

com.eulersbridge.iEngage.rest.domain
	where the Rest Domain objects are defined.

resources.templates
	velocity templates for email and web.


* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Repo owner or admin
* Other community or team contact
