<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
$(document).ready(function(){
	
	$("#btn_addfolder").click(function(){
	});
	
	$("#btn_addwordset").click(function(){
	});
	
});

</script>

<article>
    <header>
	    <h1>${user.name}'s WordSet List</h1>
    </header>
    
    <c:if  test="${empty folderList and empty wordsetList}">
        NO ELEMENTS
    </c:if>	    
    <ul>
        <c:if  test="${!empty folderList}">
            <c:forEach items="${folderList}" var="folder">
                <li>F-${folder.name}</li>
            </c:forEach>   
        </c:if>	    
    </ul>
	<ul>
	    <c:if  test="${!empty wordsetList}">
		    <c:forEach items="${wordsetList}" var="wordset">
			     <li>W-${wordset.name}</li>
			</c:forEach>   
		</c:if>
	</ul>
	
	<button id="btn_addfolder">+Folder</button>
	<button id="btn_addwordset">+Wordset</button>
</article>
