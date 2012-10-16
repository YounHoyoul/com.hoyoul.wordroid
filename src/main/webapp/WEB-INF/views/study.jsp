<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title>Wordroid's Website</title>
	
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.0/themes/base/jquery-ui.css" />
  <link rel="stylesheet" href="<c:url value="/resources/study/main.css"/>" />
  <link rel="stylesheet" href="<c:url value="/resources/study/study.css"/>" />
  
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.8.2.js"></script>
  <script type="text/javascript" src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
  <script type="text/javascript" src="<c:url value="/resources/study/study.js"/>"></script>
  
</head>
<body>
  <div id="big_wrapper">
    <header id="top_header">
      <h1>Welcome to Wordroid!</h1>
    </header>
    
    <nav id="top_menu">
      <ul>
        <li>Home</li>
        <li>Tutorial</li>
        <li>Padcast</li>
      </ul>
    </nav>
    
    <section id="main_section">
      <article>
        
        <div id="option_frame">
          <input type="checkbox" id="reverse_study" name="reverse">Reverse</input>
          <input type="checkbox" id="magic7_study" name="reverse">Magic7</input>
        </div>
        
        <div id="card_frame">
          <div  class="card" id="card" toggle="0" wordid=""></div>
        </div>
        <div id="answer_frame">
          <table>
            <tr>
              <td>
                <ul class='answer' id="no_ans"><span class="title">I DO NOT KNOW<span></ul>
              </td>            
              <td>
                <ul class='answer' id="yes_ans"><span class="title">I KONW<span></ul>
              </td>
              <td>
                <ul class='answer' id="sure_ans"><span class="title">I AM SURE<span></ul>
              </td>              
            </tr>
          </table>
        </div>
        <div id="box_frame">
          <table>
            <tr>
              <td><span class='box' id="1_box">1</span></td>
              <td><span class='box' id="2_box">2</span></td> 
              <td><span class='box' id="3_box">3</span></td> 
              <td><span class='box' id="4_box">4</span></td> 
              <td><span class='box' id="5_box">5</span></td>
              <td><span class='box' id="6_box">SURE</span></td>                            
            </tr>
          </table>
        </div>
        <ul class='box' id="card_box" style="display:none;"></ul>
        <div id="modify_dialog" style="display:none;">
          <input type="hidden" id="dlg_wordid" name="wordid" value=""/>
          <table>
            <tr>
              <td>Word :</td>
              <td><input type="text" id="dlg_word" name="word" value="" size="30"/></td>
            </tr>
            <tr>
              <td>Mean :</td>
              <td><textarea id="dlg_mean" name="mean" cols="30" rows="5"></textarea></td>
            </tr>
          </table>
        </div>
      </article>    
    </section>
    
    <aside id="side_news">
      <h4>News</h4>
      Bucky has a new dog!
    </aside>
    
    <footer id="the_footer">
      Copyright Wordroid 2012
    </footer>
  </div>
</body>	
</html>