<%--
  Created by IntelliJ IDEA.
  User: yonghao
  Date: 2018/5/9
  Time: 下午2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" class="no-js">

<head>

  <meta charset="utf-8">
  <title>Fullscreen Login</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- CSS -->
  <link rel='stylesheet' href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'>
  <link rel="stylesheet" href="/Back/css/reset.css">
  <link rel="stylesheet" href="/Back/css/supersized.css">
  <link rel="stylesheet" href="/Back/css/style.css">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.js"></script>
  <![endif]-->

</head>

<body>

<div class="page-container">
  <h1>Login</h1>
  <form action="/adminLogin.do" method="post">
    <input type="text" name="adminUsername" class="username" placeholder="Username">
    <input type="password" name="adminPassword" class="password" placeholder="Password">
    <button type="submit">Sign me in</button>
    <div class="error"><span>+</span></div>
  </form>

</div>


<!-- Javascript -->
<script src="/Back/js/jquery-1.8.2.min.js"></script>
<script src="/Back/js/supersized.3.2.7.min.js"></script>
<script src="/Back/js/supersized-init.js"></script>
<script src="/Back/js/scripts.js"></script>

</body>

</html>
