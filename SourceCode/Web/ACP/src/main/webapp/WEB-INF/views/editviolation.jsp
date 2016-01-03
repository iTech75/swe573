<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="Edit Violation" addGoogleMap="true" enableGoogleMapLocationChange="true">
    <jsp:attribute name="scripts">
        <script lang="text/javascript">
        var violationType = ${violationType};
        var selectedControlType = ${selectedControlType}; 

        $(document).ready(
            function() {
                $('#addNewControlButtonRow').removeClass('hidden'); 
                $('#violationTypeRow').addClass('hidden'); 
                $('#controlTypeRow').addClass('hidden'); 
                $('#newControlEntryPanel').addClass('hidden');
                $("[id='newControlValueCheckbox']").bootstrapToggle({
                     on: 'Exists', 
                     off: 'None',
                     onstyle: 'danger',
                     offstyle: 'success',
                     width: 100
                });

                $('#addNewControlButton').on('click', function(){
                    try{
                        loadViolationTypes(0);
                        
                        $('#violationTypeRow').removeClass('hidden'); 
                        $('#addNewControlButtonRow').addClass('hidden');
                        scrollToView($('#violationType'));
                    }
                    finally{
                        return false;
                    }
                });
                
                $('#violationType').on('change',function() {
                    $('#controlType').empty();
                    if($('#violationType').val() != '0'){
                        loadControlTypesFor($('#violationType').val(), 0);
                        $('#controlTypeRow').removeClass('hidden'); 
                        scrollToView($('#controlType'));
                    }
                    else{
                        $('#controlTypeRow').addClass('hidden'); 
                        $('#newControlEntryPanel').addClass('hidden');
                    }
                });
                
                $('#controlType').on('change',function() {
                    if($('#controlType').val() == '0'){
                        $('#newControlEntryPanel').addClass('hidden');
                    }
                    else{
                        $('#newControlEntryPanel').removeClass('hidden');
                        arrangeNewControlPanelForSelectedControl($('#controlType').val());
                        scrollToView($('#newControlEntryPanel'));
                        
                    }
                });
            }
        );
            
        function loadViolationTypes(activeType){
            var json = $.getJSON("/acp/violation/types")
            .done(function(data){
                $("#violationType").empty();
                $.each(data, function(key, value){
                    $("#violationType").append("<option value='" + value.id + "'>" + value.description + "</option>");
                });
                $("#violationType").val(activeType);
            });
        }
        
        function loadControlTypesFor(violationType, selectedControlType){
            if(violationType == 0){
                return;
            }
            var json = $.getJSON("/acp/violation/metas/" + violationType)
            .done(function(data) {
                $("#controlType").empty();
                $.each(data, function(key, value){
                    $("#controlType").append("<option value='" + value.id + "'>" + value.description + "</option>");
                });
                $("#controlType").val(selectedControlType);
            })
        }
        
        function arrangeNewControlPanelForSelectedControl(selectedControlType){
            if(selectedControlType == 0){
                return;
            }
            var json = $.getJSON("/acp/violation/meta/" + selectedControlType)
            .done(function(data) {
                $('#newControlValueLabel').html(data.description);
                $('#newControlValueCheckboxLabel span.newControlValueCheckboxLabel').text(data.description);
                $('#newUnit').val(data.expectedValueUnit);
                $('#expectedValue').html(data.expectedValue + " (" + data.expectedValueUnit + ")");
                
                switch (data.type) {
                case 0:
                    $('#newControlValueTextRow').removeClass("hidden");
                    $('#newControlValueCheckboxRow').addClass("hidden");
                    break;

                case 1:
                    $('#newControlValueTextRow').addClass("hidden");
                    $('#newControlValueCheckboxRow').removeClass("hidden");
//                     $("[id='newControlValueCheckbox']").bootstrapSwitch('labelText', data.description);
                    break;

                default:
                    break;
                }
            })
        }
        
        function scrollToView(target)
        {
            if(target){
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 1000);
            }
        }

        function getParameterByName(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"); 
            var results = regex.exec(location.search);
            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }
        </script>
    </jsp:attribute>

    <jsp:body>
    <form id="violationForm" action="/acp/violation/save" method="post">
        <div class="row">
            <div class="col-md-3 col-md-offset-1">
                <div class="row">
                    <img class="img-responsive img-rounded" src="/acp/image/${violation.getId()}"></img>
                </div>
                <div class="row">
                    &nbsp;
                </div>
                <div class="row">
                    <table class="table table-responsive table-striped">
                        <caption class="caption">Violation Items</caption>
                        <tr>
                            <th>Violation Type</th>
                            <th>Control Type</th>
                            <th>Value</th>
                            <th>&nbsp;</th>
                        </tr>
                        <c:forEach items="${violation.getViolationData()}" var="item">
                        <tr>
                            <td>${item.getViolationMetaTypeDescription()}</td>
                            <td>${item.getViolationMetaDescription()}</td>
                            <td>${item.getValue()} ${item.getUnit().toString()}</td>                         
                            <td><a class="btn btn-success" href="/acp/violation/removecontrol/${item.getId()}">remove</a></td>                         
                        </tr>
                        </c:forEach>
                    </table>
                </div>
                
                <div class='row' id='violationTypeRow'>
                    <div class="form-group">
                        <label for="violationType" class="control-label">Violation Type</label>
                        <select class="form-control" id="violationType" name="violationType">
                            <option value="0">Please wait...loading</option>
                        </select>
                    </div>
                </div>
            
                <div class='row' id='controlTypeRow'>
                    <div class="form-group">
                        <label for="controlType" class="control-label">Control Type</label> 
                        <select class="form-control" id="controlType" name="controlType">
                            <option value="0">Please select violation type first</option>
                        </select>
                    </div>
                </div>
            
            
                <div class="row" id='addNewControlButtonRow'>
                    <button class="btn btn-danger" id="addNewControlButton">Add new violation check...</button>
                </div>
                
                <div class="row" id="newControlEntryPanel">
                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title">Details</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row" id="newControlValueTextRow">
                                <div class="form-group">
                                    <label for='newControlValue' class='control-label' id="newControlValueLabel">newControlValue</label>
                                    <input type='text' class='form-control' id='newControlValue' name='newControlValue'>
                                </div>
                            </div>  
                        
                            <div class="row"  id="newControlValueCheckboxRow">
                                 <div class="form-group">
                                    <label id="newControlValueCheckboxLabel" for="newControlValueCheckbox">
                                        <span class="newControlValueCheckboxLabel">#newControlValueCheckboxLabel</span>
                                        <input type='checkbox' id='newControlValueCheckbox' name='newControlValueCheckbox' class="checkbox">
                                    </label>
                                 </div>
                            </div>  
                        
                            <div class="row">
                                <div class='form-group'>
                                    <label for='newUnit' class='control-label'>Unit</label>
                                    <select class='form-control' id='newUnit' name='newUnit'>
                                        <optgroup label='length'>
                                            <option>MM</option>
                                            <option>CM</option>
                                            <option>M</option>
                                        </optgroup>
                                        <optgroup label='slope'>
                                            <option>DEGREE</option>
                                        </optgroup>
                                        <optgroup label='boolean'>
                                            <option>BOOLEAN</option>
                                        </optgroup>
                                    </select>
                                </div>
                            </div>
                        
                            <div class="row">
                                <div class='form-group'>
                                    <label for='expectedValue' class='control-label'>Expected value</label>
                                    <p class='form-control' id='expectedValue'>...</p>
                                </div>
                            </div>
                            
                            <div class="row">
                                <div class='form-group'>
                                  <button class="btn btn-warning" id="addControl" name="operation" value="add">Add</button>
                                </div>
                            </div>
                            
                        </div>
                    </div>    
                </div>
                
            </div>
            <div class="col-md-6 col-md-offset-1">
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
                        <label for="violationlocation" class="control-label">Violation Location</label>
                        <div id="map-container" class="form-control"></div>
                        <input class="form-control control" id="violationLocation" name="violationLocation" value="${violation.getLocation()}" readonly>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group ">
                        <button class="btn btn-primary" id="saveViolationButton" name="operation" value="save">Save Violation</button>
                        <button class="btn btn-secondary" id="cancelButton" name="operation" value="cancel">Cancel</button>
                    </div>
                </div>
                
            </div>
        </div>    
    </form>
    </jsp:body>
</t:master>