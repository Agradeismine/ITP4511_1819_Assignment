/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.servlet;

import ict.bean.Restaurant;
import ict.bean.UserInfo;
import ict.db.CommentDB;
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
    private CommentDB cdb;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new RestaurantDB(dbUrl, dbUser, dbPassword);
        cdb = new CommentDB(dbUrl, dbUser, dbPassword);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            Restaurant rBean = db.getRestaurantByRestId(restId);
            request.setAttribute("rBean", rBean);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/" + targetURL);
            rd.forward(request, response);
        } else if ("doComment".equalsIgnoreCase(action)) {
            int RestaurantrestId = Integer.parseInt(request.getParameter("restId"));
            int AccountuserId = user.getUserID();
            boolean Mood = request.getParameter("likeStatue").equalsIgnoreCase("like") ? true : false;
            String contents = request.getParameter("contents");
            String title = request.getParameter("title");
            String date = request.getParameter("date");
            if (title.trim().length() > 0 && RestaurantrestId > 0 && AccountuserId > 0 && date.trim().length() > 0) {
                cdb.whiteComment(RestaurantrestId, AccountuserId, Mood, contents, title, date);
            }
            response.sendRedirect("handleRestaurant?action=view&restId=" + restId);
        }
    }

}
