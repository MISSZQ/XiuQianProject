<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta http-equiv=”refresh” content=”2″>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>动态界面</title>  
    <link rel="stylesheet" href="/Back/css/pintuer.css">
    <link rel="stylesheet" href="/Back/css/admin.css">
    <script src="/Back/js/jquery.js"></script>
    <script src="/Back/js/pintuer.js"></script>
</head>
<body>
<div class="panel admin-panel">
  <div class="panel-head"><strong class="icon-reorder"> 签到列表</strong></div>
  <div class="padding border-bottom">  
  <a class="button border-yellow" href=""><span class="icon-plus-square-o"></span> 添加内容</a>
  </div> 
  <table class="table table-hover text-center">
      <tr>
          <th width="5%">ActionId</th>
          <th width="8%">DiscussId</th>
          <th width="8%">评论内容</th>
          <th width="10%">评论图片</th>
          <th width="10%">评论点赞数</th>
          <th width="15">发表时间</th>
          <th width="15">相关操作</th>
      </tr>
      <c:forEach var="discuss" items="${discusses}">
          <tr>
              <td>${discuss.actionId.actionId}</td>
              <td>${discuss.discussId}</td>
              <td>${discuss.discussIntroduce}</td>
              <td width="10%"><img src="${discuss.discussImageUrl}" alt="" width="70" height="50" /></td>
              <td>${discuss.discussPraise}</td>
              <td>${discuss.discussTime}</td>
              <td>
                  <div class="button-group">
                      <a type="button" class="button border-main" href="#"><span class="icon-edit"></span>详情</a>
                      <a class="button border-red" href="javascript:void(0)" onclick="return del(17)"><span
                              class="icon-trash-o"></span> 删除</a>
                  </div>
              </td>
          </tr>
      </c:forEach>
  </table>
</div>

<%--<div class="panel admin-panel">--%>
  <%--<div class="panel-head"><strong class="icon-reorder"> 动态列表</strong></div>--%>
  <%--<div class="padding border-bottom">  --%>
  <%--<a class="button border-yellow" href=""><span class="icon-plus-square-o"></span> 添加内容</a>--%>
  <%--</div> --%>
  <%--<table class="table table-hover text-center">--%>

          <%--<tr>--%>
              <%--<th width="5%">用户ID</th>--%>
              <%--<th width="8%">活动ID</th>--%>
              <%--<th width="8%">活动标题</th>--%>
              <%--<th width="8">活动介绍</th>--%>
              <%--<th width="10%">活动图片</th>--%>
              <%--<th width="10%">发布时间</th>--%>
              <%--<th width="10">截止时间</th>--%>
              <%--<th width="15%">操作</th>--%>
          <%--</tr>--%>

      <%--<c:forEach items="${activities}" var="activity">--%>
          <%--<tr>--%>
              <%--<td>${userId}</td>--%>
              <%--<td>${activity.activityId}</td>--%>
              <%--<td>${activity.activityTitle}</td>--%>
              <%--<td><img alt="图片" src=""></td>--%>
              <%--<td>50</td>--%>
              <%--<td>2018-1-1</td>--%>
              <%--<td>--%>
                  <%--<div class="button-group">--%>
                      <%--<a type="button" class="button border-main" href="#"><span class="icon-edit"></span>修改</a>--%>
                      <%--<a class="button border-red" href="javascript:void(0)" onclick="return del(17)"><span--%>
                              <%--class="icon-trash-o"></span> 删除</a>--%>
                  <%--</div>--%>
              <%--</td>--%>
          <%--</tr>--%>
      <%--</c:forEach>--%>

  </table>
</div>
<script>
function del(id){
	if(confirm("您确定要删除吗?")){
		
	}
}
</script>

</body></html>