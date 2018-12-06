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
            background-color: #222;
            color: white;
            margin: 1px -10px 0px -10px;
        }
        .restName{
            transform: translate(250px, -250px);
            font-family: "Comic Sans MS", cursive, sans-serif;
        }
        .like{
            height: 40px;
            width: auto;
            border-bottom: 1px solid grey;
            color: black;
            transform: translateY(-160px);
            text-align: center;
        }
        .menuTable, .commentArea{
            height: 100px;
            width: auto;
            color: black;
            border: 1px solid grey;
            transform: translateY(-100px);
            margin: 5px;
        }
        .goComment{
            transform: translate(250px, -200px);
        }
    </style>
    <jsp:include page="heading.jsp" />
    <jsp:useBean id="rBean" class="ict.bean.Restaurant" scope="request"/>
    <%
        int restId = rBean.getRestId();
        String name = rBean.getName();
        int viewCoiunt = rBean.getViewCount();
        String iconPath = "upload/"+rBean.getRestIcon();
    %>
    <div class='intro'>
        <div><img src='<%=iconPath%>' class='restImg'/></div>
        <h1 class='restName'><%=name%></h1><div/>
        <form action='handleComment' class='goComment'>
            <input type="hidden" name="action" value="comment"/>
            <input type='hidden' name='restId' value='<%=restId%>'/>
            <input type='submit' value='Go to comment'/><br/>
            <%
                if (request.getAttribute("notLogin") != null) {
                    out.println("<a style='color:white;'>" + request.getAttribute("notLogin") + "</a>");
                }
            %>
        </form>
        <div class='like'><h3>Views: <%=viewCoiunt%></h3></div>
        <div class='menuTable'>FOR MENU</div>
        <%
            for (int i = 0; i < 5; i++) {
                out.print("<div class='commentArea'>FOR COMMENT</div>");
            }
        %>
        <body>

        </body>
</html>
