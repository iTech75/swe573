<%@ page language="java" contentType="text/html; charset=windows-1254" pageEncoding="windows-1254"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t"%>
<t:master pageTitle="ACP Login">
    <div class="row col-md-6 col-md-offset-3">
        <h1>ACP Login</h1>
        <form action="/acp/login/" method="post" id="loginForm" name="loginForm">
            <div class="form-group has-warning has-feedback">
                <label for="username" class="control-label">User Name</label>
                <div class="input-group">
                    <span class="input-group-addon">@</span> <input type="email" id="username" name="username" class="form-control"
                        aria-describedby="inputGroupWarningStatus" placeholder="EMail">
                </div>
                <span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span> <span
                    id="inputGroupWarningStatus" class="sr-only">(warning)</span>
            </div>
            <div class="form-group">
                <label for="password">Password</label> <input type="password" id="password" name="password" class="form-control"
                    placeholder="Password">
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>
        </form>
    </div>
</t:master>