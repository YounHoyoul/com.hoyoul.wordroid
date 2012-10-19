<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
$(document).ready(function(){
	$("#btn_join").click(function(){
		document.location.href="joinpage";
	});
	
	$("#btn_submit").click(function(){
		$("#loginform").trigger("submit");
	});
	
	$("#loginform").submit(function(){
		
		return $(this).form('validate');
		
		/*
		$(".error").hide();
		var hasError = false;
		
		var loginid = $("#loginid").val();
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
		*/
		
	});
	
	/*
	$('#loginform').form({
		onSubmit:function(){
			return $(this).form('validate');
		},
		success:function(data){
			$.messager.alert('Info', data, 'info');
		}
	});
	*/
	
});

/*
$(document).ready(function(){
	$("#submit,#btn_join").button();
});
*/
</script>
<style>
</style>
<div class="easyui-panel" title="Sign-up Form" style="width:300px;background:#fafafa;padding:30px;">
	<form:form id="loginform" method="post" action="main/login" commandName="user">
	<table>
		<tr>
			<td align="right"><form:label path="loginId">Login ID</form:label></td>
			<td><form:input path="loginId" id="loginid" class="easyui-validatebox" required="true"/></td>
			<td id="loginid_error" align="left"></td>
		</tr>
		<tr>
			<td align="right"><form:label path="password">Password</form:label></td>
			<td><form:input path="password" id="password" type="password" class="easyui-validatebox" required="true"/></td>
			<td id="password_error" align="left"></td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<a href="javascript:void(0)" id="btn_submit" class="easyui-linkbutton">Sing up</a>
				<a href="javascript:void(0)" id="btn_join" class="easyui-linkbutton">Join</a>
			</td>
		</tr>
	</table>
	</form:form>
</div>	
