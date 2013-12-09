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
          <li class="${history}"><a href="${pageContext.request.contextPath}/history">History</a></li>
          <li class="${about}"><a href="${pageContext.request.contextPath}/about">About</a></li>
        </ul>
        <h3 class="text-muted">About</h3>
      </div>

      <div class="jumbotron">
        <h1 class="small">Today I will choose...</h1><br/>
        Have you ever woken up, without knowing to which database you will port everything in production into?
        <br /><br/>
        Never worry - Flavour of the Now reveals it to you! Now you don't have to explain anyone why you broke everything, because Flavour
        of the Now shows you what's hip and cool.
        </div>
        
        <h2>Disclaimer</h2>
        Please note that Flavour of the Now/Month and the features provided on this site are intended to be satire and not accurate descriptions of respective software. All of them have their own advantages and limitations!
        <h2>In the meantime</h2>
        Fork this app on <a href="https://github.com/narck/jlab">Github</a>!

      <div class="row marketing">

      <div class="footer">
          <p><a href="https://github.com/narck">narck</a></p>
      </div>

    </div> <!-- /container -->
    
        
        
        
        
        
        
    </body>
</html>