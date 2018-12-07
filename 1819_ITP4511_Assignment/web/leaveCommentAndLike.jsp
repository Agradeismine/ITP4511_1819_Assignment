<%-- 
    Document   : writeCommentAndLike
    Created on : Dec 6, 2018, 5:24:16 PM
    Author     : arthurking
--%>
<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Leave Comment</title>
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
            transform: translateY(-120px);
            text-align: center;
        }
        input:hover{
            cursor: pointer;
        }
    </style>
    <body>
        <jsp:include page="heading.jsp"/>
        <jsp:useBean id="rBean" class="ict.bean.Restaurant" scope="request"/>
        <%
            int restId = rBean.getRestId();
            String name = rBean.getName();
            int viewCoiunt = rBean.getViewCount();
            String iconPath = "upload/" + rBean.getRestIcon();
        %>
        <div class='intro'>
            <div><img src='<%=iconPath%>' class='restImg'/></div>
            <h1 class='restName'><%=name%></h1><div/>
        </div>
        <div class='like'><h3>Views: <%=viewCoiunt%></h3></div>
    </body>
</html>
