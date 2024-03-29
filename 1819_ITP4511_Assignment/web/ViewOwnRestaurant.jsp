<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.Restaurant"%>
<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.UserInfo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="/heading.jsp" />

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
            if (user.getUsername() != null && user.getRole().equals("owner")) {   //user has login and he is owner

            } else {
                response.sendRedirect("index.jsp?haveNotLogin");    //Redirect and haven't login message display
            }
        %>
        <br/><table border="1">
            <tr><th>Restaurant Icon</th><th>Restaurant ID</th><th>Restaurant Name</th><th>Restaurant Address</th><th>Restaurant Description</th><th>Tel</th><th colspan="4">Action</th></tr>
                    <%
                        RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
                        ArrayList<Restaurant> restaurants = db.getRestaurantByOwnerId(user.getUserID());     //do到here
                        for (int i = 0; i < restaurants.size(); i++) {
                            Restaurant restaurant = restaurants.get(i);

                            out.println("<tr style='text-align:center;'>"
                                    + "<td><img src='upload/" + restaurant.getRestIcon() + "' width='100' height='100'></td>"
                                    + "<td>" + restaurant.getRestId() + "</td>"
                                    + "<td>" + restaurant.getName() + "</td>"
                                    + "<td>" + restaurant.getAddress() + "</td>"
                                    + "<td>" + restaurant.getDescription() + "</td>"
                                    + "<td>" + restaurant.getTel() + "</td>"
                                    + "<td><a href='handleMenu?action=maintainRestMenu&restId=" + restaurant.getRestId() + "'>Maintain menus</a></td>"
                                    + "<td><a href='handleRestaurant?action=editRestaurantIcon&restId=" + restaurant.getRestId() + "'>Change Icon</a></td>"
                                    + "<td><a href='handleRestaurant?action=getEditRestaurant&restId=" + restaurant.getRestId() + "'>Edit</a></td>"
                                    + "<td><a href='handleRestaurant?action=confirmDeleteRestaurant&restId=" + restaurant.getRestId() + "'>Delete</a></td>"
                                    + "</tr>");
                        }
                    %>
        </table>
    </body>
</html>
