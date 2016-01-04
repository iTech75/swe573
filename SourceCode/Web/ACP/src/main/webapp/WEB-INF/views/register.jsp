<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="ACP Register New User">
    <div class="row col-md-6 col-md-offset-3">
        <h1>ACP Register New User</h1>
        <form action="${pageContext.request.contextPath}/register" method="post" id="registerForm" name="registerForm">
            <span id="inputGroupWarningStatus" class="sr-only">(warning)</span>
            <div class="form-group">
                <label for="username" class="control-label">User Name</label>
                <input type="text" id="username" name="username" class="form-control" aria-describedby="inputGroupWarningStatus" placeholder="Username">
            </div>
            
            <div class="form-group">
                <label for="email" class="control-label">Email</label>
                <div class="input-group">
                    <span class="input-group-addon">@</span> 
                    <input type="email" id="email" name="email" class="form-control" aria-describedby="inputGroupWarningStatus" placeholder="EMail">
                </div>
            </div>
            
            <div class="form-group">
                <label for="password">Password</label>
                <div class="input-group">
                    <span class="input-group-addon">*</span> 
                    <input type="password" id="password" name="password" class="form-control" placeholder="Password">
                </div> 
            </div>
            <div class="form-group">
                <label for="password">Password Repeat</label>
                <div class="input-group">
                    <span class="input-group-addon">*</span> 
                    <input type="password" id="passwordRepeat" name="passwordRepeat" class="form-control" placeholder="Password-Repeat">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Register</button>
                <button type="reset" class="btn btn-danger">Cancel inputs</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</t:master>