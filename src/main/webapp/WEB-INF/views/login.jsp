<%--
  Created by IntelliJ IDEA.
  User: Stella
  Date: 20.09.2019
  Time: 7:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>${errorMsg}</div>
<form action="/internetshop_war_exploded/login" method="post">
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this form to sign in into account.</p>
        <hr>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter Login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>

        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>
        <button type="submit" class="registerbtn">Sign in</button>
    </div>

    <div class="container signin">
        <p>Don't have an account?<a href='/internetshop_war_exploded/registration'>Sign up</a></p>
    </div>
</form>
</body>
</html>
