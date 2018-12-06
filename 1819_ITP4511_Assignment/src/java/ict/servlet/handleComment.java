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
@WebServlet(name = "handleComment", urlPatterns = {"/handleComment"})
public class handleComment extends HttpServlet {

    private RestaurantDB db;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int restId = Integer.parseInt(request.getParameter("restId"));
        UserInfo user = ((UserInfo) request.getSession().getAttribute("userInfo"));
        String targetURL;
        if ("comment".equalsIgnoreCase(action)) {
            if (user.getUsername() != null) {
                targetURL = "leaveCommentAndLike.jsp";
            } else {
                request.setAttribute("notLogin", "Please login first.");
                
                targetURL = "handleRestaurant?action=view&restId=" + restId;
            }
            db.decreaseViewCount(restId, user);
            Restaurant rBean = db.getRestaurantByRestId(restId);
            request.setAttribute("rBean", rBean);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/" + targetURL);
            rd.forward(request, response);
        }
    }

}
