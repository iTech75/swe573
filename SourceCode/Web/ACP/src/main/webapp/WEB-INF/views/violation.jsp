<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="${violation.getTitle()}">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">${violation.getTitle()}</h3>
        </div>
        <div class="panel-body">
            <div class="row">
                <div class="col-xs-6 col-xs-offset-1">
                    <div class="row">
                        <div class="col-md-2">
                            <span class="label label-default">Description</span>
                        </div>
                        <div class="col-md-6 form-control-static">${violation.getDescription()}</div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <span class="label label-default">Location</span>
                        </div>
                        <div class="col-md-6 form-control-static">${violation.getLatitude()},${violation.getLongitude()}</div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <span class="label label-default">User</span>
                        </div>
                        <div class="col-md-6 form-control-static">${violation.getUserName()}</div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <span class="label label-default">Since</span>
                        </div>
                        <div class="col-md-6 form-control-static">${violation.getSince() }</div>
                    </div>
                    <div class="row">
                        <a class="btn btn-primary" href="/acp/violation/edit">Edit</a>
                    </div>
                </div>
                <div class="col-xs-4">
                    <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img>
                </div>
            </div>
        </div>
    </div>
</t:master>