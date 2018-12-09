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
            <% for (int i = 0; i < users.size(); i++) {
                    UserInfo user = (UserInfo) users.get(i);
            %>
            <tr>
                <td><h2 style="color: orangered"><%=user.getUserID()%></h2></td>
                <td><a style="color: gray;">Name: <%=user.getUsername()%></a><br/></td>
                <td><a style="color: green;">Password: <%=user.getPassword()%></a></td>
                <td><a style="color: red;">Role: <%= user.getRole()%></a><br/></td>
                <td><a>Sex: <%= user.getSex()%></a><br/></td>
                <td><a>District: <%= user.getDistrict()%></a><br/></td>
                <td><a href='handleAccount?action=edit&userId=<%= user.getUserID()%>'>Edit</a></td>
                <td><a href='handleAccount?action=delete&userId=<%= user.getUserID()%>'>Delete</a></td>
            </tr>
            <% }%>
        </table>
    </body>
</html>
