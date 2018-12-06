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
    <style>
        .restImg{
            height: 200px;
            width: 200px;
            margin: 20px;
        }
        .intro{
            height: 200px;
            width: auto;
            background-color: black;
            color: white;
            margin-top: 5px;
        }
        .restName{
            transform: translate(250px, -200px);
        }
        .like{
            height: 40px;
            width: auto;
            border-left: 2px solid black;
            border-bottom: 2px solid black;
            border-right: 2px solid black;
            color: black;
            transform: translateY(-125px);
            text-align: center;
        }
        .menuTable{
            height: 100px;
            width: auto;
            color: black;
            border: 1px solid grey;
            transform: translateY(-100px);
        }
    </style>
    <jsp:include page="heading.jsp" />
    <jsp:useBean id="rBean" class="ict.bean.Restaurant" scope="request"/>
    <%
        RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
        int restId = Integer.parseInt(request.getAttribute("restId").toString());
        out.print("<div class='intro'><div><img src='upload/" + rBean.getRestIcon() + "' class='restImg'/></div>");
        out.print("<h1 class='restName'>" + rBean.getName() + "</h1><div/>");
        out.print("<div class='like'><h3>Views:"+rBean.getViewCount()+"</h3></div>");
        out.print("<div class='menuTable'>FOR MENU</div>");
    %>
    <body>

    </body>
</html>
