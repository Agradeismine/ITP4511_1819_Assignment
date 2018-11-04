<%-- 
    Document   : index
    Created on : Nov 4, 2018, 9:56:15 PM
    Author     : YipYi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/login.css">
        <script src="js/login.js"></script>
        <title>JSP Page</title>
    </head>
    <div class="nav">
        <table style="position: absolute; right: 0px; top: 0px;">
            <tr>
                <td><button onclick="document.getElementById('id01').style.display = 'block'" style="width:auto;" class="login_btn"><b>Login</b></button></td>
            </tr>
        </table>
    </div>
    <div style="width: 100%; height: 100px; background-color: black; color: white; margin-top: 10px;">
        <a style="margin: 20px; font-size: 36px;">Takeaway King</a>
        <input type="text" placeholder="Searching..." style="width: 400px;">
    </div>

    <div id="id01" class="modal">

        <form class="modal-content animate" action="/action_page.php">
            <div class="imgcontainer">
                <span onclick="document.getElementById('id01').style.display = 'none'" class="close" title="Close Modal">&times;</span>
            </div>

            <div class="container">
                <label for="uname"><b>Username</b></label>
                <input type="text" placeholder="Enter Username" name="uname" required>

                <label for="psw"><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw" required>

                <button type="submit"><b>Login</b></button>
                <label>
                    <input type="checkbox" checked="checked" name="remember"> Remember me
                </label>
            </div>

            <div class="container" style="background-color:#f1f1f1">
                <button type="button" onclick="document.getElementById('id01').style.display = 'none'" class="cancelbtn">Cancel</button>
                <span class="psw">Forgot <a href="#">password?</a></span>
            </div>
        </form>
    </div>
</body>
</html>
