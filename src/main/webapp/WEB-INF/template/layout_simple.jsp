<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>Wordroid's Website</title>
    
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
    
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/easyui/themes/default/easyui.css"/>" />  
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/easyui/themes/icon.css"/>" /> 
	<script type="text/javascript" src="<c:url value="/resources/easyui/jquery.easyui.min.js"/>"></script>
    
	<link rel="stylesheet" href="<c:url value="/resources/dialog/basic.css"/>" />
	<script type="text/javascript" src="<c:url value="/resources/dialog/jquery.simplemodal.js"/>"></script>
  	
  	<link rel="stylesheet" href="<c:url value="/resources/common.css"/>" />
</head>
<body>
    <div id="simple_wrapper">
        <tiles:insertAttribute name="body" />
    </div>
</body> 
</html>