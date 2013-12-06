<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
        <h3 class="text-muted">About</h3>
      </div>

      <div class="jumbotron">
        <h1 class="small">Today I will choose...</h1>
        

      </div>

      <div class="row marketing">

      <div class="footer">
        <p>narck@github.com</p>
      </div>

    </div> <!-- /container -->
    
        
        
        
        
        
        
    </body>
</html>