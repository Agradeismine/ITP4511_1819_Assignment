<%@page import="ict.db.MenuDB"%>
<%@page import="ict.bean.Menu"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.Restaurant"%>
<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.UserInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Restaurant Menus</title>
    </head>
    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
        <jsp:useBean id="restaurantMenu" class="ArrayList<Menu>" scope="request"/>


        <%
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
            if (user.getUsername() != null && user.getRole().equals("owner")) {   //user has login and he is owner
                out.println("<div class='userInfo'><a name='show_username'>" + user.getUsername() + "</a>, Hello.<br>");
                out.print("<form action='main' method='post'>"
                        + "<input type='hidden' name='action' value='logout'>"
                        + "<input type='submit' name='logoutButton' value='Logout'>"
                        + "</form></div>");
            } else {
                response.sendRedirect("index.jsp?haveNotLogin");    //Redirect and haven't login message display
            }
            
    int restId = Integer.parseInt(request.getParameter("restId"));
        %>
        <br/>
        <a href="handleMenu?action=addMenu&restId=<%= restId %>"><button>Add Menu</button></a>
        <br/><table border="1">
            <th>Menu ID</th><th>Menu Name</th><th>Photo</th><th>Menu Type</th><th>Start Time</th><th>End Time</th></tr>
                <%
                    for (int i = 0; i < restaurantMenu.size(); i++) {
                        Menu menu = restaurantMenu.get(i);
                        out.println("<tr style='text-align:center;'>"
                                + "<td>" + menu.getImgId() + "</td>"
                                + "<td>" + menu.getImgName() + "</td>"
                                + "<td><img src='upload/" + menu.getMenuPath() + "' width='100' height='100'></td>"
                                + "<td>" + menu.getMenuType() + "</td>"
                                + "<td>" + menu.getMenuStartTime() + "</td>"
                                + "<td>" + menu.getMenuEndTime() + "</td>"
                                + "<td><a href='handleMenu?action=editMenuIcon&imgId=" + menu.getImgId() + "'>Change Icon</a></td>"
                                + "<td><a href='handleMenu?action=getEditMenu&imgId=" + menu.getImgId() + "'>Edit</a></td>"
                                + "<td><a href='handleMenu?action=confirmDeleteMenu&imgId=" + menu.getImgId() + "'>Delete</a></td>"
                                + "</tr>");
                    }
                %>
    </table>
</body>
</html>
