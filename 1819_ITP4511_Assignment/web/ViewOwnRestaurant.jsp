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
        <%
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
            if (user.getUsername() != null) {   //user has login
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
            <tr><th>Restaurant Icon</th><th>Restaurant ID</th><th>Restaurant Name</th><th>Restaurant Address</th><th>Restaurant Description</th><th colspan="3">Action</th></tr>
                    <%
                        RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
                        ArrayList<Restaurant> restaurants = db.getRestaurantByOwnerId(user.getUserID());     //do到here
                        for (int i = 0; i < restaurants.size(); i++) {
                            Restaurant restaurant = restaurants.get(i);
                            out.println("<tr>"
                                    + "<td align='center'><img src='upload/" + restaurant.getRestIcon() + "' width='100' height='100'></td>"
                                    + "<td>" + restaurant.getRestId() + "</td>"
                                    + "<td>" + restaurant.getName() + "</td>"
                                    + "<td>" + restaurant.getAddress() + "</td>"
                                    + "<td>" + restaurant.getDescription() + "</td>"
                                    + "<td><a href='handleRestaurant?action=editRestaurantIcon&restId=" + restaurant.getRestId() + "'>Change Icon</a></td>"
                                    + "<td><a href='handleRestaurant?action=getEditRestaurant&restId=" + restaurant.getRestId() + "'>Edit</a></td>"
                                    + "<td><a href='handleRestaurant?action=confirmDeleteRestaurant&restId=" + restaurant.getRestId() + "'>Delete</a></td>"
                                    + "</tr>");
                        }
                    %>
        </table>
    </body>
</html>
