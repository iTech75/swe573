<%@tag description="ACP master tag" pageEncoding="windows-1254"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="pageTitle" required="true"%>
<%@attribute name="scripts" fragment="true" required="false"%>
<!DOCTYPE html">
<html lang="en">
<head>
<meta charset="windows-1254">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
<title>${pageTitle}</title>
<link href="/acp/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="padding-top: 90px">
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">ACP <small><em>(Accessibility Control Platform)</em></small></a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="/acp/violation">Violations</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container-fluid">
        <!--  ************************** BODY PART *********************************************   -->
        <jsp:doBody />
        <!--  ************************** BODY PART *********************************************   -->
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="/acp/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <jsp:invoke fragment="scripts" />
</body>
</html>
