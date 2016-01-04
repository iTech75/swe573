<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>

<t:master pageTitle="Sorry :( Something went wrong!" addGoogleMap="false" enableGoogleMapLocationChange="false">
    <jsp:body>
        <div class="row">
            <div class="col-md-5">
                <h1 class="text-center">Oups!!!</h1>
                <h4 class="text-center">This is an unexpected error!</h4>
                <h2 class="text-center">Our engineers will work hard not to make you sorry again!</h2>
                <h5 class="text-center">Date of error: ${datetime}</h5>
                <h5 class="text-center">url: ${url}</h5>
                 <h5 class="text-center">message: ${exception.getMessage()}</h5>
            </div>
            <div class="col-md-7">
                <img src="/acp/resources/images/sbC50.jpg" class="img-responsive img-rounded" style="margin: 0 auto"/>
            </div>
        </div>
    </jsp:body>
</t:master>