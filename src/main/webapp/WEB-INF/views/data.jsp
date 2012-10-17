<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
{"items":[
<c:if  test="${not empty wordList}">
	<c:forEach items="${wordList}" var="word" varStatus="status">
	{"id":"${word.id}","bx":"${word.box}","wd":"${word.word}","mn":"${word.mean}"}<c:if test="${not status.last}">,</c:if>
	</c:forEach>
</c:if>	
]}
<%
/*
{
  "items":
  [
    {"id":"1","bx":"1","wd":"She's got her father's eyes","mn":"개 눈은 아빠랑 닮았어"},
    {"id":"2","bx":"1","wd":"I'm going to the English class!","mn":"영어학원 가려구요"},
    {"id":"3","bx":"1","wd":"My feet are killing me","mn":"다리가 너무 아파"},
    {"id":"4","bx":"1","wd":"Did you drive?","mn":"차 갖고 오셨어요?"},
    {"id":"5","bx":"1","wd":"My mp3 player is not working","mn":"내 mp3 가 고장났어"},
    {"id":"6","bx":"1","wd":"Is it your day off tomorrow?","mn":"내일 쉬는 날이예요?"},
    {"id":"7","bx":"1","wd":"My English is not so good.","mn":"제 영어가 짧아요."},
    {"id":"8","bx":"1","wd":"What did you do during the break","mn":"쉬는 시간에 뭐했어."}
  ]
}
*/
%>
