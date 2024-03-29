<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Menu Icon</title>
        <script src="jslib/jquery-3.3.1.js"></script>
        <script>
            $(document).ready(function () {

            });
            function validate(form) {
                var checked = document.getElementById("formCheck").checked;
                var confirm = false;
                if (checked) {
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
        <jsp:useBean id="restMenu" class="ict.bean.Menu" scope="request" />
        <jsp:useBean id="restaurant" class="ict.bean.Restaurant" scope="request" />
        <%
            int imgId = restMenu.getImgId();
        %>
        <h1>Edit Menu</h1>
        <form method="post" action="uploadMenuIcon" enctype="multipart/form-data" onsubmit="return validate(this);">
            <div class="block" style='border-style: solid;'>
                <label>Restaurant Name: </label> <%= restaurant.getName()%><br><br>
                <label>MenuID:</label>
                <%
                    out.println(imgId + "<input type=\"hidden\" name=\"imgId\" value=\"" + imgId + "\" /><br><br>");
                %>
                <label>Old Menu Photo: <img src='upload/menu/<%= restMenu.getMenuPath()%>' width='50' height="50"></label><input type="file" name="uploadFile" value=""/><br>
                I confirm the menu information that I have changed. <input type="checkbox" id='formCheck'/> <br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>

    </body>
</html>
