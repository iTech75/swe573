<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="Near by Violations" addGoogleMap="true" enableGoogleMapLocationChange="true">
    <jsp:body>
        <div class="row">
            <div class="col-md-6 col-md-offset-1">
                <div class="row">
                    <div class="form-group">
                        <label class="control-label" for="map-container">Location</label>
                        <div id="map-container" class="form-control-static img-rounded"></div>
                    </div>
                </div>
                <form action="/acp/violation/findnearby" method="get">
                    <div class="row">
                        <input class="form-control control" id="violationLocation" name="violationLocation" value="" readonly>
                    </div>
                    <div class="row">
                        <button type="submit" class="form-control control" id="violationLocation" name="operation" value="find">Find Nearby Violations</button>
                    </div>
                </form>
            </div>
            
            <div class="col-md-3 col-md-offset-1">
                <c:forEach items="${violations}" var="item">
                <div class="row">
                    <a class="form-control" href="/acp/violation/${item.id}">${item.title}</a>
                </div>
                </c:forEach>
            </div>
        </div>
    </jsp:body>
</t:master>