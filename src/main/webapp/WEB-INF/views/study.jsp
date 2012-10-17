<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    

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
        <div id="button_frame">
            <button id="btn_addnewcard">Add New Card</button>
        </div>
        
        <ul class='box' id="card_box" style="display:none;"></ul>
        
        <!-- modal content -->
        <div id="basic-modal-content">
            <h3 id="dlg_title"></h3>
            <form name='wordform' id="wordform">
				<input type="hidden" id="dlg_wordid" name="wordid" value=""/>
				<input type="hidden" id="dlg_mode" name="mode" value=""/>
				<table>
			    <tr>
			      <td>Word :</td>
			      <td><input type="text" id="dlg_word" name="word" value="" style="width:300px;"/></td>
			    </tr>
			    <tr>
			      <td>Mean :</td>
			      <td><textarea id="dlg_mean" name="mean" cols="30" rows="5" style="width:300px;"></textarea></td>
			    </tr>
			    <tr>
	              <td colspan="2">
	                <button id="dlg_ok">OK</button>
	                <!-- <button id="dlg_cancel">Cancel</button> -->
	                <h3 id="dlg_status"></h3>
	                
	              </td>
	            </tr>
				</table>
			</form>
        </div>
             
        
      </article>    


    
