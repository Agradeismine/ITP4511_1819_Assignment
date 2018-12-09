<%@page import="ict.bean.AccountRole"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="/heading.jsp" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Role</title>
        <script>
            function validate(form) {
                var checked = document.getElementById("formCheck").checked;
                var confirm = false;
                if (checked == true) {
                    confirm = confirm('Do you really want to submit the changing?');
                } else {
                    alert('Please ensure you have confirmed the information.');
                }
                return confirm;
            }
        </script>
    </head>
    <body>
        <jsp:useBean id="thisUser" class="ict.bean.UserInfo" scope="request" />
        <jsp:useBean id="roles" class="ArrayList" scope="request" />
        <form method="post" action="handleAccountRole" onsubmit="return validate(this);">
            <div class="block" style='border-style: solid;'>
                <input type="hidden" name="action" value="editUserRole" />
                <input type="hidden" name="userId" value="<%= thisUser.getUserID()%>" />
                <label>User Id: </label><%= thisUser.getUserID()%> <br><br>
                <label>User Name: </label> <%= thisUser.getUsername()%> <br><br>
                <label>Role </label> 
                <select name="roleName">
                    <option label='<%= thisUser.getRole()%>' value="<%= thisUser.getRole()%>"></option>
                    <%
                        for (int i = 0; i < roles.size(); i++) {
                            AccountRole role = (AccountRole) roles.get(i);
                            if(!role.getRoleName().equals(thisUser.getRole())){
                    %>
                    <option label='<%= role.getRoleName()%>' value="<%= role.getRoleName()%>"></option>
                    <% }}
                    %>
                </select>
                <br><br>
                I confirm the account information that I have changed. <input type="checkbox" id='formCheck'/> <br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>
    </body>
</html>
