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
        if("viewMyFavourite".equalsIgnoreCase(action)){
            ArrayList<Restaurant> restList = db.getMyFavouriteInRestaurant(user.getUserID());
            ArrayList<Menu> menuList = db.getMyFavouriteInMenu(user.getUserID());
            request.setAttribute("restList", restList);
            request.setAttribute("menuList", menuList);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/myFavourite.jsp");
            rd.forward(request, response);
        }
    }
}
