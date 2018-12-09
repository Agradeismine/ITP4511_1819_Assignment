<%-- 
    Document   : confirmAction
    Created on : Nov 15, 2018, 12:11:50 PM
    Author     : YipYi
--%>

<%@page import="ict.bean.UserInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmation Page</title>
    </head>
    <style>
        .block{
            width: 800px;
            display: block;
            margin: 5px 0;
        }
        .center{
            margin: 5px 0;
            text-align: center;
        }
        label {
            display: inline-block;
            width: 170px;
            text-align: left;
        }
        input,textarea{
            vertical-align: top;
        }
    </style>
    <body>
        <jsp:useBean id="role" class="ict.bean.AccountRole" scope="request" />
        <jsp:useBean id="type" class="String" scope="request" />

        <%
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
            if (user.getRole().equals("admin")) {   //user has login and he is restaurant owner
                out.println("<div class='userInfo'><a name='show_username'>" + user.getUsername() + "</a>, Hello.<br>");
                out.print("<form action='main' method='post'>"
                        + "<input type='hidden' name='action' value='logout'>"
                        + "<input type='submit' name='logoutButton' value='Logout'>"
                        + "</form></div>");
            } else {
                response.sendRedirect("index.jsp?haveNotLogin");    //Redirect and haven't login message display
            }

            String actionName = "";
            if (type != null) {
                actionName = type;
            }
        %>
        <h1><%= actionName%> Confirmation</h1>
        <form method="post" action="handleAccountRole"> 
            <div class="block" >
                <input type="hidden" name="action" value="<%= type%>" />
                <input type="hidden" name="name" value="<%= role.getRoleName() %>"/>
                <label>Role Name: </label> <%= role.getRoleName() %><br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>
    </body>
</html>
