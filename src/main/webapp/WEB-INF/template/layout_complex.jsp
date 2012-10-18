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
  <link rel="stylesheet" href="<c:url value="/resources/common.css"/>" />
  <link rel="stylesheet" href="<c:url value="/resources/dialog/basic.css"/>" />
          
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.js"></script>
  <script type="text/javascript" src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
  <script type="text/javascript" src="<c:url value="/resources/dialog/jquery.simplemodal.js"/>"></script>
  
  <link rel="stylesheet" href="<c:url value="/resources/study/study.css"/>" />
  <script type="text/javascript" src="<c:url value="/resources/study/study.js"/>"></script>
  
</head>
<body>
  <div id="big_wrapper">
    <header id="top_header">
      <tiles:insertAttribute name="header" />
    </header>
    
    <nav id="top_menu">
      <tiles:insertAttribute name="menu" />
    </nav>

    <section id="main_section">
      <tiles:insertAttribute name="body" />
    </section>
    
    <aside id="side_news">
      <tiles:insertAttribute name="sidebar" />
    </aside>
    
    <footer id="the_footer">
      <tiles:insertAttribute name="footer" />
    </footer>
  </div>
</body> 
</html>