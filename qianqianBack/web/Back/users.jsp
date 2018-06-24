<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
<link rel="stylesheet" href="/Back/css/pintuer.css">
<link rel="stylesheet" href="/Back/css/admin.css">
<script src="/Back/js/jquery.js"></script>
<script src="/Back/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head"><strong class="icon-reorder"> 内容列表</strong></div>
  <div class="padding border-bottom">  
  <button type="button" class="button border-yellow" onclick="window.location.href='#add'"><span class="icon-plus-square-o"></span> 添加内容</button>
  </div>
  <table class="table table-hover text-center">
    <tr>
      <th width="5%">ID</th>
      <th width="10%">用户学号</th>
      <th width="15%">用户头像</th>
      <th width="10%">用户昵称</th>
      <th width="10%">用户邮箱</th>
      <th width="25%">个人介绍</th>
      <th width="15%">操作</th>
    </tr>

    <c:forEach var="user" items="${users}">
      <tr>
        <td>${user.userId}</td>
        <td>${user.userNum}</td>
        <td><img src="${user.userImageUrl}" alt="" width="120" height="50" /></td>
        <td>${user.userName}</td>
        <td>${user.userEmail}</td>
        <td>${user.userIntroduce}</td>
        <td><div class="button-group">
          <a class="button border-main" href="/User/showUserAndActivities.do?userId=${user.userId}"><span class="icon-edit"></span> 详情</a>
          <a class="button border-main" href="/User/showUserAndAttention.do?userId=${user.userId}"><span class="icon-edit"></span> 关注</a>
          <a class="button border-red" href="javascript:void(0)" onclick="return del(1,1)"><span class="icon-trash-o"></span> 删除</a>
        </div></td>
      </tr>
    </c:forEach>
  </table>
</div>
<script type="text/javascript">
function del(id,mid){
	if(confirm("您确定要删除吗?")){
	
	}
}
</script>
</body></html>