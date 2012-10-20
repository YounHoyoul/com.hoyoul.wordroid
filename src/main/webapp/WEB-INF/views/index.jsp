<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<style>
article{
	background: #FFFBCC;
	border: 1px solid red;
	padding: 20px;
	margin-bottom: 15px;
	height: 400px;
}
</style>

<script>
$(document).ready(function(){

});	
</script>

<article>
<div class="easyui-panel" title="Flash Card Set" icon="icon-help" style="width:620px;height:400px;padding:5px;background: #fafafa;">  
    <div class="easyui-layout" fit="true">  
        <div region="west" split="true" style="width:150px;">  
            <ul class="easyui-tree">  
                <li>  
                    <span>Root</span>  
                    <ul>  
                        <li><span>Voca2001</span></li>  
                        <li><span>Master2002</span></li>  
                        <li><span>수능영단어3000</span></li>  
                    </ul>  
                </li>  
            </ul>  
        </div>  
        <div region="center" border="false" border="false">  
            <div class="easyui-tabs" fit="true">  
                <div title="Voca2001">  
                    <table id="tt" class="easyui-datagrid" style="width:550px;height:310px"  
					        url="datagrid2_getdata.php"  
					        iconCls="icon-save" toolbar="#toolbar1"
					        rownumbers="true" pagination="true">  
					    <thead>  
					        <tr>  
					            <th field="word" width="250">Word</th>  
					            <th field="mean" width="270">Mean</th>  
					        </tr>  
					    </thead>
					    <tbody>
					    	<tr>
					    		<td>I am going to the English class.</td>
					    		<td>영어수업 가요.</td>
					    	</tr>
					    	<tr>
					    		<td>My English is not so good.</td>
					    		<td>제 영어가 짧아요.</td>
					    	</tr>
					    	<tr>
					    		<td>Did you drive?</td>
					    		<td>차 가져오셨나요?</td>
					    	</tr>
					    </tbody>
					</table> 
                </div>  
                <div title="Master2002">  
                    <table id="tt" class="easyui-datagrid" style="width:550px;height:310px"  
					        url="datagrid2_getdata.php"  
					        iconCls="icon-save"  toolbar="#toolbar2"
					        rownumbers="true" pagination="true">  
					    <thead>  
					        <tr>  
					            <th field="word" width="250">Word</th>  
					            <th field="mean" width="270">Mean</th>  
					        </tr>  
					    </thead>  
					</table>
                </div>  
            </div>  
        </div>   
    </div>  
</div> 

<div id="toolbar1">  
    <a href="javascript:void(0)" id="btn_newuser" class="easyui-linkbutton" iconCls="icon-add" plain="true" >New Card</a>  
    <a href="javascript:void(0)" id="btn_edituser" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >Edit Card</a>  
    <a href="javascript:void(0)" id="btn_destoryuser" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >Remove Card</a>
</div> 

<div id="toolbar2">  
    <a href="javascript:void(0)" id="btn_newuser" class="easyui-linkbutton" iconCls="icon-add" plain="true" >New Card</a>  
    <a href="javascript:void(0)" id="btn_edituser" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >Edit Card</a>  
    <a href="javascript:void(0)" id="btn_destoryuser" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >Remove Card</a>
</div>  

</article>

