
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
            }
            .rest_content{
                margin-right: 50px;
            }
            .restInfo{
                transform: translate(200px, -200px);
            }
        </style>
    </head>

    <body>
        <jsp:include page="/heading.jsp" />
        <br><a href="ViewOwnRestaurant.jsp">View Own Restaurant (default restaurant owner can see this function only, set it later)</a><br><br>
        <form action="handleRestaurant">
            <input type="hidden" name="action" value="search">
            <input type="text" placeholder="Search..." name="search" style="width: 400px;"><br/>
            <input type="radio" name="selectedType" value="restaurant" checked>Restaurant
            <input type="radio" name="selectedType" value="menu">Menu
            <input type="submit">
        </form><br/>
        <%
            RestaurantDB db = new RestaurantDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
            MenuDB mdb = new MenuDB(this.getServletContext().getInitParameter("dbUrl"), this.getServletContext().getInitParameter("dbUser"), this.getServletContext().getInitParameter("dbPassword"));
            ArrayList<Restaurant> restaurants;
            String selectedType;
            if (request.getAttribute("type") == null) { //check selected type
                selectedType = "restaurant";
            } else {
                selectedType = String.valueOf(request.getAttribute("type"));
            }
            if (request.getAttribute("restaurants") == null) { //for restaurant
                restaurants = db.getAllRestaurants();
            } else {
                restaurants = (ArrayList) request.getAttribute("restaurants");
            }
            int viewCount, tel;
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
        %>
        <div class='rest_table'>
            <img class='restIcon' src='upload/<%=restIcon%>'/>
            <div class="restInfo">
                <h2 class='rest_content' style="color: orangered"><%=name%></h2>
                <a class='rest_content' style="color: gray;">Views: <%=viewCount%></a><br/>
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
                <form action="handleRestaurant">
                    <input type="hidden" name="action" value="addMyFavourite"/>
                    <input type="hidden" name="restId" value="<%=imgId%>"/>
                    <input type="hidden" name="type" value="menu"/>
                    <%
                        String record = "&#9734;"; //white star
                        String getRecord = "record"+i;
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
        %>
    </body>
</html>
