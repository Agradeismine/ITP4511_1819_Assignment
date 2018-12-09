/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.AccountRole;
import ict.bean.Menu;
import ict.bean.UserInfo;
import ict.db.AccountRoleDB;
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

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        userDb = new UserDB(dbUrl, dbUser, dbPassword);
        roleDb= new AccountRoleDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        ArrayList<UserInfo> users;
        if (action.equalsIgnoreCase("maintainAccount")) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                users = userDb.getAllUserInfo();
                request.setAttribute("users", users);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/maintainAllAccount.jsp");
                rd.forward(request, response);
            } else if (!user.getRole().equalsIgnoreCase("admin")) {
                request.setAttribute("user", user);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/maintainAccount.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "Please confirm you have logged in.");
            }
        } else if (action.equalsIgnoreCase("maintainAccRole")) {
            if (user.getRole().equals("admin")) {
                ArrayList<AccountRole> roles =  roleDb.getAllRole();
                request.setAttribute("roles", roles);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/MaintainAccountRole.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        }
//else if (action.equalsIgnoreCase("editMenuIcon")) {
//            int imgId = Integer.parseInt(request.getParameter("imgId"));
//            restMenu = menuDb.getMenuByImgId(imgId);
//            restaurant = db.getRestaurantByRestId(restMenu.getRestId());
//            if (restaurant.getOwnerId() == user.getUserID()) {
//                request.setAttribute("restMenu", restMenu);
//                request.setAttribute("restaurant", restaurant);
//                RequestDispatcher rd;
//                rd = getServletContext().getRequestDispatcher("/editMenuIcon.jsp");
//                rd.forward(request, response);
//            } else {
//                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
//            }
//        } else if (action.equalsIgnoreCase("confirmDeleteMenu")) {
//            int imgId = Integer.parseInt(request.getParameter("imgId"));
//            restMenu = menuDb.getMenuByImgId(imgId);
//            restaurant = db.getRestaurantByRestId(restMenu.getRestId());
//            if (restaurant.getOwnerId() == user.getUserID()) {
//                request.setAttribute("restaurant", restaurant);
//                request.setAttribute("restMenu", restMenu);
//                request.setAttribute("type", "Delete Menu");
//                RequestDispatcher rd;
//                rd = getServletContext().getRequestDispatcher("/confirmMenuAction.jsp");
//                rd.forward(request, response);
//            } else {
//                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
//            }
//        } else if (action.equalsIgnoreCase("addMenu")) {
//            int restId = Integer.parseInt(request.getParameter("restId"));
//            restaurant = db.getRestaurantByRestId(restId);
//            if (restaurant.getOwnerId() == user.getUserID()) {
//                request.setAttribute("restaurant", restaurant);
//                RequestDispatcher rd;
//                rd = getServletContext().getRequestDispatcher("/AddMenu.jsp");
//                rd.forward(request, response);
//            } else {
//                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
//            }
//        } else {
//            System.out.println("No such action");
//        }
    }

    public void showErrorMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("message", msg);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/message.jsp");
        rd.forward(request, response);
    }

}
