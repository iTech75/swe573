<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="${violation.getTitle()}" addGoogleMap="true" enableGoogleMapLocationChange="false">
    <jsp:body>
        <div class="row">
            <div class="col-md-6 col-md-offset-1">
                <div class="row">
                    <div class="h1" id="idTitle">${violation.getTitle()}</div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="control-label" for="idDescription">&nbsp;</label>
                        <div class="form-control-static" id="idDescription">${violation.getDescription()}</div>
                    </div>
                </div>

                <c:if test="${violation.getState()=='FIXED_CANDIDATE' || violation.getState()=='FIXED'}">
                    <div class="row">
                        <h3 class="alert-success">AFTER</h3>
                    </div>
                    <div class="row">
                        <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}/fix"></img>
                    </div>
                    <div class="row">
                        <h3 class="alert-danger">BEFORE</h3>
                    </div>
                </c:if>


                <div class="row">
                    <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img>
                </div>
                <c:if test="${violation.getState() == 'OPEN' && violation.getUserId() == sessionScope.userid}">
                    <div class="row">
                        <button type="button" class="btn btn-warning form-control" data-toggle="modal" data-target="#changePhotoModal">Change Photo</button>
                    </div>
                </c:if>
                <div class="row">&nbsp;</div>    
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 id="comments" class="panel-title">Comments on this violation</h3>
                    </div>
                    <div class="panel-body">
                        <c:forEach items="${violation.getComments()}" var="item">
                        <div class="row">
                            <blockquote class="well">
                                <p>${item.getContent()}</p>
                                <footer>${item.getUsername()}, ${item.getTimestamp()}</footer>
                            </blockquote>
                        </div>
                        </c:forEach>
                    </div>
                </div> 
                
            </div>
            
            <div class="col-md-3 col-md-offset-1">
                <div class="row">
                    <div class="form-group">
                        <label class="control-label" for="x">Violation Status</label>
                        <h3> <span id="x" class="from-control-static label label-danger">${violation.getState()}</span></h3>
                    </div>
                </div>
                <div class="row">
                    <c:choose>
                        <c:when test="${violation.getState() == 'OPEN'}">
                            <button type="button" class="btn btn-warning form-control" data-toggle="modal" data-target="#fixModal">Fix This!</button>
                        </c:when>
                        <c:when test="${violation.getState() == 'FIXED_CANDIDATE' && violation.getUserId() == sessionScope.userid}">
                            <form action="${pageContext.request.contextPath}/violation/approvereject" method="post">
                                <button type="submit" class="btn btn-success" name="operation" value="approve">Approve</button>
                                <button type="submit" class="btn btn-danger" name="operation" value="reject">Reject</button>
                            </form>
                        </c:when>
                    </c:choose>
                </div>
                <div class="row">
                    <hr/>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="control-label">Severity</label>
                        <div class="progress">
                            <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getSeverityForSection(1)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getSeverityForSection(1)}%">
                            </div>
                            <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getSeverityForSection(2)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getSeverityForSection(2)}%">
                            </div>
                            <div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getSeverityForSection(3)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getSeverityForSection(3)}%">
                            </div>
                            <div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getSeverityForSection(4)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getSeverityForSection(4)}%">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="control-label">Public Severity</label>
                        <div class="progress">
                            <div class="progress-bar progress-bar-info progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getPublicSeverityForSection(1)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getPublicSeverityForSection(1)}%">
                            </div>
                            <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getPublicSeverityForSection(2)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getPublicSeverityForSection(2)}%">
                            </div>
                            <div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getPublicSeverityForSection(3)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getPublicSeverityForSection(3)}%">
                            </div>
                            <div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" aria-valuenow="${violation.getPublicSeverityForSection(4)}" aria-valuemin="0" aria-valuemax="25" 
                                 style="width:${violation.getPublicSeverityForSection(4)}%">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <hr/>
                </div>
                <c:if test="${violation.getState()=='FIXED_CANDIDATE' || violation.getState()=='OPEN'}">
                    <div class="row">
                        <form id="voteForm" action="${pageContext.request.contextPath}/violation/vote" method="post">
                            <div class="form-group">
                                <label class="control-label" for="severityVote">Vote for severity</label>
                                <select class="form-control" id="publicSeverityVote" name="vote">
                                    <option value="0"${violation.getVoteForUser(sessionScope.userid) == 0 ? " selected" : ""}>LOW</option>
                                    <option value="1"${violation.getVoteForUser(sessionScope.userid) == 1 ? " selected" : ""}>MEDIUM</option>
                                    <option value="2"${violation.getVoteForUser(sessionScope.userid) == 2 ? " selected" : ""}>HIGH</option>
                                    <option value="3"${violation.getVoteForUser(sessionScope.userid) == 3 ? " selected" : ""}>VERY HIGH</option>
                                    <option value="4"${violation.getVoteForUser(sessionScope.userid) == 4 ? " selected" : ""}>MOST IMPORTANT</option>
                                </select>
                                <c:if test="${violation.getVoteForUser(sessionScope.userid) == -1 && violation.getState()=='OPEN'}">
                                    <button class="btn btn-success form-control" name="operation" value="vote">Vote</button>
                                </c:if>
                                <c:if test="${violation.getVoteForUser(sessionScope.userid) != -1}">
                                    <button class="btn btn-danger form-control" name="operation" value="deletevote">Delete Your Vote</button>
                                </c:if>
                                
                            </div>
                        </form>
                    </div>
                </c:if>
                <div class="row">
                    <div class="form-group">
                        <label class="control-label" for="map-container">Location</label>
                        <div id="map-container" class="form-control-static img-rounded"></div>
                    </div>
                </div>
                <div class="row">
                    <table class="table table-responsive table-striped">
                        <caption class="caption">Violation Items</caption>
                        <tr>
                            <th>Violation Type</th>
                            <th>Control Type</th>
                            <th>Value</th>
                        </tr>
                        <c:forEach items="${violation.getViolationData()}" var="item">
                        <tr>
                            <td>${item.getViolationMetaTypeDescription()}</td>
                            <td>${item.getViolationMetaDescription()}</td>
                            <td>${item.getValue()} ${item.getUnit().toString()}</td>                         
                        </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="row">
                    <hr/>
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
                    <hr/>
                </div>

                <c:if test="${violation.getUserId() == sessionScope.userid}">                
                    <div class="row">
                        <a class="btn btn-primary form-control" href="/acp/violation/edit">Edit Violation</a>
                    </div>
                </c:if>

                <div class="row">
                    <hr/>
                </div>
                
                <form id="addCommentForm" action="${pageContext.request.contextPath}/violation/addcomment" method="POST">                
                    <div class='row'>
                        <div class="form-group">
                            <label for="newComment" class="control-label">Enter your comment here</label>
                            <textarea class="form-control" rows="6" id="newComment" name="newComment" placeholder="New comment here"></textarea>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="form-group ">
                            <button class="btn btn-success form-control" id="addCommentButton" name="operation" value="addComment">Add Comment</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <form action="${pageContext.request.contextPath}/violation/fix" method="post" enctype="multipart/form-data">
            <div id="fixModal" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Fix Violation</h4>
                        </div>
                        
                        <div class="modal-body">
                            <div class="row panel">
                                <div class="form-group">
                                    <label class="control-label">Select a photo of the site showing the new state</label>
                                    <input type="file" id="photo" name="photo">
                                </div>
                            </div>
                        </div>
                        
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" name="operation" value="fix">Save changes</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </form>
        
        <form action="${pageContext.request.contextPath}/violation/changephoto" method="post" enctype="multipart/form-data">
            <div id="changePhotoModal" class="modal fade" tabindex="-1" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Change Violation Photo</h4>
                        </div>
                        
                        <div class="modal-body">
                            <div class="row panel">
                                <div class="form-group">
                                    <label class="control-label">Please select a photo of the site</label>
                                    <input type="file" id="photo" name="photo">
                                </div>
                            </div>
                        </div>
                        
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary" name="operation" value="change">Change</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </form>
    </jsp:body>
</t:master>