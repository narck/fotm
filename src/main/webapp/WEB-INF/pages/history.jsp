<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
        <title></title>
        <link rel="stylesheet" type="text/css" href="https://github.com/twbs/bootstrap/blob/master/dist/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="http://getbootstrap.com/examples/jumbotron-narrow/jumbotron-narrow.css">
        <style type="text/css">
            
            
            img.logo {
                display: block;
                margin-left: auto;
                margin-right: auto;
                height:20%;
                margin-top:5%;
                
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
            
            h1.small {
                
                font-size: 2.5em;
                
            }
            
            span.glyphicon-stop {
                margin-right: 3%;
            }
            
            
        </style>
    </head>
    <body>
    <div class="container">
      <div class="header">
        <ul class="nav nav-pills pull-right">
          <li class="${now}"><a href="${pageContext.request.contextPath}/">Now</a></li>
          <li class="${history}"><a href="${pageContext.request.contextPath}/history">Month</a></li>
          <li class="${about}"><a href="${pageContext.request.contextPath}/about">About</a></li>
        </ul>
        <h3 class="text-muted">History</h3>
      </div>

        
        
      <div class="jumbotron">
        <h1 class="small">Flavour of the Month</h1>
        
        <c:choose>
            <c:when test="${month == 'oresult'}">
                <h2>Cannot determine result. Maybe the cache is empty?</h2>
            </c:when>

            <c:otherwise>
                <img class="logo" src="<spring:message code="${month}.image"/>"/>
                
                <h2>Latest results: </h2>
                <c:forEach var="item" items="${histItems}">
                    <p><span class="glyphicon glyphicon-stop"></span><spring:message code="${item.hashtag}.title"/> - fetched <fmt:formatDate value="${item.dateFetched}" pattern="E, dd-MM-yy 'at' HH:mm" /></p>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        
        
       
        
        

      </div>


      <div class="footer">
        <p>narck@github.com</p>
      </div>

    </div> <!-- /container -->
    
        
        
        
        
        
        
    </body>
</html>