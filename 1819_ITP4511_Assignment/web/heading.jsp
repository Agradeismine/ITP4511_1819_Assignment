<%-- 
    Document   : heading
    Created on : Dec 5, 2018, 4:27:45 PM
    Author     : arthurking
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Takeaway King</title>
    </head>
    <style>
        .heading {
            border: 1px solid yellow;
            height: 90px;
            width: auto;
            background-color: yellow;
            padding-left: 20px;
            margin: 0px -10px 0px -10px;
        }
        .loginForm {
            width: 500px;
            margin-top: 10px;
            padding: 10px;
            float: right;
            transform: translateY(-60px);
            text-align: right;
        }
        .t {
            color: brown;
            font-family: "Comic Sans MS", cursive, sans-serif;
        }
        .k {
            color: red;
            font-family: "Comic Sans MS", cursive, sans-serif;
        }
        .tkbg {
            background-color: white;
            width: 250px;;
            height: 50px;
            text-align: center;
            border-radius: 50px;
        }
        .tkbg:hover{
            cursor: pointer;
        }
        .userId, .userPw {
            margin-left: 10px;
            margin-right: 10px;
        }
    </style>
    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
        <div class="heading">
            <div class="tkbg" onclick="location.href = 'index.jsp';">
                <h1><a class="t">Takeaway </a><a class="k">King</a></h1>
            </div>
            <div class='loginForm'>
                <%
                    UserInfo user = (UserInfo) session.getAttribute("userInfo");
                    if (user.getUsername() != null) {
                        out.println("<div class='userInfo'><a name='show_username'>" + user.getUsername() + "</a>, Hello.<br>");
                        out.print("<form action='main' method='post'>"
                                + "<input type='hidden' name='action' value='logout'>"
                                + "<input type='submit' name='logoutButton' value='Logout'>"
                                + "</form></div>");
                    } else {
                        out.println("<form method='post' action='main'>"
                                + "<input type='hidden' name='action' value='authenticate'>"
                                //+ "<p>username: <input type='text' id='username' name='username'></p>"
                                + "<span class='userId'>User ID: <input type='text' id='userId' name='userId'></span>"
                                + "<span class='userPw'>Password: <input type='password' id='password' name='password'></span>"
                                + "<input type='submit' value='Sign In'>"
                                + "</form><br/>");
                        if (request.getAttribute("loginError") != null) {
                            out.println("<a style='color:red;'>" + request.getAttribute("loginError") + "</a>");
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>
