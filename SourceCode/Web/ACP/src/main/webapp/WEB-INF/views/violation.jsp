<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="${violation.getTitle()}" addGoogleMap="true" enableGoogleMapLocationChange="false">
    <jsp:body>
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">${violation.getTitle()}</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-5 col-sm-offset-1">
                        <div class="row">
                            <div class="form-group">
                                <label class="control-label" for="idDescription">Description</label>
                                <div class="form-control-static" id="idDescription">${violation.getDescription()}</div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group">
                                <label class="control-label" for="map-container">Location</label>
                                <div id="map-container" class="form-control-static"></div>
                            </div>
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
                    </div>
                    <div class="col-xs-5 col-md-offset-1">
                        <div class="row">
                            <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img>
                        </div>
                        <div class="row">
                        &nbsp;
                        </div>
                        
                        <div class="row">
                            <div class="panel-warning">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Commands</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="row">
                                        <a class="btn btn-primary form-control" href="/acp/violation/edit">Edit Violation</a>
                                    </div>
                                </div>
                            </div>                        
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:master>