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
	//TREE
	var beginEditText = "";
	
	$("#folderTree").tree({
		url:"folder/data",
		method:"get",
		animate:"true",
		onDblClick: function(node){  
            
			beginEditText = node.text;
			
			$(this).tree('beginEdit',node.target);
        
		},
		onContextMenu:function(e,node){
			
			e.preventDefault(); 
            $(this).tree('select',node.target);
            if(node.attributes.type == "folder"){
	            $('#mm').menu('show',{  
	                left: e.pageX,
	                top: e.pageY
	            });  
			}
            
		},
		onAfterEdit:function(node){
			
			if(node.attributes.type=="folder"){
				
				if(beginEditText != node.text){
					$.post("folder/update/"+node.id,
							{id:node.id,name:node.text,description:node.text},
							function(data){
								var pnode = $("#folderTree").tree('getParent',node.target);
								$("#folderTree").tree('reload',pnode.target);
							}
						);
				}
			}
			
		}
	});
	
	$("#menu_newfolder").click(function(e){
		e.preventDefault();

		var t = $("#folderTree");
		var node = t.tree('getSelected');

		$.post("folder/add",
			{name:"new folder",description:"new folder",parentid:node.id},
			function(data){
				$("#folderTree")
				.tree('reload',node.target)
				.tree('expand',node.target);
			}
		);
		
	});
	
	$("#menu_newcardset").click(function(e){
		e.preventDefault();	
		var t = $("#folderTree");
		var node = t.tree('getSelected');  
        t.tree('append', {  
            parent: (node?node.target:null),  
            data: [{  
                text: 'New CardSet',
                state: 'open'
            }]  
        }); 
	});
	
	$("#menu_remove").click(function(e){
		e.preventDefault();	
		var t = $("#folderTree");
		var node = t.tree('getSelected');  
		$.messager.confirm('Confirm','Are you sure you want to delete folder?',function(r){  
		    if (r){  
		        //alert('ok');
		        $.get("folder/delete/"+node.id,function(data){
		        	//alert(data.trim());
		        	var pnode = $("#folderTree").tree('getParent',node.target);
					$("#folderTree").tree('reload',pnode.target);
		        });
		    }  
		});
	});
});	
</script>

<article>
<div class="easyui-panel" title="Flash Card Set" icon="icon-help" style="width:620px;height:400px;padding:5px;background: #fafafa;">  
    <div class="easyui-layout" fit="true">  
        <div region="west" split="true" style="width:150px;">  
            <ul id="folderTree" class="easyui-tree"></ul>  
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

<div id="mm" class="easyui-menu" style="width:120px;">  
    <div id="menu_newfolder" data-options="iconCls:'icon-add'">New Folder</div> 
    <div id="menu_newcardset" data-options="iconCls:'icon-add'">New Card Set</div>
    <div id="menu_remove" data-options="iconCls:'icon-remove'">Delete</div>       
    <div class="menu-sep"></div>  
    <div onclick="expand()">Expand</div>  
    <div onclick="collapse()">Collapse</div>  
</div>  

<div id="toolbar1">  
    <a href="javascript:void(0)" id="btn_newuser" class="easyui-linkbutton" iconCls="icon-add" plain="true" ></a>  
    <a href="javascript:void(0)" id="btn_edituser" class="easyui-linkbutton" iconCls="icon-edit" plain="true" ></a>  
    <a href="javascript:void(0)" id="btn_destoryuser" class="easyui-linkbutton" iconCls="icon-remove" plain="true" ></a>
    <input type="text" id="search_name" size="10" />
    <a href="javascript:void(0)" id="btn_search" class="easyui-linkbutton" iconCls="icon-search" plain="true"></a>
</div> 

<div id="toolbar2">  
    <a href="javascript:void(0)" id="btn_newuser" class="easyui-linkbutton" iconCls="icon-add" plain="true" ></a>  
    <a href="javascript:void(0)" id="btn_edituser" class="easyui-linkbutton" iconCls="icon-edit" plain="true" ></a>  
    <a href="javascript:void(0)" id="btn_destoryuser" class="easyui-linkbutton" iconCls="icon-remove" plain="true" ></a>
    <input type="text" id="search_name" size="10" />
    <a href="javascript:void(0)" id="btn_search" class="easyui-linkbutton" iconCls="icon-search" plain="true"></a>
</div>  

</article>

