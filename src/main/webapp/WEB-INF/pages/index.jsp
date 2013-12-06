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
            .fitem {
                padding-right: 50px;
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
          <li class="${history}"><a href="${pageContext.request.contextPath}/history">Month</a></li>
          <li class="${about}"><a href="${pageContext.request.contextPath}/about">About</a></li>
        </ul>
        <h3 class="text-muted">Now</h3>
      </div>

      <div class="jumbotron">
        <h1 class="small">Flavour of the Now</h1>
        
        <img class="logo" src="<spring:message code="${message}.image" arguments="${message}"/>" />
        <h2>Notable features:</h2>  
        
        <div class="feature">
            <p><span class="glyphicon glyphicon-ok fitem"></span><spring:message code="${message}.feature1" arguments="${message}"/></p>
            <p><span class="glyphicon glyphicon-ok fitem"></span><spring:message code="${message}.feature2" arguments="${message}"/></p>
            <p><span class="glyphicon glyphicon-ok fitem"></span><spring:message code="${message}.feature3" arguments="${message}"/></p>
</div>
      </div>

      <div class="row marketing">
        <div class="col-lg-6">
          <h4>Subheading</h4>
          <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>

          <h4>Subheading</h4>
          <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras mattis consectetur purus sit amet fermentum.</p>

          <h4>Subheading</h4>
          <p>Maecenas sed diam eget risus varius blandit sit amet non magna.</p>
        </div>


      <div class="footer">
        <p>narck@github.com</p>
      </div>

    </div> <!-- /container -->
    
        
        
        
        
        
        
    </body>
</html>