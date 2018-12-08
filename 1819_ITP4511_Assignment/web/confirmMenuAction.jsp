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
        <jsp:useBean id="restaurant" class="ict.bean.Restaurant" scope="request" />
        <jsp:useBean id="restMenu" class="ict.bean.Menu" scope="request" />
        <jsp:useBean id="type" class="String" scope="request" />

        <%
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
            if (user.getRole().equals("owner") && user.getUserID()==restaurant.getOwnerId()) {   //user has login and he is restaurant owner
                out.println("<div class='userInfo'><a name='show_username'>" + user.getUsername() + "</a>, Hello.<br>");
                out.print("<form action='main' method='post'>"
                        + "<input type='hidden' name='action' value='logout'>"
                        + "<input type='submit' name='logoutButton' value='Logout'>"
                        + "</form></div>");
            } else {
                response.sendRedirect("index.jsp?haveNotLogin");    //Redirect and haven't login message display
            }

            String actionName = "";
            String menuStartTime = "";
            String menuEndTime = "";
            if (type != null) {
                actionName = type;
            }
            if(restMenu.getMenuType().equalsIgnoreCase("General")){     //without startDate & endDate
                menuStartTime = "All day";
                menuEndTime = "All day";
            }else{
                menuStartTime = String.valueOf(restMenu.getMenuStartTime());
                menuEndTime = String.valueOf(restMenu.getMenuEndTime());
            }
        %>
        <h1><%= actionName%> Confirmation</h1>
        <form method="post" action="handleMenuEdit"> 
            <div class="block" >
                <input type="hidden" name="action" value="<%= type%>" />
                <input type="hidden" name="restId" value="<%= restMenu.getRestId()%>" />
                <input type="hidden" name="imgId" value="<%= restMenu.getImgId()%>"/>
                <input type="hidden" name="imgName" value="<%= restMenu.getImgName() %>" />
                <input type="hidden" name="menuType" value="<%= restMenu.getMenuType()%>" />
                <input type="hidden" name="menuPath" value="<%= restMenu.getMenuPath()%>" />
                <input type="hidden" name="menuStartTime" value="<%= restMenu.getMenuStartTime()%>" />
                <input type="hidden" name="menuEndTime" value="<%= restMenu.getMenuEndTime() %>" />
                <img src='upload/menu/<%=restMenu.getMenuPath()%>' width='100' height='100'><br><br>
                <label>Restaurant Name: </label> <%= restaurant.getName()%><br><br>
                <label>Menu ID: </label> <%= restMenu.getImgId() %><br><br>
                <label>Menu Name: </label> <%= restMenu.getImgName()%><br><br>
                <label>Menu Type: </label> <%= restMenu.getMenuType()%><br><br>
                <label>Menu Start Date: </label> <%= menuStartTime %><br><br>
                <label>Menu End Date: </label> <%= menuEndTime %><br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>
    </body>
</html>
