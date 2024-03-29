/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import com.sun.net.httpserver.HttpServer;
import ict.bean.Comment;
import ict.bean.Menu;
import ict.bean.Restaurant;
import ict.bean.UserInfo;
import ict.db.CommentDB;
import ict.db.MenuDB;
import ict.db.RestaurantDB;
import ict.db.SearchDB;
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
@WebServlet(name = "handleRestaurant", urlPatterns = {"/handleRestaurant"})
public class handleRestaurant extends HttpServlet {

    private RestaurantDB db;
    private CommentDB cdb;
    private MenuDB mdb;
    private SearchDB sdb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
        cdb = new CommentDB(dbUrl, dbUser, dbPassword);
        mdb = new MenuDB(dbUrl, dbUser, dbPassword);
        sdb = new SearchDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("search");
        String type = request.getParameter("selectedType");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        ArrayList<Restaurant> restaurants;
        ArrayList<Menu> menus;

        if ("search".equalsIgnoreCase(action) && "restaurant".equalsIgnoreCase(type)) {
            if (name.trim().equals("")) {
                restaurants = db.getAllRestaurants();
            } else {
                restaurants = db.getRestaurantByName(name);
                sdb.saveSearchRecord(user.getUserID(), name, user.getDistrict());
            }
            request.setAttribute("restaurants", restaurants);
            request.setAttribute("type", type);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if ("search".equalsIgnoreCase(action) && "menu".equalsIgnoreCase(type)) {
            if (name.trim().equals("")) {
                menus = mdb.getRestaurantAllMenu();
            } else {
                menus = mdb.getMenuByKeywords(name);
            }
            String hasRecord;
            for (int i = 0; i < menus.size(); i++) {
                if (db.hasMyFavouriteRecord(user.getUserID(), menus.get(i).getImgId(), type)) {
                    hasRecord = "true";
                } else {
                    hasRecord = "false";
                }
                String recoed = "record" + i;
                request.setAttribute(recoed, hasRecord);
            }
            request.setAttribute("type", type);
            request.setAttribute("menus", menus);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        } else if ("view".equalsIgnoreCase(action)) {
            int restId = Integer.parseInt(request.getParameter("restId"));
            db.increaseViewCount(restId, user);
            String hasRecoed;
            if (db.hasMyFavouriteRecord(user.getUserID(), restId, "restaurant")) {
                hasRecoed = "true";
            } else {
                hasRecoed = "false";
            }
            Restaurant rBean = db.getRestaurantByRestId(restId);
            ArrayList<Comment> comments = cdb.getCommentByID(restId);
            menus = mdb.getRestaurantMenuByRestId(restId);
            request.setAttribute("rBean", rBean);
            request.setAttribute("comments", comments);
            request.setAttribute("menus", menus);
            request.setAttribute("record", hasRecoed);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/viewRestaurantDetails.jsp");
            rd.forward(request, response);
        } else if (action.equalsIgnoreCase("getEditRestaurant")) {
            Restaurant restaurant;
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editRestaurant.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else if (action.equalsIgnoreCase("editRestaurantIcon")) {
            Restaurant restaurant;
            int restId = Integer.parseInt(request.getParameter("restId"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                request.setAttribute("restaurant", restaurant);
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editRestaurantIcon.jsp");
                rd.forward(request, response);
            } else {
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
                request.setAttribute("type", "Delete Restaurant");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/confirmAction.jsp");
                rd.forward(request, response);
            } else {
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
