<%-- 
    Document   : myFavourite
    Created on : Dec 7, 2018, 8:56:22 PM
    Author     : arthurking
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.db.*"%>
<%@page import="ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Favourite</title>
    </head>
    <style>
        .restIcon {
            height: 180px;
            width: 180px;
            margin-right: 50px;
        }
        .rest_table{
            border: 1px solid black;
            height: 200px;
            padding: 10px;
            margin: 5px;
        }
        .rest_content{
            margin-right: 50px;
        }
        .restInfo{
            transform: translate(200px, -200px);
        }
    </style>

    <jsp:include page="heading.jsp"/>
    <body>
        <h1>My Favourite!</h1>
        <%
            ArrayList<Restaurant> restList = (ArrayList<Restaurant>) request.getAttribute("restList");
            ArrayList<Menu> menuList = (ArrayList<Menu>) request.getAttribute("menuList");
            String restName, restIcon, address, description;
            int viewCount, tel, restId;
            for (int i = 0; i < restList.size(); i++) {
                restName = restList.get(i).getName();
                restIcon = restList.get(i).getRestIcon();
                viewCount = restList.get(i).getViewCount();
                address = restList.get(i).getAddress();
                description = restList.get(i).getDescription();
                tel = restList.get(i).getTel();
                restId = restList.get(i).getRestId();
        %>
        <div class="rest_table"><img class='restIcon' src='upload/<%=restIcon%>'/>
            <div class="restInfo">
                <h2 class='rest_content' style="color: orangered"><%=restName%></h2>
                <a class='rest_content' style="color: gray;">Views: <%=viewCount%></a><br/>
                <a class='rest_content'>Address: <%=address%></a><br/>
                <a class='rest_content'>Description: <%=description%></a><br/>
                <a class='rest_content'>Tel: <%=tel%></a><br/>
                <a class='rest_content' href='handleRestaurant?action=view&restId=<%=restId%>'>View more</a>
                <form action="handleMyFavourite">
                    <input type="hidden" name="action" value="addMyFavourite">
                    <input type="hidden" name="restId" value="<%=restId%>"/>
                    <input type="hidden" name="type" value="restaurant"/>
                    <input type="submit" value="remove form My Favourite">
                </form>
            </div>
        </div>
        <%
            }
            String menuPath, imgName, menuType;
            int imgId;
            for (int i = 0; i < menuList.size(); i++) {
                menuPath = menuList.get(i).getMenuPath();
                imgName = menuList.get(i).getImgName();
                menuType = menuList.get(i).getMenuType();
                imgId = menuList.get(i).getImgId();
%>
        <div class="rest_table">
            <img class='restIcon' src='upload/menu/<%=menuPath%>'/>
            <div class="restInfo">
                <h1><%=imgName%></h1>
                <h2><%=menuType%></h2>
                <form action="handleMyFavourite">
                    <input type="hidden" name="action" value="addMyFavourite"/>
                    <input type="hidden" name="restId" value="<%=imgId%>"/>
                    <input type="hidden" name="type" value="menu"/>
                    <input type="submit" value="remove form My Favourite"/>
                </form>
            </div>
        </div>
        <%
            }
        %>
    </body>
</html>
