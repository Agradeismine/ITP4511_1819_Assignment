<%@page import="ict.bean.UserInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/heading.jsp" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Maintain Account Page</title>
    </head>
    <body>
        <jsp:useBean id="users" class="ArrayList" scope="request" />
        <a href="handleAccount?action=maintainAccRole">Maintain User role</a>
        <table border='1' style="width:100%;">
            <% 
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
                for (int i = 0; i < users.size(); i++) {
                    UserInfo thisUser = (UserInfo) users.get(i);
                    
            %>
            <tr>
                <td><h2 style="color: orangered"><%=thisUser.getUserID()%></h2></td>
                <td><a style="color: gray;">Name: <%=thisUser.getUsername()%></a><br/></td>
                <td><a style="color: green;">Password: <%=thisUser.getPassword()%></a></td>
                <td><a style="color: red;">Role: <%= thisUser.getRole()%></a><br/></td>
                <td><a>Sex: <%= thisUser.getSex()%></a><br/></td>
                <td><a>District: <%= thisUser.getDistrict()%></a><br/></td>
                <td><a href='handleAccount?action=getEditAccount&userId=<%= thisUser.getUserID()%>'>Edit</a></td>
                <td><a href='handleAccount?action=deleteAccountConfirmation&userId=<%= thisUser.getUserID()%>'>Delete<% if (thisUser.getUserID() ==user.getUserID()) {out.println("<br>(This is Your Account)");} %></a></td>
            </tr>
            <% }
                %>
        </table>
    </body>
</html>
