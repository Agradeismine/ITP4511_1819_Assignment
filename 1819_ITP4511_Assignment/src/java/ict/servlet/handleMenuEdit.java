/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Menu;
import ict.bean.Restaurant;
import ict.bean.UserInfo;
import ict.db.MenuDB;
import ict.db.RestaurantDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "handleMenuEdit", urlPatterns = {"/handleMenuEdit"})
public class handleMenuEdit extends HttpServlet {

    private RestaurantDB db;
    private MenuDB menuDb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
        menuDb = new MenuDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Restaurant restaurant;
        Menu menu;
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        String action = request.getParameter("action");
        if ("editMenu".equalsIgnoreCase(action)) {
            int imgId = Integer.parseInt(request.getParameter("imgId"));
            menu = menuDb.getMenuByImgId(imgId);
            restaurant = db.getRestaurantByRestId(menu.getRestId());
            if (restaurant.getOwnerId() == user.getUserID()) {
                Menu menuNewInfo = new Menu();      //menu new info
                menuNewInfo.setRestId(menu.getRestId());
                menuNewInfo.setImgId(menu.getImgId());
                menuNewInfo.setImgName(request.getParameter("menuName"));
                menuNewInfo.setMenuType(request.getParameter("menuType"));
                menuNewInfo.setMenuPath(menu.getMenuPath());
                if (!request.getParameter("menuType").equalsIgnoreCase("General")) {
                    menuNewInfo.setMenuStartTime(Date.valueOf(request.getParameter("menuStartDate")));
                    menuNewInfo.setMenuEndTime(Date.valueOf(request.getParameter("menuEndDate")));
                }
                boolean isUpdateSuccess = menuDb.updateMenuRecord(menuNewInfo);
                if (isUpdateSuccess) {
                    response.sendRedirect("handleMenu?action=maintainRestMenu&restId="+restaurant.getRestId());
                } else {
                    showErrorPage(request, response, "There have some error information in update process.");
                }
            } else {
                showErrorPage(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else if (action.equalsIgnoreCase("Delete")) {
            int ownerId = Integer.parseInt(request.getParameter("ownerId"));
            if (ownerId == user.getUserID()) {
                db.delRestaurantById(Integer.parseInt(request.getParameter("restId")));
                response.sendRedirect("ViewOwnRestaurant.jsp");
            } else {
                showErrorPage(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }

    public void showErrorPage(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("message", msg);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/message.jsp");
        rd.forward(request, response);
    }
}
