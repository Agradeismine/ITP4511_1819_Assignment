<%-- 
    Document   : ViewRestaurantDetails
    Created on : Dec 4, 2018, 3:48:53 PM
    Author     : arthurking
--%>

<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restaurant Details</title>
    </head>
    <%
        RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
        int restId = Integer.parseInt(request.getAttribute("restId").toString());
        Restaurant restaurant = db.getRestaurantByRestId(restId);
        out.print("<h1>" + restaurant.getName() + "</h1>");
    %>
    <body>

    </body>
</html>
