jlab - Flavour of the Now (formerly Month)
====

University of Helsinki Java assignment for "Javalabra 2013"

Need to use a database for your program? This app will tell you which one to choose.
Flavour of the Now (FOTN) parses Twitter feeds to determine what is your database of choice. The base functionality of the app is built upon the Twitter API 1.1, and it uses the twitter4j library.
It runs a Java Servlet front-end, although the results don't necessarily have only limited use. Users primarily make requests to the servlet, when the core logic does its work behind the scenes. 

# NOTE
Ycombinator component has been redacted.

# Instructions
To run the app, simply clone the source and run the following in jlab/:
```shell
$ mvn clean package
$ java -jar target/dependency/jetty-runner.jar <--port {number}> target/<war package>.war
```

By default, the app runs at port 8080, so use localhost:8080. By specifying --port before the .war package, you can run it at any available port. Maven Jetty plugin is also included, so you can use mvn jetty:run as well.

If you need to test the app, you can register an app @ https://dev.twitter.com/

TO-DO:

