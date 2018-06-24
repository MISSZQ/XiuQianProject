<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta http-equiv=”refresh” content=”1″>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>Xiu签后台管理中心</title>  
    <link rel="stylesheet" href="/Back/css/pintuer.css">
    <link rel="stylesheet" href="/Back/css/admin.css">
    <script src="/Back/js/jquery.js"></script>
</head>
<body style="background-color:#f2f9fd;">
<div class="header bg-main">
  <div class="logo margin-big-left fadein-top">
    <h1><img src="/Back/images/logo.png" class="radius-circle rotate-hover" height="50" alt="" />Xiu签后台管理中心</h1>
  </div>
  <div class="head-l"><a class="button button-little bg-green" href="" target="_blank"><span class="icon-home"></span> 前台首页</a> &nbsp;&nbsp;<a href="##" class="button button-little bg-blue"><span class="icon-wrench"></span> 清除缓存</a> &nbsp;&nbsp;<a class="button button-little bg-red" href="index.html"><span class="icon-power-off"></span> 退出登录</a> </div>
</div>
<div class="leftnav">
  <div class="leftnav-title"><strong><span class="icon-list"></span>菜单列表</strong></div>
  <h2><span class="icon-user"></span>用户管理</h2>
  <ul style="display:block">
    <li><a href="/Back/info.jsp" target="right"><span class="icon-caret-right"></span>网站设置</a></li>
    <li><a href="/Back/pass.jsp" target="right"><span class="icon-caret-right"></span>管理员修改密码</a></li>
    <li><a href="/User/showUsers.do" target="right"><span class="icon-caret-right"></span>用户信息</a></li>
    <%--<li><a href="/User/showUserAndActivities.do" target="right"><span class="icon-caret-right"></span>用户动态</a></li>--%>
  </ul>
  <h2><span class="icon-pencil-square-o"></span>活动管理</h2>
  <ul>
    <li><a href="/Activity/showActivitys.do" target="right"><span class="icon-caret-right"></span>活动内容管理</a></li>
    <li><a href="/Back/add.jsp" target="right"><span class="icon-caret-right"></span>添加活动</a></li>
    <li><a href="/Back/cate.jsp" target="right"><span class="icon-caret-right"></span>比赛活动惩罚表</a></li>
  </ul>
</div>
<script type="text/javascript">
$(function(){
  $(".leftnav h2").click(function(){
	  $(this).next().slideToggle(200);
	  $(this).toggleClass("on");
  })
  $(".leftnav ul li a").click(function(){
	    $("#a_leader_txt").text($(this).text());
  		$(".leftnav ul li a").removeClass("on");
		$(this).addClass("on");
  })
});
</script>
<ul class="bread">
  <li><a href="##" id="a_leader_txt">网站信息</a></li>
  <li><b>当前语言：</b><span style="color:red;">中文</php></span>
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;切换语言：<a href="##">中文</a> &nbsp;&nbsp;<a href="##">英文</a> </li>
</ul>
<div class="admin">
  <iframe scrolling="auto" rameborder="0" src="/Back/info.jsp" name="right" width="100%" height="100%"></iframe>
</div>
<div style="text-align:center;">
<p>来源:<a href="http://www.mycodes.net/" target="_blank">源码之家</a></p>
</div>
</body>
</html>