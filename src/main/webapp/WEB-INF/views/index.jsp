<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
				save(node);
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
	
	var selected_node = null;
	
	$("#menu_property").click(function(e){
		e.preventDefault();
		var t = $("#folderTree");
		var node = t.tree('getSelected');   
        if (node){
        	
        	selected_node = node;
        	
        	if(node.attributes.type=="folder"){
        		$('#dlg').dialog('open').dialog('setTitle','Edit Folder'); 
        		$(".ftitle").html("Folder Information");
        		$("#magic7").hide();
        		$("#reverse").hide();
        	}else if(node.attributes.type=="wordset"){
        		$('#dlg').dialog('open').dialog('setTitle','Edit CardSet');  
        		$(".ftitle").html("CardSet Information");
        		$("#magic7").show();
        		$("#reverse").show();
        	}
        	
            $('#fm').form('load',{	id:node.id,
            						name:node.text,
            						description:node.attributes.description,
            						magic7:node.attributes.magic7,
            						reverse:node.attributes.reverse
            					 });  
            //url = "./update/"+node.id;
        } 
	});
	
	$("#btn_save").click(function(){
		var node = selected_node;
		
		if(node.attributes.type != null || node.attributes.type != ""){
			var url = "";

			if(node.attributes.type == "folder"){
				url = "folder/update/" + node.id;
			}else{
				url = "wordset/update/" + node.id;
			}
			
	        if($('#fm').form('validate')){
	        	$.post(
	        		url,
	             	$("#fm").serialize(),
	             	function(result){
		        		var result = eval('('+result+')');  
		                if (result.errorMsg){  
		                    $.messager.show({  
		                        title: 'Error',  
		                        msg: result.errorMsg  
		                    });  
		                } else {  
		                	var pnode = $("#folderTree").tree('getParent',node.target);
							$("#folderTree").tree('reload',pnode.target);
		                }
	       			}
	        	);
	        }
		}

		$('#dlg').dialog('close');
	});
	
	function save(node){
		if(node.attributes.type == "folder"){
			$.post("folder/update/"+node.id,
					{id:node.id,
					 name:node.text,
					 description:node.attributes.description},
					function(data){
						var pnode = $("#folderTree").tree('getParent',node.target);
						$("#folderTree").tree('reload',pnode.target);
					}
				);
		}else if(node.attributes.type == "wordset"){
			$.post("wordset/update/"+node.id,
					{id:node.id,
				     name:node.text,
				     description:node.attributes.description,
				     magic7:node.attributes.magic7,
				     reverse:node.attributes.reverse
					},
					function(data){
						var pnode = $("#folderTree").tree('getParent',node.target);
						$("#folderTree").tree('reload',pnode.target);
					}
				);
		}
	}
	
	$("#btn_cancel").click(function(){
		$('#dlg').dialog('close')
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
	
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
	        closed="true" buttons="#dlg-buttons">  
	    <div class="ftitle">User Information</div>  
	    <form id="fm" method="post" novalidate> 
	    	<input type="hidden" name="id" value="" />
	        <div class="fitem">  
	            <label>Name:</label>  
	            <input name="name" class="easyui-validatebox" required="true">  
	        </div>  
	        <div class="fitem">  
	            <label>Description:</label>  
	            <input name="description" class="easyui-validatebox">  
	        </div> 
	        <div class="fitem" id="magic7">  
	            <label>Magic7:</label>  
	            <input type="checkbox" name="magic7" class="easyui-validatebox">  
	        </div> 
	        <div class="fitem" id="reverse">  
	            <label>Reverse:</label>  
	            <input type="checkbox" name="reverse" class="easyui-validatebox">  
	        </div>                 
	    </form>  
	</div>  
    <div id="dlg-buttons">  
        <a href="javascript:void(0)" id="btn_save" class="easyui-linkbutton" iconCls="icon-ok" >Save</a>  
        <a href="javascript:void(0)" id="btn_cancel" class="easyui-linkbutton" iconCls="icon-cancel">Cancel</a>  
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

