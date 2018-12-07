/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Comment;
import ict.bean.UserInfo;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author arthurking
 */
public class CommentDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public CommentDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
    
    public ArrayList<Comment> getCommentByID(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Comment> comments = new ArrayList();
        Comment comment = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM RestaurantComment WHERE RestaurantrestId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                comment = new Comment();
                comment.setRestaurantrestId(rs.getInt("RestaurantrestId"));
                comment.setAccountuserId(rs.getInt("AccountuserId"));
                comment.setMood(rs.getBoolean("Mood"));
                comment.setContents(rs.getString("contents"));
                comment.setTitle(rs.getString("title"));
                comment.setMealDate(rs.getString("mealDate"));
                comments.add(comment);
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return comments;
    }
    
    public void whiteComment(int RestaurantrestId, int AccountuserId, boolean Mood, String contents, String title, String mealDate) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO RestaurantComment(RestaurantrestId, AccountuserId, Mood, contents, title, mealDate) VALUES (?, ?, ?, ?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, RestaurantrestId);
            pStmnt.setInt(2, AccountuserId);
            pStmnt.setBoolean(3, Mood);
            pStmnt.setString(4, contents);
            pStmnt.setString(5, title);
            pStmnt.setString(6, mealDate);
            pStmnt.executeUpdate();
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
