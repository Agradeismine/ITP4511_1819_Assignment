
<%@page import="ict.bean.UserInfo"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
        <h1>Takeaway King</h1>

        <%
            UserInfo user = (UserInfo)session.getAttribute("userInfo");
            if(user.getUsername()!=null){
                out.println("<div class='userInfo'><a name='show_username'>"+ user.getUsername() +"</a>, Hello.<br>");
                out.print("<form action='main' method='post'>"
                            +"<input type='hidden' name='action' value='logout'>"
                            +"<input type='submit' name='logoutButton' value='Logout'>"        
                        +"</form></div>");
            }else{
                out.println("<form method='post' action='main'>"
                                +"<input type='hidden' name='action' value='authenticate'>"
                                +"username: <input type='text' name='username'><br/>"
                                +"password: <input type='text' name='password'><br/>"
                                +"<input type='submit' value='Sign In'>"
                            +"</form><br/>");
            }
        %>
    </body>
</html>
