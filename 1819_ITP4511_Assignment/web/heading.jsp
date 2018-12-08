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
        body{
            font-family: "Comic Sans MS", cursive, sans-serif;
        }
        .heading {
            border: 1px solid #FCD742;
            height: 90px;
            width: auto;
            background-color: #FCD742;
            padding-left: 20px;
            margin: 0px -10px 0px -10px;
        }
        .loginForm {
            width: 500px;
            margin: 10px 100px 0px 0px;
            padding: 10px;
            float: right;
            transform: translateY(-80px);
            text-align: right;
        }
        .t {
            color: brown;
        }
        .k {
            color: red;
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
        input:hover{
            cursor: pointer;
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
                        String username = user.getUsername();
                %>
                <div class='userInfo'><a name='show_username'><%=username%></a>, Hello.<br>
                    <form action='main' method='post'>
                        <input type='hidden' name='action' value='logout'>
                        <input type='submit' name='logoutButton' value='Logout'>
                    </form>
                    <form action="handleMyFavourite">
                        <input type="hidden" name="action" value="viewMyFavourite"/>
                        <input type="submit" value="My Favourite"/>
                    </form>
                </div>
                <%
                } else {
                %>
                <form method='post' action='main'>
                    <input type='hidden' name='action' value='authenticate'>
                    <span class='userId'>User ID: <input type='text' id='userId' name='userId'></span>
                    <span class='userPw'>Password: <input type='password' id='password' name='password'></span>
                    <input type='submit' value='Sign In'>
                </form><br/>
                <%
                        if (request.getAttribute("loginError") != null) {
                            out.println("<a style='color:red;'>" + request.getAttribute("loginError") + "</a>");
                        }
                    }
                %>
            </div>
        </div>
    </body>
</html>
