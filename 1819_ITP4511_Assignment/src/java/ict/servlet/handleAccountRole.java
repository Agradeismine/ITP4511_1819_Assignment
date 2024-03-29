package ict.servlet;

import ict.bean.AccountRole;
import ict.bean.UserInfo;
import ict.db.AccountRoleDB;
import ict.db.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "handleAccountRole", urlPatterns = {"/handleAccountRole"})
public class handleAccountRole extends HttpServlet {

    private AccountRoleDB roleDb;
    private UserDB userDb;

    @Override
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        roleDb = new AccountRoleDB(dbUrl, dbUser, dbPassword);
        userDb = new UserDB(dbUrl, dbUser, dbPassword);
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
        } else if (action.equalsIgnoreCase("confirmDeleteRole")) {
            String roleName = request.getParameter("roleName");
            role = roleDb.getRoleByName(roleName);
            if (user.getRole().equalsIgnoreCase("admin")) {
                request.setAttribute("role", role);
                request.setAttribute("type", "Delete Role");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/confirmDeleteRole.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("message", "Please confirm you login as admin!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else if (action.equalsIgnoreCase("addNewRole")) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/addNewRole.jsp");
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
        } else if (action.equalsIgnoreCase("editUserRole")) {
            boolean isUpdateSuccess = false;
            if (user.getRole().equalsIgnoreCase("admin")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                UserInfo thisUser = userDb.queryCustByID(userId);
                String roleName = request.getParameter("roleName");
                if (!roleName.equalsIgnoreCase("") || roleName != null) {
                    thisUser.setRole(roleName);
                    isUpdateSuccess = userDb.updateUser(thisUser);
                }
                if (isUpdateSuccess) {
                    response.sendRedirect("handleAccount?action=maintainAccount");
                } else {
                    showErrorMsg(request, response, "Please confirm your inputted information is correct and no duplicate.");
                }
            } else {
                showErrorMsg(request, response, "Please confirm you have logged in as admin.");
            }
        } else if (action.equalsIgnoreCase("editUserInfo")) {
            boolean isUpdateSuccess = false;
            String[] districts = {"Central and Western", "Wan Chai", "Eastern", "Southern", "Yau Tsim Mong", "Sham Shui Po", "Kowloon City", "Wong Tai Sin", "Kwun Tong", "Kwai Tsing", "Tsuen Wan", "Tuen Mun", "Yuen Long", "North", "Tai Po", "Sha Tin", "Sai Kung", "Islands"};
            if (!user.getRole().equalsIgnoreCase("admin")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                UserInfo thisUser = userDb.queryCustByID(userId);
                String roleName = request.getParameter("roleName");
                String sex = request.getParameter("sex");
                String district = request.getParameter("district");
                String password1 = request.getParameter("password1");
                String password2 = request.getParameter("password2");

                if (sex.equalsIgnoreCase("M") || sex.equalsIgnoreCase("F")) {
                    thisUser.setSex(sex);
                }
                for (int i = 0; i < districts.length; i++) {
                    if (district.equalsIgnoreCase(districts[i])) {
                        thisUser.setDistrict(district);
                    }
                }
                if (password1.equals(password2) && !password1.equals("") && !password2.equals("")) {
                    thisUser.setPassword(password1);
                }

                isUpdateSuccess = userDb.updateUser(thisUser);

                if (isUpdateSuccess) {
                    HttpSession session = request.getSession(true);
                    //store the userInfo(which is a bean) to the session
                    session.setAttribute("userInfo", thisUser);
                    response.sendRedirect("index.jsp");
                } else {
                    showErrorMsg(request, response, "Please confirm your inputted information is correct and no duplicate.");
                }
            } else {
                showErrorMsg(request, response, "Please confirm you have logged in as admin.");
            }
        } else if (action.equalsIgnoreCase("Delete Role")) {
            String roleName = request.getParameter("name");
            if (user.getRole().equalsIgnoreCase("admin")) {
                roleDb.delRoleByName(roleName);
                response.sendRedirect("handleAccount?action=maintainAccRole");
            } else {
                showErrorMsg(request, response, "Please confirm you login as admin!");
            }
        } else if (action.equalsIgnoreCase("addRole")) {
            String roleName = request.getParameter("name");
            if (user.getRole().equalsIgnoreCase("admin")) {
                if (roleDb.addRoleByName(roleName)) {
                    response.sendRedirect("handleAccount?action=maintainAccRole");
                } else {
                    showErrorMsg(request, response, "Please confirm your input is correct!");
                }
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }

    public void showErrorMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("message", msg);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/message.jsp");
        rd.forward(request, response);
    }
}
