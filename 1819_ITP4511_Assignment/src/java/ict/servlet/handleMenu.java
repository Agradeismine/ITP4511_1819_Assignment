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
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author YipYi
 */
@WebServlet(name = "handleMenu", urlPatterns = {"/handleMenu"})
public class handleMenu extends HttpServlet {

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
        String action = request.getParameter("action");
        String name = request.getParameter("search");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        ArrayList<Restaurant> restaurants;
        Restaurant restaurant = null;
        Menu restMenu = null;

        if (action.equalsIgnoreCase("maintainRestMenu")) {       //maintainRestMenumaintainRestMenumaintainRestMenumaintainRestMenumaintainRestMenumaintainRestMenu
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                ArrayList<Menu> menus = menuDb.getRestaurantMenuByRestId(restId);
                request.setAttribute("restaurantMenu", menus);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/maintainRestMenuPage.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else if (action.equalsIgnoreCase("getEditMenu")) {
            int imgId = Integer.parseInt(request.getParameter("imgId"));
            restMenu = menuDb.getMenuByImgId(imgId);
            restaurant = db.getRestaurantByRestId(restMenu.getRestId());
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                request.setAttribute("restMenu", restMenu);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editMenu.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else if (action.equalsIgnoreCase("editMenuIcon")) {
            int imgId = Integer.parseInt(request.getParameter("imgId"));
            restMenu = menuDb.getMenuByImgId(imgId);
            restaurant = db.getRestaurantByRestId(restMenu.getRestId());
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restMenu", restMenu);
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editMenuIcon.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else if (action.equalsIgnoreCase("confirmDeleteMenu")) {
            int imgId = Integer.parseInt(request.getParameter("imgId"));
            restMenu = menuDb.getMenuByImgId(imgId);
            restaurant = db.getRestaurantByRestId(restMenu.getRestId());
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                request.setAttribute("restMenu", restMenu);
                request.setAttribute("type", "Delete Menu");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/confirmMenuAction.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else if (action.equalsIgnoreCase("addMenu")) {
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/AddMenu.jsp");
                rd.forward(request, response);
            } else {
                showErrorMsg(request, response, "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
            }
        } else {
            System.out.println("No such action");
        }
    }

    public void showErrorMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("message", msg);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/message.jsp");
        rd.forward(request, response);
    }

}
