
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.db.*"%>
<%@page import="ict.bean.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Takeaway King</title>
        <script src="jslib/jquery-3.3.1.js"></script>
        <script>
            $(document).ready(function () {
                $("input#userId").on({
                    keydown: function (e) {
                        if (e.which === 32)
                            return false;
                    },
                    change: function () {
                        this.value = this.value.replace(/\s/g, "");
                    }
                });
                $("input#password").on({
                    keydown: function (e) {
                        if (e.which === 32)
                            return false;
                    },
                    change: function () {
                        this.value = this.value.replace(/\s/g, "");
                    }
                });
            });
        </script>
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
                transform: translateY(-180px);
            }
            .rest_content{
                margin-right: 50px;
            }
            .restInfo{
                transform: translate(200px, -205px);
            }
            .likeImg{
                height: 30px;
                width: 30px;
            }
            .ranking{
                height: 200px;
                width: 500px;
                border: 1px solid black;
                transform: translate(500px, -50px);
            }
            .popKeywords{
                height: 150px;
                width: 400px;
                border: 1px solid black;
                transform: translate(0px, -200px);
            }
        </style>
    </head>
    <body>
        <jsp:include page="/heading.jsp" />
        <jsp:useBean id="user" class="ict.bean.UserInfo" scope="session"/>
        <%
            String dbUrl = this.getServletContext().getInitParameter("dbUrl");
            String dbUser = this.getServletContext().getInitParameter("dbUser");
            String dbPassword = this.getServletContext().getInitParameter("dbPassword");
            RestaurantDB db = new RestaurantDB(dbUrl, dbUser, dbPassword);
            SearchDB sdb = new SearchDB(dbUrl, dbUser, dbPassword);
            ArrayList<Restaurant> restaurants = db.getAllRestaurants();
            String selectedType;
            if (user != null) {
                user = ((UserInfo) request.getSession().getAttribute("userInfo"));
                String role = user.getRole();
                if ("admin".equalsIgnoreCase(role) || "owner".equalsIgnoreCase(role)) {
        %>
        <br><a href="ViewOwnRestaurant.jsp">View Own Restaurant (default restaurant owner can see this function only, set it later)</a>
        <a href="handleAnalytic?action=viewReport&ownerId=">view report</a>
        <%
                }
            }
        %>
        <form action="handleRestaurant">
            <input type="hidden" name="action" value="search">
            <input type="text" placeholder="Search..." name="search" style="width: 400px;"><br/>
            <input type="radio" name="selectedType" value="restaurant" checked>Restaurant
            <input type="radio" name="selectedType" value="menu">Menu
            <input type="submit">
        </form><br/>
        <div class="ranking">
            <a style="font-size: 30px; color: red;">Restaurant Ranking</a><br/>
            <%@ taglib uri="/WEB-INF/tlds/ranking.tld" prefix="ict"%>
            <ict:ranking restaurants = "<%=restaurants%>" />
        </div>
        <div class="popKeywords" style="overflow: scroll;">
            <a style="font-size: 30px; color: blue;">Popular Keywords</a><br/>
            <%
                ArrayList[] list = sdb.popKeywords();
                ArrayList<String> keywords = list[0];
                String keyword;
                try {
                    for (int i = 0; i < 20; i++) {
                        keyword = keywords.get(i);
            %>
            <div style="float: left; margin: 5px;">
                <a href="handleRestaurant?action=search&search=<%=keyword%>&selectedType=restaurant"><%=keyword%></a>
            </div>
            <%
                    }
                } catch (Exception e) {
                }
            %>
        </div>
        <%            if (request.getAttribute("type") == null) { //check selected type
                selectedType = "restaurant";
            } else {
                selectedType = String.valueOf(request.getAttribute("type"));
            }
            if (request.getAttribute("restaurants") == null) { //for restaurant
                restaurants = db.getAllRestaurants();
            } else {
                restaurants = (ArrayList) request.getAttribute("restaurants");
            }
            int viewCount, tel, like, dislike;
            String restIcon, name, address, description, restId;
            if ("restaurant".equalsIgnoreCase(selectedType)) {
                for (int i = 0; i < restaurants.size(); i++) {
                    Restaurant restaurant = restaurants.get(i);
                    restIcon = restaurant.getRestIcon();
                    restId = String.valueOf(restaurant.getRestId());
                    name = restaurant.getName();
                    address = restaurant.getAddress();
                    description = restaurant.getDescription();
                    viewCount = restaurant.getViewCount();
                    tel = restaurant.getTel();
                    like = restaurant.getLike();
                    dislike = restaurant.getDislike();
        %>
        <div class='rest_table'>
            <img class='restIcon' src='upload/<%=restIcon%>'/>
            <div class="restInfo">
                <h2 class='rest_content' style="color: orangered"><%=name%></h2>
                <a class='rest_content' style="color: gray;">Views: <%=viewCount%></a><br/>
                <a class='rest_content' style="color: green"><img src="upload/Facebook_like_thumb.png" class="likeImg"> <%=like%></a> 
                <a class='rest_content' style="color: red;"><img src="upload/Facebook-dislike.png" style="height: 15px; width: 15px;"> <%=dislike%></a><br/>
                <a class='rest_content'>Address: <%=address%></a><br/>
                <a class='rest_content'>Description: <%=description%></a><br/>
                <a class='rest_content'>Tel: <%=tel%></a><br/>
                <a class='rest_content' href='handleRestaurant?action=view&restId=<%=restId%>'>View more</a>
            </div>
        </div>
        <%
            }
        } else if ("menu".equalsIgnoreCase(selectedType)) {
            ArrayList<Menu> menus = (ArrayList<Menu>) request.getAttribute("menus");
            for (int i = 0; i < menus.size(); i++) {
                if (menus.get(i).getMenuStartTime() == null || (menus.get(i).getMenuStartTime().compareTo(new Date()) <= 0 && menus.get(i).getMenuEndTime().compareTo(new Date()) >= 0)) {
                    String imgName = menus.get(i).getImgName();
                    String menuType = menus.get(i).getMenuType();
                    String menuPath = menus.get(i).getMenuPath();
                    int imgId = menus.get(i).getImgId();
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
                    <%
                        String record = "&#9734;"; //white star
                        String getRecord = "record" + i;
                        if (request.getAttribute(getRecord).toString().equalsIgnoreCase("true")) {
                            record = "&#9733;"; //black star
                        }
                    %>
                    <input type="submit" value="<%=record%>"/>
                </form>
            </div>
        </div>
        <%
                    }
                }
            }
        %>
    </body>
</html>
