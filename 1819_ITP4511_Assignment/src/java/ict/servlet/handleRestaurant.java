/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import com.sun.net.httpserver.HttpServer;
import ict.bean.Restaurant;
import ict.bean.UserInfo;
import ict.db.RestaurantDB;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.security.provider.certpath.ResponderId;

/**
 *
 * @author arthurking
 */
@WebServlet(name = "handleRestaurant", urlPatterns = {"/handleRestaurant"})
public class handleRestaurant extends HttpServlet {

    private RestaurantDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("search");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        ArrayList<Restaurant> restaurants;

        if ("search".equalsIgnoreCase(action)) {
            if (name.trim().equals("")) {
                restaurants = db.getAllRestaurants();
            } else {
                restaurants = db.getRestaurantByName(name);
            }
            request.setAttribute("restaurants", restaurants);

            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if ("view".equalsIgnoreCase(action)) {
            int restId = Integer.parseInt(request.getParameter("restId"));
            db.increaseViewCount(restId, user);

            response.sendRedirect("viewRestaurantDetails.jsp");
        } else if (action.equalsIgnoreCase("maintainRestMenu")) {       //maintainRestMenumaintainRestMenumaintainRestMenumaintainRestMenumaintainRestMenumaintainRestMenu
            Restaurant restaurant;
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/maintainRestMenuPage.jsp");
                rd.forward(request, response);
            }else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else if (action.equalsIgnoreCase("getEditRestaurant")) {
            Restaurant restaurant;
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editRestaurant.jsp");
                rd.forward(request, response);
            }else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        }else if (action.equalsIgnoreCase("editRestaurantIcon")) {
            Restaurant restaurant;
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editRestaurantIcon.jsp");
                rd.forward(request, response);
            }else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else if (action.equalsIgnoreCase("confirmDeleteRestaurant")) {
            Restaurant restaurant;
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                request.setAttribute("type", "Delete");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/confirmAction.jsp");
                rd.forward(request, response);
            }else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else {
            System.out.println("No such action");
        }
    }

}
