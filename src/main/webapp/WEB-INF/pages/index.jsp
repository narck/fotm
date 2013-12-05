<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
        <title></title>
    
        <style type="text/css">
            body {
                color: black;
                width: 50%;
                margin-left: auto;
                margin-right: auto;
                margin-top: 10%;
                
            }
            
            img.logo {
                display: block;
                margin-left: auto;
                margin-right: auto;
                height:20%;
                
            }
            
            .title {
                display: block;
                margin-left: auto;
                margin-right: auto;
                text-align: center;
                font-size: 2em;
            }
            
            .features {
                display:block;
                margin-left: auto;
                margin-right: auto;
            }
            
            
        </style>
    </head>
    
    <body>
        <span class="title">The Flavour of the Now is...</span>
        
        <img class="logo" src="<spring:message code="${message}.image" arguments="${message}"/>" />
        <h2>Notable features:</h2>  
        
        <div class="feature">
          <ul class="features">
            <li><p><spring:message code="${message}.feature1" arguments="${message}"/></p></li>
            <li><p><spring:message code="${message}.feature2" arguments="${message}"/></p></li>
            <li><p><spring:message code="${message}.feature3" arguments="${message}"/></p></li>
        </ul></div>
        
        
    </body>
</html>