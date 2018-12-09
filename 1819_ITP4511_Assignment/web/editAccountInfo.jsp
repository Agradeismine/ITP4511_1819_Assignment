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
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>

        <form method="post" action="handleAccountRole" onsubmit="return validate(this);">
            <div class="block" style='border-style: solid;'>
                <% if(userInfo.getRole().equalsIgnoreCase("admin")) { %>
                <input type="hidden" name="action" value="editUserRole" />
                <% }else{ %>
                <input type="hidden" name="action" value="editUserInfo" />
                <% } %>
                <input type="hidden" name="userId" value="<%= thisUser.getUserID()%>" />
                <label>User Id: </label><%= thisUser.getUserID()%> <br><br>
                <label>User Name: </label> <%= thisUser.getUsername()%> <br><br>
              <%  if(userInfo.getRole().equalsIgnoreCase("admin")){ %>
                <label>Role </label> 
                <select name="roleName">
                    <option label='<%= thisUser.getRole()%>' value="<%= thisUser.getRole()%>"></option>
                    <%
                        for (int i = 0; i < roles.size(); i++) {
                            AccountRole role = (AccountRole) roles.get(i);
                            if (!role.getRoleName().equals(thisUser.getRole())) {
                    %>
                    <option label='<%= role.getRoleName()%>' value="<%= role.getRoleName()%>"></option>
                    <%}
                        }%>
                </select><br><br>
                <% } %>
                <%if (userInfo.getUserID() == thisUser.getUserID()) { %>
                <label>Sex: </label>
                <select name="sex" >
                    <%
                        if (thisUser.getSex().equalsIgnoreCase("m")) {
                    %>
                    <option label='Man' value="M"></option>
                    <option label='Female' value="F"></option>
                    <% } else if (thisUser.getSex().equalsIgnoreCase("f")) { %>
                    <option label='Female' value="F"></option>
                    <option label='Man' value="M"></option>
                    <%}%>
                </select> <br><br>
                <label>District: </label>
                <select name="district">
                    <option label='<%= thisUser.getDistrict() %>' value="<%= thisUser.getDistrict() %>"></option>
                    <option label='Central and Western' value="Central and Western"></option>
                    <option label='Wan Chai' value="Wan Chai"></option>
                    <option label='Eastern' value="Eastern"></option>
                    <option label='Southern' value="Southern"></option>
                    <option label='Yau Tsim Mong' value="Yau Tsim Mong"></option>
                    <option label='Sham Shui Po' value="Sham Shui Po"></option>
                    <option label='Kowloon City' value="Kowloon City"></option>
                    <option label='Wong Tai Sin' value="Wong Tai Sin"></option>
                    <option label='Kwun Tong' value="Kwun Tong"></option>
                    <option label='Kwai Tsing' value="Kwai Tsing"></option>
                    <option label='Tsuen Wan' value="Tsuen Wan"></option>
                    <option label='Tuen Mun' value="Tuen Mun"></option>
                    <option label='Yuen Long' value="Yuen Long"></option>
                    <option label='North' value="North"></option>
                    <option label='Tai Po' value="Tai Po"></option>
                    <option label='Sha Tin' value="Sha Tin"></option>
                    <option label='Sai Kung' value="Sai Kung"></option>
                    <option label='Islands' value="Islands"></option>
                </select><br><br><br>
                    <label>New Password:</label><input type="password" name="password1">    (Only input when you want to change.)<br><br>
                    <label>Input New Password Again:</label><input type="password" name="password2">    (Only input when you want to change.)<br><br>
                <%

                    }%>
                <br><br>
                I confirm the account information that I have changed. <input type="checkbox" id='formCheck'/> <br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>
    </body>
</html>
