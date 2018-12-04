<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Restaurant</title>
        <script>
            function validate(form) {
                var checked = document.getElementById("formCheck").checked;
                var confirm = false;
                if (checked == true) {
                    confirm = confirm('Do you really want to submit the changing?');
                } else {
                    alert('Please ensure you have confirmed the information.');
                }
                return confirm;
            }
        </script>
    </head>
    <style>
        .block{
            width: 800px;
            display: block;
            margin: 5px 0;
        }
        .center{
            margin: 5px 0;
            text-align: center;
        }
        label {
            display: inline-block;
            width: 170px;
            text-align: left;
        }
        input,textarea{
            vertical-align: top;
        }
    </style>
    <body>
        <jsp:useBean id="restaurant" class="ict.bean.Restaurant" scope="request" />
        <%
            int restId = restaurant.getRestId();
        %>
        <h1>Edit Restaurant</h1>
        <form method="get" action="handleRestaurantEdit" onsubmit="return validate(this);">
            <div class="block" style='border-style: solid;'>
                <input type="hidden" name="action" value="editRestaurant" />
                <label>ID:</label>
                <%
                    out.println(restId + "<input type=\"hidden\" name=\"restId\" value=\"" + restId + "\" /><br><br>");
                %>
                <label>Restaurant Name: </label> <input name="name" type="text" value="<%= restaurant.getName()%>"/> <br><br>
                <label>Restaurant Icon: </label> <img src='upload/<%= restaurant.getRestIcon()%>' width='160' height="160"><br><br>
                <label>Restaurant Address: </label> <input name="address" type="text" value="<%= restaurant.getAddress()%>"/> <br><br>
                <label>Restaurant Description: </label> <input name="description" type="text" value="<%= restaurant.getDescription()%>"/> <br><br>
                I confirm the restaurant information that I have changed. <input type="checkbox" id='formCheck'/> <br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>

    </body>
</html>
