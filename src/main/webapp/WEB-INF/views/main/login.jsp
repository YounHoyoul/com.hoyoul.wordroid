<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$(document).ready(function(){
	$("#join").click(function(){
		document.location.href="joinpage";
	});
	
	$("#loginform").submit(function(){
		
		$(".error").hide();
		var hasError = false;
		
		var email = $("#loginid").val();
		if(loginid == ''){
			hasError = true;
			$("#loginid_error").html("<span class='error'>please enter your login ID</span>");
		}
		
		var password = $("#password").val();
        if(password == ''){
            hasError = true;
            $("#password_error").html("<span class='error'>please enter your password</span>");
        }
        
		return !hasError;
	});
});

$(document).ready(function(){
	$("#submit,#join").button();
});
</script>
<style>
</style>
<form:form id="loginform" method="post" action="main/login" commandName="user">
<table>
    <tr>
        <td align="right"><form:label path="loginId">Login ID</form:label></td>
        <td><form:input path="loginId" id="loginid"/></td>
        <td id="loginid_error" align="left"></td>
    </tr>
    <tr>
        <td align="right"><form:label path="password">Password</form:label></td>
        <td><form:input path="password" id="password" type="password"/></td>
        <td id="password_error" align="left"></td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <input id="submit" type="submit" value="Sing up" />
            <input id="join" type="button" value="Join" />
        </td>
    </tr>
</table>
</form:form>
