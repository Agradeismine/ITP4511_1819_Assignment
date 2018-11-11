
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
                $("input#username").on({
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
                        + "<p>username: <input type='text' id='username' name='username'></p>"
                        + "<p>password: <input type='password' id='password' name='password'></p>"
                        + "<input type='submit' value='Sign In'>"
                        + "</form><br/>");
                if (request.getAttribute("loginError") != null) {
                    out.println("<a style='color:red;'>Login error. Please input again.</a>");
                }
            }
        %>
        <br/><input type="text" placeholder="Search..." name="search" id="search" class="search" style="width: 400px;"><br/>
        <br/><table border="1">
            <tr><th>Restaurant Name</th></tr>
            <jsp:useBean id="restaurant" class="ict.bean.Restaurant" scope="request"/>
            <%
                RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
                ArrayList<Restaurant> restaurants = db.getAllRestaurants();
                for (int i = 0; i < restaurants.size(); i++) {
                    Restaurant restaurtant = restaurants.get(i);
                    out.println("<tr><td>"+restaurtant.getName()+"</td></tr>");
                }
            %>
        </table>
    </body>
</html>
