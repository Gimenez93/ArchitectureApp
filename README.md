Architecure App
============


This is a very simple Spring demo of a Series application providing a RESTFul JSON API also available from an HTML user interface. It features:

* Spring MVC with Content Negotiation
* Spring Data JPA/Hibernate persistence over HSQL and Heroku PostgreSQL

* ...

To run locally, first build WAR package:
```
mvn package
```

Then run embedded Tomcat server:
```
mvn exec:exec
```

The application will be available at http://localhost:8080/search When running locally.


The app is running on: 

http://stormy-temple-9571.herokuapp.com/

You can access to the next directories:
http://stormy-temple-9571.herokuapp.com/search
http://stormy-temple-9571.herokuapp.com/search/addShow
http://stormy-temple-9571.herokuapp.com/users
http://stormy-temple-9571.herokuapp.com/series



