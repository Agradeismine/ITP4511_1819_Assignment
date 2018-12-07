<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Menu</title>
        <script src="jslib/jquery-3.3.1.js"></script>
        <script>
            $(document).ready(function () {
                $("#menuType").change(function () {
                    if ($("#menuType option:selected").val() != "General") {
                        $("#menuShowDate").show();
                    } else {
                        $("#menuShowDate").hide();
                    }
                });

            });     //End of $(document).ready

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
        <jsp:useBean id="restMenu" class="ict.bean.Menu" scope="request" />
        <%
            int imgId = restMenu.getImgId();
        %>
        <h1>Edit Menu</h1>
        <form method="post" action="handleMenuEdit" onsubmit="return validate(this);">
            <div class="block" style='border-style: solid;'>
                <input type="hidden" name="action" value="editMenu" />
                <label>Restaurant Name:</label> <%= restaurant.getName()%><br><br>
                <label>Menu ID:</label>
                <%
                    out.println(imgId + "<input type=\"hidden\" name=\"imgId\" value=\"" + imgId + "\" /><br><br>");
                %>
                <label>Menu Name: </label> <input name="menuName" type="text" value="<%= restMenu.getImgName()%>"/> <br><br>
                <label>Menu Type: </label> 
                <select id='menuType' style='width:160px;'name="menuType" id="type">
                    <option label='General' value="General"></option>
                    <option label='Special' value="Special"></option>
                    <option label='Temporary' value="Temporary"></option>
                    <option label='Seasonal' value="Seasonal"></option>
                </select><br><br>
                <label>Menu Photo: </label> <img src='upload/menu/<%= restMenu.getMenuPath()%>' width='160' height="160"><br><br>
                <div id='menuShowDate' hidden>
                    <label>Menu Start Date:</label><input name="menuStartDate" type="date" value="<%= restMenu.getMenuStartTime()%>"/> <br><br>
                    <label>Menu End Date:</label><input name="menuEndDate" type="date" value="<%= restMenu.getMenuEndTime()%>"/> <br><br>
                </div >
                I confirm the restaurant information that I have changed. <input type="checkbox" id='formCheck'/> <br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>

    </body>
</html>
