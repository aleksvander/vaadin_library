Test Task
=========
Simple CRUD application (schema library)

Prerequisites
-------------

* [Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/download.cgi)


Build and Run
-------------

1. Run in the command line:
	1. mvn package
	
	2. mvn org.eclipse.jetty:jetty-maven-plugin:run
     or 
    mvn spring-boot:run

3. Open `http://localhost:8080` in a web browser.

Technologies
-------------

* Jetty
* Vaadin 7
* Spring-Boot
* Spring-Data
* Hibernate