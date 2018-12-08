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
@WebServlet(name = "handleAnalytic", urlPatterns = {"/handleAnalytic"})
public class handleAnalytic extends HttpServlet {

    private RestaurantDB rdb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        rdb = new RestaurantDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        String role = user.getRole();
        if ("admin".equalsIgnoreCase(role) || "owner".equalsIgnoreCase(role)) {
            if ("viewReport".equalsIgnoreCase(action)) {
                int numberOfVisitor = rdb.ViewCount(rdb.getRestaurantByOwnerId(user.getUserID()).get(0).getRestId());
                request.setAttribute("numberOfVisitor", numberOfVisitor);
                request.setAttribute("restName", rdb.getRestaurantByOwnerId(user.getUserID()).get(0).getName());
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/myReport.jsp");
                rd.forward(request, response);
            }
        } else {
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }
}
