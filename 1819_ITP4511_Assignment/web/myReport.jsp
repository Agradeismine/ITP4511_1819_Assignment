<%-- 
    Document   : myReport
    Created on : Dec 8, 2018, 9:31:05 PM
    Author     : arthurking
--%>
<%@page import="java.util.*"%>
<%@page import="ict.bean.UserInfo" %>
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
            UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
            if ("owner".equalsIgnoreCase(user.getRole())) {
                String numberOfVisitor = String.valueOf(request.getAttribute("numberOfVisitor"));
                String restName = String.valueOf(request.getAttribute("restName"));
                String avgByMonth = String.valueOf(request.getAttribute("avgByMonth"));
                ArrayList[] list = (ArrayList[]) request.getAttribute("avgByDistrict");
                ArrayList districtList = list[0];
                ArrayList countList = list[1];
                String district, count;
        %>
        <h1 style="color: red">Analytic & Report</h1>
        <h2>Restaurant Name: <%=restName%></h2>
        <h2>Number Of Visitor: <%=numberOfVisitor%></h2>
        <h2>Average number of visitors by month: <%=avgByMonth%></h2>
        <h2>Average number of visitors by district</h2>
        <table border="1">
            <tr>
                <td>District</td>
                <%
                    for (int i = 0; i < districtList.size(); i++) {
                        district = String.valueOf(districtList.get(i));
                %>
                <td><%=district%></td>
                <%
                    }
                %>
            </tr>
            <tr>
                <td>Count</td>
                <%
                    for (int i = 0; i < countList.size(); i++) {
                        count = String.valueOf(countList.get(i));
                %>
                <td><%=count%></td>
                <%
                    }
                %>
            </tr>
        </table>
        <%
        } else if ("admin".equalsIgnoreCase(user.getRole())) {
        %>
        <h2>Popular keywords</h2>
        <h2>Number of visitors of each restaurant</h2>
        <%
            }
        %>
    </body>
</html>
