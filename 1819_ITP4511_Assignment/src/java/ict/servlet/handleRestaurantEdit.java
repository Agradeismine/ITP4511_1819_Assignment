/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Restaurant;
import ict.bean.UserInfo;
import ict.db.RestaurantDB;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "handleRestaurantEdit", urlPatterns = {"/handleRestaurantEdit"})
public class handleRestaurantEdit extends HttpServlet {

    private RestaurantDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Restaurant restaurant;
        String action = request.getParameter("action");
        if ("editRestaurant".equalsIgnoreCase(action)) {
            int restId = Integer.parseInt(request.getParameter("restId"));
            UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
            restaurant = db.getRestaurantByRestId(restId);
            if (restaurant.getOwnerId() == user.getUserID()) {
                Restaurant restNewInfo = new Restaurant();      //restaurant new info
                restNewInfo.setRestId(restaurant.getRestId());
                restNewInfo.setName(request.getParameter("name"));
                restNewInfo.setOwnerId(restaurant.getOwnerId());
                restNewInfo.setRestIcon(restaurant.getRestIcon());
                restNewInfo.setAddress(request.getParameter("address"));
                restNewInfo.setDescription(request.getParameter("description"));
                boolean ss = db.editRestaurantRecord(restNewInfo);
                response.sendRedirect("ViewOwnRestaurant.jsp");
            } else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else if (action.equalsIgnoreCase("Delete")) {
            UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
            int ownerId = Integer.parseInt(request.getParameter("ownerId"));
            if (ownerId == user.getUserID()) {
                db.delRestaurantById(Integer.parseInt(request.getParameter("restId")));
                response.sendRedirect("ViewOwnRestaurant.jsp");
            } else {
                request.setAttribute("message", "You are not this restaurant owner or you have not login.<br>Please confirm you login as restaurant owner!");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/message.jsp");
                rd.forward(request, response);
            }
        } else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }

}
