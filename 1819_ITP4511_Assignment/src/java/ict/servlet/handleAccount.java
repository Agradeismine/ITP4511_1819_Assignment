/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.AccountRole;
import ict.bean.Menu;
import ict.bean.Restaurant;
import ict.bean.UserInfo;
import ict.db.AccountRoleDB;
import ict.db.MenuDB;
import ict.db.RestaurantDB;
import ict.db.UserDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "handleAccount", urlPatterns = {"/handleAccount"})
public class handleAccount extends HttpServlet {

    private UserDB userDb;
    private AccountRoleDB roleDb;
    private RestaurantDB restDb;
    private MenuDB menuDb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userDb = new UserDB(dbUrl, dbUser, dbPassword);
        roleDb = new AccountRoleDB(dbUrl, dbUser, dbPassword);
        restDb = new RestaurantDB(dbUrl, dbUser, dbPassword);
        menuDb = new MenuDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        ArrayList<UserInfo> users;
        try {
            if (action.equalsIgnoreCase("maintainAccount")) {
                if (user.getRole().equalsIgnoreCase("admin")) {
                    users = userDb.getAllUserInfo();
                    request.setAttribute("users", users);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/maintainAllAccount.jsp");
                    rd.forward(request, response);
                } else if (!user.getRole().equalsIgnoreCase("admin")) {
                    ArrayList<AccountRole> roles = roleDb.getAllRole();
                    request.setAttribute("roles", roles);
                    request.setAttribute("thisUser", user);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/editAccountInfo.jsp");
                    rd.forward(request, response);
                } else {
                    showErrorMsg(request, response, "Please confirm you have logged in.");
                }
            } else if (action.equalsIgnoreCase("maintainAccRole")) {
                if (user.getRole().equals("admin")) {
                    ArrayList<AccountRole> roles = roleDb.getAllRole();
                    request.setAttribute("roles", roles);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/MaintainAccountRole.jsp");
                    rd.forward(request, response);
                } else {
                    showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                }
            } else if (action.equalsIgnoreCase("getEditAccount")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                UserInfo thisUser = userDb.queryCustByID(userId);
                if (thisUser.getUserID() == user.getUserID() || user.getRole().equalsIgnoreCase("admin")) {
                    ArrayList<AccountRole> roles = roleDb.getAllRole();
                    request.setAttribute("roles", roles);
                    request.setAttribute("thisUser", thisUser);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/editAccountInfo.jsp");
                    rd.forward(request, response);
                } else {
                    showErrorMsg(request, response, "You are not this ccount owner or you have not login.<br>Please confirm you login as restaurant owner!");
                }
            } else if (action.equalsIgnoreCase("deleteAccountConfirmation")) {
                int userId = Integer.parseInt(request.getParameter("userId"));
                UserInfo thisUser = userDb.queryCustByID(userId);
                if (user.getRole().equalsIgnoreCase("admin")) {
                    request.setAttribute("thisUser", thisUser);
                    request.setAttribute("type", "Delete User");
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/confirmDeleteUser.jsp");
                    rd.forward(request, response);
                } else {
                    showErrorMsg(request, response, "Please confirm you login as admin!");
                }
            } else {
                System.out.println("No such action");
            }
        } catch (Exception ex) {
            response.sendRedirect("index.jsp");
            ex.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        ArrayList<UserInfo> users;
        if (action.equalsIgnoreCase("Delete User")) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            boolean delSuccess = false;
            UserInfo thisUser;
            if (user.getRole().equalsIgnoreCase("admin")) {
                thisUser = userDb.queryCustByID(userId);
                if (thisUser.getRole().equalsIgnoreCase("owner")) { //the ready-to-delete user is owner
                    ArrayList<Restaurant> restaurants = restDb.getRestaurantByOwnerId(userId);
                    if (restaurants != null) {
                        showErrorMsg(request, response, "The restaurant owner has one or more restaurants. The restaurants should be deleted before delete the account.");

//                        for (int i = 0; i < restaurants.size(); i++) {
//                            ArrayList<Menu> menus = menuDb.getRestaurantMenuByRestId(((Restaurant) restaurants.get(i)).getRestId());
//                            for (int j = 0; j < menus.size(); j++) {
//                                menuDb.delMenuByImgId(menus.get(j).getImgId());
//                            }
//                            restDb.delRestaurantById(restaurants.get(i).getRestId());
//                        }
//                        delSuccess = userDb.delAccountById(userId);
                    }
                } else {
                    delSuccess = userDb.delAccountById(userId);
                }
                if (delSuccess) {
                    response.sendRedirect("handleAccount?action=maintainAccount");
                } else {
                    showErrorMsg(request, response, "There are some error when deleting the user.");
                }
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
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
