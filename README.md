jlab - Flavour of the Month
====

University of Helsinki Java assignment for "Javalabra 2013"

Need to use a database for your program? This app will tell you which one to choose.
Flavour of the Month parses Twitter feeds to determine what is your database of choice.


# Instructions
To run the app, simply clone the source and run the following in jlab/:
```shell
$ mvn clean package
$ java -jar target/dependency/jetty-runner.jar /target/<war package>.war
```

By default, the app runs at port 8080, so use localhost:8080.

TO-DO:
* Core logic
* API bindings
* CLI component (portmapping)
* Unit testing
