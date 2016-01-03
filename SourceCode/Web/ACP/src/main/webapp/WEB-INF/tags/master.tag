<%@tag description="ACP master tag" pageEncoding="windows-1254"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="pageTitle" required="true"%>
<%@attribute name="scripts" fragment="true" required="false"%>
<%@attribute name="addGoogleMap" required="false"%>
<%@attribute name="enableGoogleMapLocationChange" required="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1254">
<title>${pageTitle}</title>
<link href="/acp/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link href="/acp/resources/css/bootstrap-toggle.css" rel="stylesheet">
<style type="text/css">
    #message {
        position: fixed;
        top: 90%;
        left: 40%;
        width: 60%;
        height: 10%;
        z-index: 1000;
    }
    #inner-message {
        margin: 0 auto;
    }
</style>
</head>
<body style="padding-top: 90px">
    <c:if test="${message != null && !message.trim().equals('')}">
        <div id="message" style="padding: 5px;" class="fade">
          <div id="inner-message" class="alert alert-${messageType} text-center">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <strong class="pull-left">[${messageType.toUpperCase()}]</strong>${ message }          
          </div>
        </div>
    </c:if>
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
                    <li class="active"><a href="/acp/">Home</a></li>
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
    <script src="/acp/resources/js/bootstrap-toggle.js"></script>
    <script type="text/javascript">
    $(document).ready (function(){
        $("#message").hide();
        $("#message").alert();
        $("#message").fadeTo(2000, 500).slideUp(500, function(){
           $("#success-alert").alert('close');
        });   
    });
    </script>
    <c:if test="${addGoogleMap == true}">
        <style>
            #map-container {
                display: block;
                width: 100%;
               height: 300px;
            }
        </style>

        <script src="http://maps.google.com/maps/api/js?sensor=true"></script>
        
        <script>    
            var var_map;
            var var_marker;
            
            function init_map() {
                var lat = ${violation.getLatitude()};
                var lng = ${violation.getLongitude()};
                var var_location = new google.maps.LatLng(lat,lng);
                
                var var_mapoptions = {
                  center: var_location,
                  zoom: 14,
                  mapTypeId: google.maps.MapTypeId.ROADMAP,
                  mapTypeControl: true,
                  panControl:true,
                  rotateControl:true,
                  streetViewControl: true,
                };
                
                var_marker = new google.maps.Marker({
                    position: var_location,
                    map: var_map,
                    title:"Violation location"});
                
                var_map = new google.maps.Map(document.getElementById("map-container"),
                    var_mapoptions);
                var_marker.setMap(var_map); 
 
    </c:if>
    
    <c:if test="${enableGoogleMapLocationChange == true}">
                var_map.addListener('click', function(e) {
                    var_marker.setPosition(e.latLng);
                    var_map.panTo(e.latLng);
                    $('#violationLocation').val(e.latLng.lat() + ", " + e.latLng.lng());
                });
                
                addYourLocationButton(var_map, var_marker);
    </c:if>
    
    <c:if test="${addGoogleMap == true}">
            }
            
            google.maps.event.addDomListener(window, 'load', init_map);
    </c:if>
    
    <c:if test="${enableGoogleMapLocationChange == true}">
            function addYourLocationButton(map, marker) 
            {
                var controlDiv = document.createElement('div');
                
                var firstChild = document.createElement('button');
                firstChild.style.backgroundColor = '#fff';
                firstChild.style.border = 'none';
                firstChild.style.outline = 'none';
                firstChild.style.width = '28px';
                firstChild.style.height = '28px';
                firstChild.style.borderRadius = '2px';
                firstChild.style.boxShadow = '0 1px 4px rgba(0,0,0,0.3)';
                firstChild.style.cursor = 'pointer';
                firstChild.style.marginRight = '10px';
                firstChild.style.padding = '0px';
                firstChild.title = 'Your Location';
                controlDiv.appendChild(firstChild);
                
                var secondChild = document.createElement('div');
                secondChild.style.margin = '5px';
                secondChild.style.width = '18px';
                secondChild.style.height = '18px';
                secondChild.style.backgroundImage = 'url(/acp/resources/images/mylocation-sprite-1x.png)';
                secondChild.style.backgroundSize = '180px 18px';
                secondChild.style.backgroundPosition = '0px 0px';
                secondChild.style.backgroundRepeat = 'no-repeat';
                secondChild.id = 'you_location_img';
                firstChild.appendChild(secondChild);
                
                google.maps.event.addListener(map, 'dragend', function() {
                    $('#you_location_img').css('background-position', '0px 0px');
                });
            
                firstChild.addEventListener('click', function() {
                    var imgX = '0';
                    var animationInterval = setInterval(function(){
                        if(imgX == '-18') imgX = '0';
                        else imgX = '-18';
                        $('#you_location_img').css('background-position', imgX+'px 0px');
                    }, 500);
                    if(navigator.geolocation) {
                        navigator.geolocation.getCurrentPosition(function(position) {
                            var latlng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                            marker.setPosition(latlng);
                            map.setCenter(latlng);
                            clearInterval(animationInterval);
                            $('#you_location_img').css('background-position', '-144px 0px');
                        });
                    }
                    else{
                        clearInterval(animationInterval);
                        $('#you_location_img').css('background-position', '0px 0px');
                    }
                });
                
                controlDiv.index = 1;
                map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(controlDiv);
            }            
    </c:if>
    <c:if test="${addGoogleMap == true}">
        </script>
    </c:if>
    <jsp:invoke fragment="scripts" />
</body>
</html>
