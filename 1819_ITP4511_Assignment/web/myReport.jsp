<%-- 
    Document   : myReport
    Created on : Dec 8, 2018, 9:31:05 PM
    Author     : arthurking
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <jsp:include page="heading.jsp"/>
        <%
            String numberOfVisitor = String.valueOf(request.getAttribute("numberOfVisitor"));
            String restName = String.valueOf(request.getAttribute("restName"));
        %>
        <h2>Restaurant Name: <%=restName%></h2>
        <h2>Number Of Visitor: <%=numberOfVisitor%></h2>
    </body>
</html>
