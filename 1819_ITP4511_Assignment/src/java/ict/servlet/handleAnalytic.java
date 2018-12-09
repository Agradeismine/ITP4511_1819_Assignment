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
@WebServlet(name = "handleAnalytic", urlPatterns = {"/handleAnalytic"})
public class handleAnalytic extends HttpServlet {

    private RestaurantDB rdb;
    private SearchDB sdb;
    
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        rdb = new RestaurantDB(dbUrl, dbUser, dbPassword);
        sdb = new SearchDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        String role = user.getRole();
        if ("admin".equalsIgnoreCase(role) || "owner".equalsIgnoreCase(role)) {
            if ("viewReport".equalsIgnoreCase(action)) {
                if ("owner".equalsIgnoreCase(role)) {
                    int restId = rdb.getRestaurantByOwnerId(user.getUserID()).get(0).getRestId();
                    String restName = rdb.getRestaurantByOwnerId(user.getUserID()).get(0).getName();
                    int numberOfVisitor = rdb.ViewCount(restId);
                    request.setAttribute("numberOfVisitor", numberOfVisitor);
                    request.setAttribute("restName", restName);
                    request.setAttribute("avgByMonth", rdb.avgByMonth(restId));
                    request.setAttribute("avgByDistrict", rdb.avgByDistrict(restId));
                }else if("admin".equalsIgnoreCase(role)){
                    request.setAttribute("popKeywords", sdb.popKeywords());
                    request.setAttribute("allRest", rdb.getAllRestaurants());
                }
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/myReport.jsp");
                rd.forward(request, response);
            }
        } else {
            System.out.println("get out here!");
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }
    }
}
