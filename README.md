jlab - Flavour of the Month
====

University of Helsinki Java assignment for "Javalabra 2013"

Need to use a database for your program? This app will tell you which one to choose.
Flavour of the Month (FOTM) parses Twitter feeds to determine what is your database of choice.
The base functionality of FOTM is built upon the Twitter API 1.1, and it uses the twitter4j library.
It runs a Java Servlet front-end, although the results don't necessarily have only limited use.


# Instructions
To run the app, simply clone the source and run the following in jlab/:
```shell
$ mvn clean package
$ java -jar target/dependency/jetty-runner.jar <--port number> /target/<war package>.war
```

By default, the app runs at port 8080, so use localhost:8080. By specifying --port before the .war package, you can run it at any available port.

TO-DO:
* Core logic
* API bindings
* CLI component (portmapping)
* Unit testing
