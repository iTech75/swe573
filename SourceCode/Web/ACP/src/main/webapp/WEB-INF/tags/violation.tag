<%@ tag language="java" pageEncoding="ISO-8859-9"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">Violation information</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-md-2">
                    <span class="label label-default">Title</span>
                </div>
                <div class="col-md-6">${violation.getTitle()}</div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <span class="label label-default">Description</span>
                </div>
                <div class="col-md-6">${violation.getDescription()}</div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <span class="label label-default">Location</span>
                </div>
                <div class="col-md-6">${violation.getLatitude()},${violation.getLongitude()}</div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <span class="label label-default">User</span>
                </div>
                <div class="col-md-6">${violation.getUserName()}</div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <span class="label label-default">Since</span>
                </div>
                <div class="col-md-6">${violation.getSince() }</div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <span class="label label-default">Image</span>
                </div>
                <div class="col-md-6">
                    <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img>
                </div>
            </div>
        </div>
    </div>
    <jsp:doBody/>