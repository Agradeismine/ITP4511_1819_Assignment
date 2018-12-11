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
 * @author arthurking
 */
@WebServlet(name = "handleMyFavourite", urlPatterns = {"/handleMyFavourite"})
public class handleMyFavourite extends HttpServlet {

    private RestaurantDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        int restId;
//        if(request.getParameter("restId")!=null){
//        restId = Integer.parseInt(request.getParameter("restId"));
//        }
        if (user.getUserID() > 0) {
            if ("viewMyFavourite".equalsIgnoreCase(action)) {
                ArrayList<Restaurant> restList = db.getMyFavouriteInRestaurant(user.getUserID());
                ArrayList<Menu> menuList = db.getMyFavouriteInMenu(user.getUserID());
                request.setAttribute("restList", restList);
                request.setAttribute("menuList", menuList);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/myFavourite.jsp");
                rd.forward(request, response);
            } else if ("addMyFavourite".equalsIgnoreCase(action)) {
                if (user.getUserID() > 0) {
                    String type = request.getParameter("type");
                    restId = Integer.parseInt(request.getParameter("restId"));
                    if (db.hasMyFavouriteRecord(user.getUserID(), restId, type)) {
                        db.deleteFavouriteRecord(user.getUserID(), restId, type);
                    } else {
                        db.addMyFavourite(user.getUserID(), restId, type);
                    }
                    ArrayList<Restaurant> restList = db.getMyFavouriteInRestaurant(user.getUserID());
                    ArrayList<Menu> menuList = db.getMyFavouriteInMenu(user.getUserID());
                    request.setAttribute("restList", restList);
                    request.setAttribute("menuList", menuList);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/myFavourite.jsp");
                    rd.forward(request, response);
                }
            } else {
                System.out.println("No such action");
            }
        } else {
            request.setAttribute("loginError", "<b>Please Login First.</b>");
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }
}
