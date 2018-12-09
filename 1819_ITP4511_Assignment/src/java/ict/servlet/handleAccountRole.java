package ict.servlet;

import ict.bean.AccountRole;
import ict.bean.UserInfo;
import ict.db.AccountRoleDB;
import ict.db.UserDB;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "handleAccountRole", urlPatterns = {"/handleAccountRole"})
public class handleAccountRole extends HttpServlet {

    private AccountRoleDB roleDb;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        roleDb = new AccountRoleDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        AccountRole role;
        if (action.equalsIgnoreCase("edit")) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                String roleName = request.getParameter("roleName");
                role = roleDb.getRoleByName(roleName);
                request.setAttribute("role", role);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editRole.jsp");
                rd.forward(request, response);

            } else {
                showErrorMsg(request, response, "Please confirm you have logged in as admin.");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        AccountRole role;
        if (action.equalsIgnoreCase("editRole")) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                String newRoleName = request.getParameter("name");
                String oldRoleName = request.getParameter("oldName");
                boolean hasThisRoleName = roleDb.hasThisRoleName(newRoleName);     //return false = no record
                if (!newRoleName.equalsIgnoreCase("") && newRoleName != null && !hasThisRoleName) {
                    boolean isSuccess = roleDb.updateRoleByName(oldRoleName, newRoleName);
                    if (isSuccess) {
                        response.sendRedirect("handleAccount?action=maintainAccRole");
                    } else {
                        showErrorMsg(request, response, "There have some error information in update process.");
                    }
                } else {
                    showErrorMsg(request, response, "Please confirm your inputted role name is correct and no duplicate.");
                }
            } else {
                showErrorMsg(request, response, "Please confirm you have logged in as admin.");
            }
        }
    }

    public void showErrorMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("message", msg);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/message.jsp");
        rd.forward(request, response);
    }
}
