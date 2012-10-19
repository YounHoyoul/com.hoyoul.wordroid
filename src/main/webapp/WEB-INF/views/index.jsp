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

.demo { 
	width:200px; 
	height:300px;
	margin:0; 
	border:1px solid gray; 
	background:white; 
	overflow:auto;
	float:left;
}

</style>

<script>
$(document).ready(function(){
	/*
	var showModal = function(){
		$('#basic-modal-content').modal({
    		overlayClose:true,
    		onOpen: function (dialog) {
				dialog.overlay.fadeIn('fast', function () {
	    			dialog.data.hide();
	    			dialog.container.fadeIn('fast', function () {
						dialog.data.slideDown('fast');   
					});
	    		});
    	   	},
    	   	onClose: function (dialog) {
				dialog.data.fadeOut('fast', function () {
    		   		dialog.container.hide('fast', function () {
    			   		dialog.overlay.fadeOut('fast', function () {
							$.modal.close();
						});
					});
				});
    	   }
		});		
	}
	
	$("#btn_addfolder").click(function(){
		$("#dlg_title").html("New Folder");
		$("#dlg_mode").val("folder");
		showModal();
	});
	
	$("#btn_addwordset").click(function(){
		$("#dlg_title").html("New Wordset");
		$("#dlg_mode").val("wordset");
		showModal();
	});
	
	$('#dlg_ok').click(function(e){
		e.preventDefault();
		  
		if($("#dlg_mode").val() == "folder"){
			
			$("#dlg_status").text("wait...");
			$.post(
				"./folder/add", 
				$("#wordform").serialize(),
				function(data,textStatus){
					if(data=="OK"){
						$("#dlg_status").hide().text("Added sucessfully.").fadeIn('slow');
					}else{
						$("#dlg_status").hide().text("There is something wrong.").css("color","red").fadeIn('slow');
					}
				}
			);
			
			$(this).hide();
			
		}else if($("#dlg_mode").val() == "wordset"){
			
			$("#dlg_status").text("wait...");
			$.post(
				"./wordset/add", 
				$("#wordform").serialize(),
				function(data,textStatus){
					if(data=="OK"){
						$("#dlg_status").hide().text("Added sucessfully.").fadeIn('slow');
					}else{
						$("#dlg_status").hide().text("There is something wrong.").css("color","red").fadeIn('slow');
					}
				}
			);
			
			$(this).hide();
		}
	});

	$('#btn_addfolder,#btn_addwordset').button();
	
	$("#demo1")
	.bind("loaded.jstree", function (event, data) {
		//alert("TREE IS LOADED");
    })
	.jstree({ 
		"plugins" : [ "themes", "html_data","ui","crrm","hotkeys","contextmenu","sort"] //"cookies","dnd","search","types","hotkeys","contextmenu"
	})
	.bind("open_node.jstree", function (e, data) {
        //data.inst is the instance which triggered this event
        //data.inst.select_node("#phtml_2", true);
    });
	*/
	
	$('#tg').treegrid({ 
		animate: true,  
        collapsible: true,
        fitColumns: true,
		method: 'GET',
	    url:'./main/data',
	    idField:'id',  
	    treeField:'name', 
        onContextMenu: function(e,row){  
            e.preventDefault();  
            $(this).treegrid('select', row.id);  
            $('#mm').menu('show',{  
                left: e.pageX,  
                top: e.pageY  
            });  
        }, 
	    columns:[[  
	        {title:'Name',field:'name',width:220,editor:'text'},  
	        {title:'Size',field:'size',width:50,align:'right'},  
	        {title:'Description',field:'description',width:250,editor:'text'},  
	    ]],
	    onLoadSuccess:function(){
	    	//alert('1');
	    },
	    onLoadError:function(args){
	    	//alert(args.description);
	    }
	});
	
	var idIndex = 100;  
	
	$("#mm_append").click(function(){
	    idIndex++;  
	    var d1 = new Date();  
	    var d2 = new Date();  
	    d2.setMonth(d2.getMonth()+1);  
	    var node = $('#tg').treegrid('getSelected');  
	    $('#tg').treegrid('append',{  
	        parent: node.id,  
	        data: [{  
	            id: idIndex,  
	            name: 'New Task'+idIndex,  
	            persons: parseInt(Math.random()*10),  
	            begin: $.fn.datebox.defaults.formatter(d1),  
	            end: $.fn.datebox.defaults.formatter(d2),  
	            progress: parseInt(Math.random()*100)  
	        }]  
	    })
	});
	
	$("#mm_remove").click(function(){
	    var node = $('#tg').treegrid('getSelected');  
	    if (node){  
	        $('#tg').treegrid('remove', node.id);  
	    }
	});
	$("#mm_collapse").click(function(){
	    var node = $('#tg').treegrid('getSelected');  
	    if (node){  
	        $('#tg').treegrid('collapse', node.id);  
	    }  
	});
	$("#mm_expand").click(function(){
	    var node = $('#tg').treegrid('getSelected');  
	    if (node){  
	        $('#tg').treegrid('expand', node.id);  
	    } 
	});
	
	
	var editingId;  
	$("#btn_edit").click(function(e){
		e.preventDefault();
		if (editingId != undefined){  
            $('#tg').treegrid('select', editingId);  
            return;  
        }
        var row = $('#tg').treegrid('getSelected');  
        if (row){  
            editingId = row.id;
            $('#tg').treegrid('beginEdit', editingId);  
        }  
    });
	
	$("#btn_save").click(function(){  
        if (editingId != undefined){  
            var t = $('#tg');  
            t.treegrid('endEdit', editingId);  
            editingId = undefined;  
            var persons = 0;  
            var rows = t.treegrid('getChildren');  
            for(var i=0; i<rows.length; i++){  
                var p = parseInt(rows[i].persons);  
                if (!isNaN(p)){  
                    persons += p;  
                }  
            }  
            var frow = t.treegrid('getFooterRows')[0];  
            frow.persons = persons;  
            t.treegrid('reloadFooter');  
        }  
    });
	
	$("#btn_cancel").click(function(){  
        if (editingId != undefined){  
            $('#tg').treegrid('cancelEdit', editingId);  
            editingId = undefined;  
        }  
    });
});	
</script>

<article>
	<%--     <header>
	    <h1>${user.name}'s WordSet List</h1>
    </header> --%>
    <table id="tg" title="${user.name}'s WordSet List" style="width:620px;height:250px"></table>  
	<div id="mm" class="easyui-menu" style="width:120px;">  
        <div id="mm_append" data-options="iconCls:'icon-add'">Append</div>  
        <div id="mm_remove" data-options="iconCls:'icon-remove'">Remove</div>  
        <div class="menu-sep"></div>  
        <div id="mm_collapse">Collapse</div>  
        <div id="mm_expand">Expand</div>  
    </div> 
    <div style="margin:10px 0;">  
        <a href="javascript:void(0)" class="easyui-linkbutton" id="btn_edit">Edit</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" id="btn_save">Save</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" id="btn_cancel">Cancel</a>  
    </div> 
    
	<table id="dg" title="My Users" class="easyui-datagrid" style="width:620px;height:250px"  
            url="get_users.php"  
            toolbar="#toolbar" 
            pagination="true"  
            rownumbers="true" 
            fitColumns="true" 
            singleSelect="true">  
        <thead>  
            <tr>  
                <th field="word" width="50">Word</th>  
                <th field="mean" width="50">Mean</th>  
            </tr>  
        </thead>  
    </table>  
    
    <div id="toolbar">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New Word</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit Word</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove Word</a>  
    </div>  
      
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
            closed="true" buttons="#dlg-buttons">  
        <div class="ftitle">User Information</div>  
        <form id="fm" method="post" novalidate>  
            <div class="fitem">  
                <label>Word:</label>  
                <input name="word" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Mean:</label>  
                <input name="mean" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Phone:</label>  
                <input name="phone">  
            </div>  
            <div class="fitem">  
                <label>Email:</label>  
                <input name="email" class="easyui-validatebox" validType="email">  
            </div>  
        </form>  
    </div>  
    <div id="dlg-buttons">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">Save</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>  
    </div>  
    <script type="text/javascript">  
        var url;  
        function newUser(){  
            $('#dlg').dialog('open').dialog('setTitle','New User');  
            $('#fm').form('clear');  
            url = 'save_user.php';  
        }  
        function editUser(){  
            var row = $('#dg').datagrid('getSelected');  
            if (row){  
                $('#dlg').dialog('open').dialog('setTitle','Edit User');  
                $('#fm').form('load',row);  
                url = 'update_user.php?id='+row.id;  
            }  
        }  
        function saveUser(){  
            $('#fm').form('submit',{  
                url: url,  
                onSubmit: function(){  
                    return $(this).form('validate');  
                },  
                success: function(result){  
                    var result = eval('('+result+')');  
                    if (result.errorMsg){  
                        $.messager.show({  
                            title: 'Error',  
                            msg: result.errorMsg  
                        });  
                    } else {  
                        $('#dlg').dialog('close');      // close the dialog  
                        $('#dg').datagrid('reload');    // reload the user data  
                    }  
                }  
            });  
        }  
        function destroyUser(){  
            var row = $('#dg').datagrid('getSelected');  
            if (row){  
                $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){  
                    if (r){  
                        $.post('destroy_user.php',{id:row.id},function(result){  
                            if (result.success){  
                                $('#dg').datagrid('reload');    // reload the user data  
                            } else {  
                                $.messager.show({   // show error message  
                                    title: 'Error',  
                                    msg: result.errorMsg  
                                });  
                            }  
                        },'json');  
                    }  
                });  
            }  
        }  
    </script>  
    <style type="text/css">  
        #fm{  
            margin:0;  
            padding:10px 30px;  
        }  
        .ftitle{  
            font-size:14px;  
            font-weight:bold;  
            color:#666;  
            padding:5px 0;  
            margin-bottom:10px;  
            border-bottom:1px solid #ccc;  
        }  
        .fitem{  
            margin-bottom:5px;  
        }  
        .fitem label{  
            display:inline-block;  
            width:80px;  
        }  
    </style>  
    <!-- <div id="demo1" class="demo">
    	Folder List
		<ul>
			<li id="phtml_1">
				<a href="#">Root node 1</a>
				<ul>
					<li id="phtml_2">
						<a href="#">Child node 1</a>
						<ul>
							<li id="phtml_6">
								<a href="#">Child node 2</a>
							</li>						
							<li id="phtml_5">
								<a href="#">Child node 1</a>
							</li>						
						</ul>						
					</li>
					<li id="phtml_3">
						<a href="#">Child node 2</a>
					</li>
				</ul>
			</li>
			<li id="phtml_4">
				<a href="#">Root node 2</a>
			</li>
		</ul>
	</div>
    
    <div class="demo">
		<ul>
			<li>Wordset 1</li>		
			<li>Wordset 2</li>	
			<li>Wordset 3</li>	
			<li>Wordset 4</li>	
			<li>Wordset 5</li>	
		</ul>
    </div> -->
    
    
    <%-- <c:if  test="${empty folderList and empty wordsetList}">
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
	
    <div id="basic-modal-content">
        <h3 id="dlg_title">New Folder</h3>
        <input type="hidden" name="dlg_mode"/>
        <form name='dataform' id="dataform">
            <table>
	            <tr>
	                <td>Name:</td>
	                <td><input type="text" id="dlg_name" name="name" value="" style="width:300px;"/></td>
	            </tr>
	            <tr>
	                <td>Description:</td>
	                <td><textarea id="dlg_desc" name="description" cols="30" rows="5" style="width:300px;"></textarea></td>
	            </tr>
	            <tr>
	                <td colspan="2">
	                    <button id="dlg_ok">OK</button>
	                    <h3 id="dlg_status"></h3>               
	                </td>
	            </tr>
            </table>
        </form>
    </div>	 --%>
	
</article>

