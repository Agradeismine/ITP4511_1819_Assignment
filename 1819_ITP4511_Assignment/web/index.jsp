
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
        <style>
            .restIcon {
                height: 100px;
                width: 100px;
                margin-right: 50px;
            }
            .rest_table{
                border: 1px solid black;
                height: 100px;
                padding: 10px;
                margin: 5px;
            }
            .rest_content{
                margin-right: 50px;
            }
        </style>
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
        <br/><table>
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
                    out.println("<div class='rest_table'>"
                            + "<img class='restIcon' src='upload/" + restaurant.getRestIcon() + "'/>"
                            + "<a class='rest_contecnt' style='margin-right:50px;'>ID: " + restaurant.getRestId() + "</a>"
                            + "<a class='rest_content'>Name: " + restaurant.getName() + "</a>"
                            + "<a class='rest_content'>Address: " + restaurant.getAddress() + "</a>"
                            + "<a class='rest_content'>Description: " + restaurant.getDescription() + "</a>"
                            + "<a class='rest_content'>ViewCount: " + restaurant.getViewCount() + "</a>"
                            + "<a class='rest_content' href='handleRestaurant?action=view&restId=" + restaurant.getRestId() + "'>View</a>"
                            + "</div>");
                }
            %>
        </table>
    </body>
</html>
