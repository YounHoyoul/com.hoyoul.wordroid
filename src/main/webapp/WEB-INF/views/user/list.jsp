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
	height: 250px;
}
</style>

<script>
$(document).ready(function(){
	$("#dg").datagrid({	
		title:"My Users",
		url: "./data",
		method: "GET",
		toolbar: "#toolbar",
		pagination: true,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,
		columns:[[
				{field:'name',title:'Name',width:100},  
				{field:'loginId',title:'Login ID',width:100},  
				{field:'email',title:'Email',width:100},
				{field:'password',title:'Password',width:100},
		        ]]
	});
	
	
});

</script>

<article>
	<table id="dg" style="width:620px;height:250px" ></table>
	 
    <div id="toolbar">  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">New User</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">Edit User</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">Remove User</a>  
    </div>  
      
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  
            closed="true" buttons="#dlg-buttons">  
        <div class="ftitle">User Information</div>  
        <form id="fm" method="post" novalidate>  
            <div class="fitem">  
                <label>Name:</label>  
                <input name="name" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Login ID:</label>  
                <input name="loginId" class="easyui-validatebox" required="true">  
            </div>  
            <div class="fitem">  
                <label>Password:</label>  
                <input name="password">  
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
            url = './user/add';  
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
</article>    