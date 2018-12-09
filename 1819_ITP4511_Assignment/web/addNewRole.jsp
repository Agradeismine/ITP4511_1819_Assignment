<jsp:include page="/heading.jsp" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Role</title>
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
    <body>
        <form method="post" action="handleAccountRole" onsubmit="return validate(this);">
            <div class="block" style='border-style: solid;'>
                <input type="hidden" name="action" value="addRole" />
                <label>Role Name: </label> <input name="name" type="text" value=""/> <br><br>
                I confirm the role information. <input type="checkbox" id='formCheck'/> <br><br>
                <div class='center'>
                    <input type="button" onclick="history.back()" value="Back"/> <input type="submit" value="submit"/> <br>
                </div>
            </div>
        </form>
    </body>
</html>
