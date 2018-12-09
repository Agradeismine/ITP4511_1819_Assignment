<%-- 
    Document   : ViewRestaurantDetails
    Created on : Dec 4, 2018, 3:48:53 PM
    Author     : arthurking
--%>

<%@page import="ict.db.RestaurantDB"%>
<%@page import="ict.bean.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Restaurant Details</title>
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
        .menuTable, .commentArea{
            height: auto;
            width: auto;
            color: black;
            border: 1px solid grey;
            margin: 5px;
            padding: 10px;
        }
        .menuTable{
            width: 1000px;
            height: 200px;
            margin: auto;
            margin-top: 20px;
            overflow: scroll;
        }
        .goComment{
            transform: translate(250px, -100px);
        }
        input:hover{
            cursor: pointer;
        }
        .comment{
            border: 1px solid black;
            width: auto;
            height: auto;
            margin: 5px;
            padding: 0px 0px 30px 20px;
        }
        .likeImg{
            height: 30px;
            width: 30px;
        }
        .menuImg{
            height: 160px;
            width: 240px;
            text-align: center;
        }
        .menuImg:hover{
            transform: scale(1.1);
            transition-duration: 1s;
        }
    </style>
    <jsp:include page="heading.jsp" />
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
    <form action="handleMyFavourite" style="transform: translate(350px, -123px);">
        <input type="hidden" name="action" value="addMyFavourite"/>
        <input type="hidden" name="restId" value="<%=restId%>"/>
        <input type="hidden" name="type" value="restaurant"/>
        <%
            String record = "&#9734;"; //white star
            if (request.getAttribute("record").toString().equalsIgnoreCase("true")) {
                record = "&#9733;"; //black star
            }
        %>
        <input type="submit" value="<%=record%>"/>
        <%--<input type="button" onclick="if(document.getElementById('addressOnMap').style.display =='block'){document.getElementById('addressOnMap').style.display ='none';}else{document.getElementById('addressOnMap').style.display ='block';}" value="Show address on map"/>--%>

    </form>
    <div class='like'><h3>Views: <%=viewCoiunt%></h3></div>
    <div class='menuTable'>
        <%
            ArrayList<Menu> menus = (ArrayList<Menu>) request.getAttribute("menus");
            String imgName, menuType, menuPath;
            for (int i = 0; i < menus.size(); i++) {
                if (menus.get(i).getMenuStartTime() == null || (menus.get(i).getMenuStartTime().compareTo(new Date()) <= 0 && menus.get(i).getMenuEndTime().compareTo(new Date()) >= 0)) {
                    imgName = menus.get(i).getImgName();
                    menuType = menus.get(i).getMenuType();
                    menuPath = menus.get(i).getMenuPath();
        %>
        <div style="margin: 5px; float: left; z-index: 2">
            <img src="upload/menu/<%=menuPath%>" class="menuImg"><br/>
            <a style="margin-left: 50px;" href="handleRestaurant?action=search&search=<%=String.valueOf(imgName)%>&selectedType=menu"><%=imgName%></a>
        </div>
        <%
                }
            }
        %>
    </div>
    <%
        ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
        int AccountuserId;
        String contents, title, mealDate, likeStatue;
        boolean Mood;
        for (int i = 0; i < comments.size(); i++) {
            AccountuserId = comments.get(i).getAccountuserId();
            Mood = comments.get(i).getMood();
            contents = comments.get(i).getContents();
            title = comments.get(i).getTitle();
            mealDate = comments.get(i).getMealDate();
            if (Mood) {
                likeStatue = "upload/Facebook_like_thumb.png";
            } else {
                likeStatue = "upload/Facebook-dislike.png";
            }
    %>
    <%--<iframe id='addressOnMap'hidden src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d29534.45450533497!2d114.14302954638568!3d22.285306700000024!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3404007ccfa9ad1d%3A0x650a77fab3962f73!2z6bql55W25Yue!5e0!3m2!1szh-TW!2shk!4v1544365328646" width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>--%>

    <div class="comment">
        <h2><img src="<%=likeStatue%>" class="likeImg"> <%=title%></h2>
        <p>User ID: <%=AccountuserId%><a style="margin-left: 50px;">Dinner Date: <%=mealDate%></a></p>
        <p>Comments: <%=contents%></p>
    </div>
    <%
        }
    %>
    <body>
    </body>
</html>
