<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
        <title></title>
        <link rel="stylesheet" type="text/css" href="http://getbootstrap.com/examples/jumbotron-narrow/jumbotron-narrow.css">
        <style type="text/css">
            
            
            img.logo {
                display: block;
                margin-left: auto;
                margin-right: auto;
                height:20%;
                margin-top:5%;
                margin-bottom:5%;
                
            }
            
            .title {
                display: block;
                margin-left: auto;
                margin-right: auto;
                text-align: center;
                font-size: 2em;
            }
            
            .feature {
                display:block;
                margin-left: auto;
                margin-right: auto;
                text-align: left;
                width: 75%;
                
               
            }
            span.glyphicon-ok {
                margin-right: 3%;
            }
            
            h1.small {
                
                font-size: 2.5em;
            }
            
            
            
            
        </style>
    </head>
    <body>
    <div class="container">
      <div class="header">
        <ul class="nav nav-pills pull-right">
          <li class="${now}"><a href="${pageContext.request.contextPath}/">Now</a></li>
          <li class="${history}"><a href="${pageContext.request.contextPath}/history">History</a></li>
          <li class="${about}"><a href="${pageContext.request.contextPath}/about">About</a></li>
        </ul>
        <h3 class="text-muted">Now</h3>
      </div>

      <div class="jumbotron">
        <h1 class="small">Flavour of the Now</h1>
        <c:choose>
            <c:when test="${message == 'oresult'}">
                <h2>Cannot determine result.</h2>
            </c:when>

            <c:otherwise>
                <img class="logo" src="<spring:message code="${message}.image"/>" />
                <h2>Notable features:</h2>  

                <div class="feature">
                    <p><span class="glyphicon glyphicon-ok"></span><spring:message code="${message}.feature1"/></p>
                    <p><span class="glyphicon glyphicon-ok "></span><spring:message code="${message}.feature2"/></p>
                    <p><span class="glyphicon glyphicon-ok"></span><spring:message code="${message}.feature3"/></p>
                </div>
            </c:otherwise>
        </c:choose>
                
        
      </div>



      <div class="footer">
        <p><a href="https://github.com/narck">narck</a></p>
      </div>

    </div> <!-- /container -->
    
        
        
        
        
        
        
    </body>
</html>