<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
  <li><a href="<c:url value="/main"/>" class="easyui-linkbutton" iconCls="icon-back">Home</a></li>
  <li><a href="<c:url value="/user/list"/>" class="easyui-linkbutton" iconCls="icon-back">Users</a></li>
  <li><a href="<c:url value="/main/logout"/>" class="easyui-linkbutton" iconCls="icon-cancel">Logout</a></li>
</ul>