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
            
            $(".icon_folder").hide();
            $(".icon_wordset").hide();
            
            if(node.attributes.type == "folder"){
            	$(".icon_folder").show();
			}else if(node.attributes.type == "wordset"){
				$(".icon_wordset").show();
			}
            
            $('#mm').menu('show',{  
                left: e.pageX,
                top: e.pageY
            });
            
		},
		onAfterEdit:function(node){
				
			if(beginEditText != node.text){

				if(node.attributes.type == "folder"){
					$.post("folder/update/"+node.id,
							{id:node.id,name:node.text,description:node.text},
							function(data){
								var pnode = $("#folderTree").tree('getParent',node.target);
								$("#folderTree").tree('reload',pnode.target);
							}
						);
				}else if(node.attributes.type == "wordset"){
					$.post("wordset/update/"+node.id,
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
		
		$.post("wordset/add",
				{name:"new Card Set",description:"new Card Set",folderId:node.id},
				function(data){
					$("#folderTree")
					.tree('reload',node.target)
					.tree('expand',node.target);
				}
			);
	});
	
	$("#menu_remove").click(function(e){
		e.preventDefault();	
		var t = $("#folderTree");
		var node = t.tree('getSelected');  
		$.messager.confirm('Confirm','Are you sure you want to delete folder?',function(r){  
		    if (r){  
		        if(node.attributes.type == "folder"){
			        $.get("folder/delete/"+node.id,function(data){
			        	var pnode = $("#folderTree").tree('getParent',node.target);
						$("#folderTree").tree('reload',pnode.target);
			        });
		        }else if(node.attributes.type == "wordset"){
			        $.get("wordset/delete/"+node.id,function(data){
			        	var pnode = $("#folderTree").tree('getParent',node.target);
						$("#folderTree").tree('reload',pnode.target);
			        });
		        }

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
    <div id="menu_newfolder" data-options="iconCls:'icon-add'" class="icon_folder">New Folder</div> 
    <div id="menu_newcardset" data-options="iconCls:'icon-add'" class="icon_folder">New Card Set</div>
    <div id="menu_remove" data-options="iconCls:'icon-remove'" class="icon_folder icon_wordset">Delete</div>
    <div id="menu_property" data-options="iconCls:'icon-edit'" class="icon_folder icon_wordset">Property</div>
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

