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
        <jsp:useBean id="thisUser" class="ict.bean.UserInfo" scope="request" />
        <jsp:useBean id="type" class="String" scope="request" />

        <%
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
            if (user.getRole().equals("admin")) {   //user has login and he is admin
            } else {
                response.sendRedirect("index.jsp?haveNotLogin");    //Redirect and haven't login message display
            }

            String actionName = "";
            if (type != null) {
                actionName = type;
            }
        %>
        <h1><%= actionName%> Confirmation</h1>
        <form method="post" action="handleAccount"> 
            <div class="block" >
                <input type="hidden" name="action" value="<%= type%>" />
                <input type="hidden" name="userId" value="<%= thisUser.getUserID() %>"/>
                <label>User Id: </label> <%= thisUser.getUserID()%><br><br>
                <label>User Name: </label> <%= thisUser.getUsername() %><br><br>
                <label>User Role: </label> <%= thisUser.getRole()%><br><br>
                <label>User Sex: </label> <%= thisUser.getSex()%><br><br>
                <label>User District </label> <%= thisUser.getDistrict()%><br><br>
                <div class='center'>
                    <input type="button" onclick="location.href='handleAccount?action=maintainAccount'" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>
    </body>
</html>
