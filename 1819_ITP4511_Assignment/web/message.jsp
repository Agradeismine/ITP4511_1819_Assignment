<%@page import="ict.bean.Restaurant"%>
<%@page import="ict.db.RestaurantDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <%
            RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
            Restaurant restaurant = db.getRestaurantByRestId(1);    //((Integer)request.getAttribute("restId")).intValue()
%>
    <center>
        <h2>${message}</h2>
        <%--<a href="upload/${fileName}" >${fileName}</a>
        <img src="upload/${fileName}"/>
        ${name}--%>
        <input type="button" onclick="history.back()" value="Back"/> <input type="button" onclick="location.href = 'index.jsp'" value="Go to Main Page"/>
    </center>
</body>
</html>