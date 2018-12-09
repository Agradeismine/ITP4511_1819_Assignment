<%@page import="ict.bean.AccountRole"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="/heading.jsp" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Maintain Account Role</title>
    </head>
    <body>
        <jsp:useBean id="roles" class="ArrayList" scope="request" />
        <input type="button" onclick="history.back()" value="Back"/>
        <a href="handleAccountRole?action=addNewRole">Add user role</a><br>
        
        <table border='1' style="width:100%;">
            <tr>
                <th>Role Name</th>
                <th colspan="2">Action</th>
            </tr>
            <% for (int i = 0; i < roles.size(); i++) {
                    AccountRole role = (AccountRole) roles.get(i);
            %>
            <tr>
                <td><h2 style="color: orangered"><%= role.getRoleName()%></h2></td>
                <td><a href='handleAccountRole?action=edit&roleName=<%= role.getRoleName()%>'>Edit</a></td>
                <td><a href='handleAccountRole?action=confirmDeleteRole&roleName=<%= role.getRoleName()%>'>Delete</a></td>
            </tr>
            <% }%>
        </table>

    </body>
</html>
