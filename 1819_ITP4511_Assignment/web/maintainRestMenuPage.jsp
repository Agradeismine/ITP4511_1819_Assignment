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
        <title>View My Restaurants</title>
    </head>
    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
        <jsp:useBean id="thisRestaurant" class="ict.bean.Restaurant" scope="request"/>

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
        %>
        <br/><table border="1">
            <tr><th>Restaurant Icon</th><th>Restaurant ID</th><th>Restaurant Name</th><th>Menu ID</th><th>Menu Name</th><th>Menu Type</th></tr>
                    <%
                        MenuDB db = new MenuDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
                        ArrayList<Menu> menus = db.getRestaurantMenuById(thisRestaurant.getRestId());    //change to menu
                        for (int i = 0; i < menus.size(); i++) {
                            Menu menu = menus.get(i);
//                            out.println("<tr>"
//                                    + "<td align='center'><img src='upload/" + restaurant.getRestIcon() + "' width='100' height='100'></td>"
//                                    + "<td>" + restaurant.getRestId() + "</td>"
//                                    + "<td>" + restaurant.getName() + "</td>"
//                                    + "<td>" + restaurant.getAddress() + "</td>"
//                                    + "<td>" + restaurant.getDescription() + "</td>"
//                                    + "<td><a href='handleRestaurant?action=editRestaurantIcon&restId=" + restaurant.getRestId() + "'>Change Icon</a></td>"
//                                    + "<td><a href='handleRestaurant?action=getEditRestaurant&restId=" + restaurant.getRestId() + "'>Edit</a></td>"
//                                    + "<td><a href='handleRestaurant?action=confirmDeleteRestaurant&restId=" + restaurant.getRestId() + "'>Delete</a></td>"
//                                    + "</tr>");
                        }
                    %>
        </table>
    </body>
</html>
