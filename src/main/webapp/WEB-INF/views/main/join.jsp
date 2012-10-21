<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	.short {color:red;font-size:13px;}
	.weak {color:yellow;font-size:13px;}
	.good {color:blue;font-size:13px;}
	.strong {color:green;font-size:13px;}
</style>
<script>
$(document).ready(function(){
	
	$.extend($.fn.validatebox.defaults.rules, {  
	    duplicate: {  
	        validator: function(value,param){
	        	var returnVal = $.ajax({	
			        				url:"../main/check/"+value,
									async:false,
									cache:false,
									type:"GET"
								}).responseText;
	        	
	        	return returnVal.trim() == "true";
	        },  
	        message: 'This user ID is already occupied.'  
	    }  
	}); 

	$("#btn_submit").click(function(){
		$("#joinform").trigger("submit");
	});	
	
	$("#joinform").submit(function(){
		return $(this).form('validate');
	});
	
	$('#password').keyup(function(){
		$('#result').html(checkStrength($('#password').val()));
	});
	
	$.extend($.fn.validatebox.defaults.rules, {  
	    equals: {  
	        validator: function(value,param){  
	            return value == $(param[0]).val();  
	        },  
	        message: 'Field do not match.'  
	    }  
	}); 
	
	function checkStrength(password){
		 
		//initial strength
		var strength = 0;
	 
		//if the password length is less than 6, return message.
		if (password.length < 6) {
			$('#result').removeClass();
			$('#result').addClass('short');
			return 'short';
		}
	 
		//length is ok, lets continue.
	 
		//if length is 8 characters or more, increase strength value
		if (password.length > 7) strength += 1;
	 
		//if password contains both lower and uppercase characters, increase strength value
		if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  strength += 1;
	 
		//if it has numbers and characters, increase strength value
		if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  strength += 1;
	 
		//if it has one special character, increase strength value
		if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/))  strength += 1;
	 
		//if it has two special characters, increase strength value
		if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,",%,&,@,#,$,^,*,?,_,~])/)) strength += 1;
	 
		//now we have calculated strength value, we can return messages
	 
		//if value is less than 2
		if (strength < 2 ) {
			$('#result').removeClass();
			$('#result').addClass('weak');
			return 'Weak';
		} else if (strength == 2 ) {
			$('#result').removeClass();
			$('#result').addClass('good');
			return 'Good';
		} else {
			$('#result').removeClass();
			$('#result').addClass('strong');
			return 'Strong';
		}
	}
});
</script>
<div class="easyui-panel" title="Join Form" style="width:350px;background:#fafafa;padding:10px;">
	<form:form id="joinform" method="post" action="../main/join">
	<table>
		<tr>
			<td align="right">*Login ID </td>
			<td><input type="text" name="loginId" id="loginid" class="easyui-validatebox" required="true" validType="duplicate"/> </td>
			<td id="loginid_error" align="left"></td>
		<tr>
		<tr>
			<td align="right"> *Password </td>
			<td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="length[4,12]"/></td>
			<td><span id="result" class="error" style="text-align:left"></span></td>
			<td id="password_error" align="left"></td>
		<tr>
		<tr>
			<td align="right">Confirm Password</td>
			<td><input type="password" id="password-check" class="easyui-validatebox" required="true" validType="equals['#password']" /></td>
			<td id="password-check_error" align="left" colspan="2"></td>
		<tr>	
		<tr>
			<td align="right"> *Name </td>
			<td><input type="text" name="name" id="username" class="easyui-validatebox" required="true"/> </td>
			<td id="username_error" align="left" colspan="2"></td>
		<tr>
		<tr>
			<td align="right"> *Email </td>
			<td><input type="text" name="email" id="email" class="easyui-validatebox" required="true" validType="email"/></td>
			<td id="email_error" align="left" colspan="2"></td>
		<tr>
		<tr>
			<td colspan="2" align="center">
				<a href="javascript:void(0)" id="btn_submit" class="easyui-linkbutton">Join</a>
			</td>
		</tr>		
	</table>	
	</form:form>
</div>