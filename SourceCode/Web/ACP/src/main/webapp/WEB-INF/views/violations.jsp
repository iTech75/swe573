<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="last 10 Violations..">
    <div class="panel panel-default">
        <div class="panel-heading">Violation information</div>
        <div class="panel-body">
    
    <c:forEach items="${violations}" var="violation">
        <div class="row">
            <div class="col-xs-3"><img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img></div>
            <div class="col-xs-9">
                <div class="row"><div class="col-xs-1">${violation.getId()}</div></div>
                <div class="row"><div class="col-xs-4"><a href="/acp/violation/${violation.getId()}">${violation.getTitle()}</a></div></div>
                <div class="row"><div class="col-xs-7">${violation.getDescription()}</div></div>
            </div>
        </div>
        <hr>
    </c:forEach>
        </div>
    </div>
</t:master>