<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
        <title>Flavour of the Month</title>
    
        <style type="text/css">
            body {
            color: black;
            width: 50%;
            margin-left: 10%;
            }
        </style>
    </head>
    
    <body>
        <h1>Flavour of the Month</h1>
        <h2>Find out what it is!</h2>
        <p>MSG: <c:out value="${message}" /></p>
    </body>
</html>
