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
        body{
            font-family: "Comic Sans MS", cursive, sans-serif;
        }
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
        }
        .like{
            height: 30px;
            width: auto;
            border-bottom: 1px solid grey;
            color: black;
            text-align: center;
        }
        input:hover{
            cursor: pointer;
        }
        .commentForm{
            border: 2px solid gray;
            padding: 20px;
            width: auto;
            height: auto;
            margin-top: 20px;
        }
        .likeImg{
            height: 20px;
            width: 20px;
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
            <h1 class='restName'><%=name%></h1></div>
        <div class='like'><h3>Views: <%=viewCoiunt%></h3></div>
        <form action="" class="commentForm">
            <h1 style="color: orangered;">Comment Information</h1>
            <p><a>Comment Title:<a style="color: red;">*</a> </a><input type="text" name="title" style="width: 300px;"/></p>
            <a>Like / Dislisk:<a style="color: red;">*</a> </a>
            <img class='likeImg' src='upload/Facebook_like_thumb.png'><input type="radio" name="likeStatue" value="like" checked/>&nbsp;
            &nbsp;<img class='likeImg' src='upload/Facebook-dislike.png'><input type="radio" name="likeStatue" value="dislike"/>
            <p>Date:<a style="color: red;">*</a> <input type="date" name="date"/></p>
            <p>Contents:<br/><textarea rows="4" cols="50" name="contents" maxlength="200" placeholder="Enter text here..."></textarea></p>
            <input type="submit">
        </form>
    </body>
</html>
