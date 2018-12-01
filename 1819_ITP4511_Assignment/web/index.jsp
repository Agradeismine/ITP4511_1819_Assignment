
<%@page import="java.util.ArrayList"%>
<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="jslib/jquery-3.3.1.js"></script>
        <script>
            $(document).ready(function () {
                $("input#userId").on({
                    keydown: function (e) {
                        if (e.which === 32)
                            return false;
                    },
                    change: function () {
                        this.value = this.value.replace(/\s/g, "");
                    }
                });
                $("input#password").on({
                    keydown: function (e) {
                        if (e.which === 32)
                            return false;
                    },
                    change: function () {
                        this.value = this.value.replace(/\s/g, "");
                    }
                });
            });
        </script>
    </head>

    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
        <h1>Takeaway King</h1>
        <%
            UserInfo user = (UserInfo) session.getAttribute("userInfo");
            if (user.getUsername() != null) {
                out.println("<div class='userInfo'><a name='show_username'>" + user.getUsername() + "</a>, Hello.<br>");
                out.print("<form action='main' method='post'>"
                        + "<input type='hidden' name='action' value='logout'>"
                        + "<input type='submit' name='logoutButton' value='Logout'>"
                        + "</form></div>");
            } else {
                out.println("<form method='post' action='main'>"
                        + "<input type='hidden' name='action' value='authenticate'>"
                        //+ "<p>username: <input type='text' id='username' name='username'></p>"
                        + "<p>User ID: <input type='text' id='userId' name='userId'></p>"
                        + "<p>Password: <input type='password' id='password' name='password'></p>"
                        + "<input type='submit' value='Sign In'>"
                        + "</form><br/>");
                if (request.getAttribute("loginError") != null) {
                    out.println("<a style='color:red;'>Login error. Please input again.</a>");
                }
            }
        %>
        <br><a href="ViewOwnRestaurant.jsp">View Own Restaurant (default restaurant owner can see this function only, set it later)</a><br><br>
        <form action="handleRestaurant">
            <input type="hidden" name="action" value="search">
            <input type="text" placeholder="Search..." name="search" style="width: 400px;">
            <input type="submit">
        </form>
        <br/><table border="1">
            <tr><th>Restaurant Icon</th><th>Restaurant Name</th><th>Restaurant Address</th><th>Restaurant Description</th></tr>
                    <%
                        RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
                        ArrayList<Restaurant> restaurants;
                        if ((ArrayList) request.getAttribute("restaurants") == null) {
                            restaurants = db.getAllRestaurants();
                        } else {
                            restaurants = (ArrayList) request.getAttribute("restaurants");
                        }
                        for (int i = 0; i < restaurants.size(); i++) {
                            Restaurant restaurant = restaurants.get(i);
                            out.println("<tr>"
                                    + "<td>" + restaurant.getRestIcon() + "</td>"
                                    + "<td>" + restaurant.getName() + "</td>"
                                    + "<td>" + restaurant.getAddress() + "</td>"
                                    + "<td>" + restaurant.getDescription() + "</td>"
                                    + "</tr>");
                        }
                    %>
        </table>
    </body>
</html>
