<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	#flashcard_win{
		padding:20px;
	}
	.study_card{
		width:450px;
		height:140px;
		border: 2px solid #cdcdcd;
		background-color:#fffa;
		margin-bottom:30px;
		border-radius: 8px;
		padding:30px;
		text-align:center;
		font-size: 18px;
		font-weight: bold;
	}
	.study_box{
		width:110px;
		height:40px;
		display:inline-block;
		border:1px solid #cdcdcd;
		background-color:#fff;
		margin-right:25px;
		border-radius: 4px;
		padding:20px;
		text-align:center;
		color:#888888;
	}
	
	.dragging_card{
		color:#aaaaaa;
	}	
	.select_box{
		background-color:#fbec88;
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
		onClick: function(node){
			
			if($('#tt').tabs('exists',node.text)){
				$('#tt').tabs('select',node.text);
				return;
			}	
			
			if(node.attributes.type == "wordset"){
				$("#tt").tabs('add',{
					title:node.text,
					content:'<table id="dg_'+node.id+'"></table>',
					closable:true
				});
				
				$('#dg_'+node.id).datagrid({  
				    url:'word/data/'+node.id,
				    method:'get',
				    width:550,
				    height:310,
				    pagination:true,rownumbers:true,fitColumns: true,singleSelect: true,
				    columns:[[  
				        {field:'word',title:'Word',width:250},  
				        {field:'mean',title:'Mean',width:270}
				    ]]  
				});
				
				$('#dg_'+node.id).datagrid({
					toolbar: [
					{
						iconCls: 'icon-add',
						handler: function(){
							
							$('#fm_word').form('clear');
							$("#word_wordsetid").val(node.id);
							$('#dlg_word').dialog('open').dialog('setTitle','New Word');
							$(".ftitle").html("New Word");
							$("#fm_word_word").focus();
							
							wordurl = "./word/add";
						}
					},'-',
					{
						iconCls: 'icon-edit',
						handler: function(e){
							var row = $('#dg_'+node.id).datagrid('getSelected');  
					        if (row){  
					        	$('#fm_word').form('load',row); 
					        	$('#dlg_word').dialog('open').dialog('setTitle','Edit Word');
					            $(".ftitle").html("Edit Word");
					            wordurl = "./word/update/"+row.id;
					        } 
						}
					},'-',
					{
						iconCls: 'icon-remove',
						handler: function(){
							var row = $('#dg_'+node.id).datagrid('getSelected');  
					        if (row){  
					            wordurl = "./word/delete/"+row.id;
					            $.get(wordurl,function(result){
									//alert(result);
					            	$('#dg_'+node.id).datagrid('reload');
								});
					        } 
						}
					},'-',
					{
						iconCls: 'icon-search',
						handler: function(){
							
							flashcard_rows = $('#dg_'+node.id).datagrid('getData');
							flashcard_index = 0;
							
							current_flashcard_row = flashcard_rows.rows[flashcard_index];
							$("#flashcard").html(current_flashcard_row.word);
							
							$("#flashcard_win").css("display","");
							$("#flashcard_win").window({
								title:node.text,
							    collapsible:false,  
							    minimizable:false,  
							    maximizable:false,
							    width:600,
							    height:400,
							    modal:true
							}).window("open");
							
							$(".study_card").draggable({  
								proxy: "clone",
								revert:true,
								axis:"v",
								onStartDrag:function(e){
									$(this).addClass('dragging_card');
								},
								onStopDrag:function(e){
									$(this).removeClass('dragging_card');
								}
							});
							
							$(".study_box").droppable({
								accept:'.study_card',
								onDragEnter:function(e,source){
									$(this).addClass('select_box');
								},
								onDragLeave:function(e,source){
									$(this).removeClass('select_box');
								},
								onDrop:function(e,source){
									$(this).removeClass('select_box');
									flashcard_index++;
									if(flashcard_index >= flashcard_rows.rows.length){
										flashcard_index = 0;
									}
									current_flashcard_row = flashcard_rows.rows[flashcard_index];
									$("#flashcard").html(current_flashcard_row.word);
								}
							});
						}
					}]
				});
			}
		},
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
            						magic7:node.attributes.magic7?"on":"",
            						reverse:node.attributes.reverse?"on":""
            					 });
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
		$('#dlg').dialog('close');
	});
	
	
	var wordurl = "";
	$("#btn_word_save").click(function(){
		if($('#fm_word').form('validate')){
        	$.post(
        		wordurl,
             	$("#fm_word").serialize(),
             	function(result){
	        		var result = eval('('+result+')');  
	                if (result.errorMsg){  
	                    $.messager.show({  
	                        title: 'Error',  
	                        msg: result.errorMsg  
	                    });  
	                } else {
						$('#dlg_word').dialog('close');
	            		var node = $("#folderTree").tree('getSelected');
	            		$('#dg_'+node.id).datagrid('reload');
	                }
       			}
        	);
        }
	});
	
	$("#btn_word_cancel").click(function(){
		$('#dlg_word').dialog('close');
	});
	
	var flashcard_rows = null;
	var flashcard_index = 0;
	var current_flashcard_row = null;
	
	$("#flashcard_win").toggle(function(e){
		if(current_flashcard_row != null){
			$("#flashcard").html(current_flashcard_row.mean);
		}
		
	},function(e){
		if(current_flashcard_row != null){
			$("#flashcard").html(current_flashcard_row.word);
		}
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
	            <div id="tt" class="easyui-tabs" fit="true"></div>  
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
    
    <div id="dlg_word" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
	        closed="true" buttons="#dlg-word_buttons">  
	    <div class="ftitle">Word Information</div>  
	    <form id="fm_word" method="post" novalidate> 
	    	<input type="hidden" id="word_id" name="id" value="" />
	    	<input type="hidden" id="word_wordsetid" name="wordsetid" value="" />
	        <div class="fitem">  
	            <label>Word:</label>  
	            <!-- <input name="word" class="easyui-validatebox" required="true"> -->
	            <textarea id="fm_word_word" name="word" class="easyui-validatebox" cols="45" rows="2" required="true"></textarea>
	        </div>  
	        <div class="fitem">  
	            <label>Mean:</label>  
	            <!-- <input name="mean" class="easyui-validatebox" required="true">   -->
	            <textarea id="fm_word_mean" name="mean" class="easyui-validatebox" cols="45" rows="2" required="true"></textarea>
	        </div>                
	    </form>  
	</div>  
    <div id="dlg-word_buttons">  
        <a href="javascript:void(0)" id="btn_word_save" class="easyui-linkbutton" iconCls="icon-ok" >Save</a>  
        <a href="javascript:void(0)" id="btn_word_cancel" class="easyui-linkbutton" iconCls="icon-cancel">Cancel</a>  
    </div>
    
    <div id="flashcard_win" style="display:none;">
    	<div class="study_card" id="flashcard">Card</div>
    	<div class="study_box" id="no_box">NO BOX</div>
    	<div class="study_box" id="yes_box">YES BOX</div>
    	<div class="study_box" id="sure_box">SURE BOX</div>
    </div>
</article>

