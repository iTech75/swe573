<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="Edit Violation">
    <jsp:attribute name="scripts">
        <script lang="text/javascript">
            $(document).ready(
                    function() {
                        $('#violationType').on('change',function() {
                            $('#violationForm').submit();

                        });
                        $('#controlType').on('change',function() {
                            $('#violationForm').submit();
                            
                        });
                    
                        var vType = ${violationType};
                        if (vType) {
                            $('#violationType').val(vType);
                        }
                        var vControl = ${selectedControlType};
                        if (vControl) {
                            $('#controlType').val(vControl);
                        }
                    }
            );
    
            function getParameterByName(name) {
                name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"); 
                var results = regex.exec(location.search);
                return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
            }
        </script>
    </jsp:attribute>

    <jsp:body>
    <form id="violationForm" action="save" method="post">
        <div class="row">
            <div class="col-xs-3 col-xs-offset-1">
                <div class="row">
                    <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img>
                </div>
                <c:forEach items="${violation.getViolationData()}" var="item">
                    <div class="row">
                      ${item.getViolationMetaDescription()}:, ${item.getValue()} ${item.getUnit().toString()}                         
                    </div>
                </c:forEach>
            </div>
            <div class="col-xs-6">
                <div class='row'>
                    <div class="form-group">
                        <label for="violationTitle" class="control-label">Violation Title</label>
                        <input class="form-control" type="text" id="violationTitle" name="violationTitle" value="${violation.getTitle() }">
                    </div>
                </div>
            
                <div class='row'>
                    <div class="form-group">
                        <label for="violationDescription" class="control-label">Violation Description</label>
                        <textarea class="form-control" rows="6" id="violationDescription" name="violationDescription">${violation.getDescription()}</textarea>
                    </div>
                </div>
            
                <div class='row'>
                    <div class="form-group">
                        <label for="violationType" class="control-label">Violation Type</label>
                        <select class="form-control" id="violationType" name="violationType">
                            <option value="0">Please select main violation type</option>
                            <option value="2">Pavement ramp</option>
                            <option value="1">Other</option>
                        </select>
                    </div>
                </div>
            
                <div class='row'>
                    <div class="form-group">
                        <label for="controlType" class="control-label">Control Type</label> 
                        <select class="form-control" id="controlType" name="controlType">
                            <option value="0">Please select control type</option>
                            <c:forEach var="item" items="${violationMetaList}"> 
                                <option value="${item.getId()}" ${item.getId() == selectedControlType ? 'selected':'' }>${item.getDescription()}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" id="metaDescription" name="metaDescription" value="${selectedMetaDescription}">
                    </div>
                </div>
            
                <div class="row ${selectedControlType==0?'hidden':''}">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">Details</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="form-group">
                                    <label for='newControlValue' class='control-label'>newControlValue</label>
                                    <input type='text' class='form-control' id='newControlValue' name='newControlValue'>
                                </div>
                            </div>  
                        
                            <div class="row">
                                <div class='form-group'>
                                    <label for='newUnit' class='control-label'>Unit</label>
                                    <select class='form-control' id='newUnit' name='newUnit'>
                                        <optgroup label='length'>
                                            <option>mm</option>
                                            <option>cm</option>
                                            <option>m</option>
                                        </optgroup>
                                        <optgroup label='slope'>
                                            <option>degree</option>
                                        </optgroup>
                                        <optgroup label='boolean'>
                                            <option>TrueFalse</option>
                                        </optgroup>
                                    </select>
                                </div>
                            </div>
                        
                            <div class="row">
                                <div class='form-group'>
                                    <label for='%s' class='control-label'>Expected value</label>
                                    <p class='form-control' id='%s'>%s</p>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class='form-group'>
                                  <button class="btn btn-warning" id="addControl" name="addControl" value="1">Add</button>
                                </div>
                            </div>
                            
                        </div>
                    </div>    
                </div>

                <div class="row">
                    <div class="form-group ">
                        <button class="btn btn-primary" id="saveViolation" name="saveViolation" value="1">Save Violation</button>
                        <button class="btn btn-secondary">Cancel</button>
                    </div>
                </div>
                            
                
            </div>
        </div>    
    </form>
    </jsp:body>
</t:master>