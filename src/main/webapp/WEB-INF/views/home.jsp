<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="false" %>
<!doctype html>
<html lang="utf-8">
<head>
    <meta charset="utf-8" />
	<title>Home</title>
	<link rel="stylesheet" href="<c:url value="/resources/common.css" />" />
	<script type="text/javascript" src="<c:url value="/resources/common.js" />" ></script>
</head>
<body>

<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
