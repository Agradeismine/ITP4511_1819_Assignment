/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Restaurant;
import ict.bean.UserInfo;
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
 * @author YipYi
 */
public class RestaurantDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public RestaurantDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<Restaurant> getAllRestaurants() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Restaurant> restaurantBeans = new ArrayList();

        Restaurant restaurantBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Restaurant;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurantBean = new Restaurant();
                restaurantBean.setRestId(rs.getInt("restId"));
                restaurantBean.setName(rs.getString("name"));
                restaurantBean.setOwnerId(rs.getInt("ownerId"));
                restaurantBean.setRestIcon(rs.getString("restIcon"));
                restaurantBean.setAddress(rs.getString("address"));
                restaurantBean.setDescription(rs.getString("description"));
                restaurantBeans.add(restaurantBean);
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return restaurantBeans;
    }

    public ArrayList<Restaurant> getRestaurantByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Restaurant> restaurantBeans = new ArrayList();
        Restaurant restaurantBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Restaurant;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurantBean = new Restaurant();
                restaurantBean.setRestId(rs.getInt("restId"));
                restaurantBean.setName(rs.getString("name"));
                restaurantBean.setRestIcon(rs.getString("restIcon"));
                restaurantBean.setAddress(rs.getString("address"));
                restaurantBean.setDescription(rs.getString("description"));
                if (restaurantBean.getName().toLowerCase().contains(name.toLowerCase())) {
                    restaurantBeans.add(restaurantBean);
                }
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return restaurantBeans;
    }

    public void increaseViewCount(int restId, UserInfo user) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int userId = user.getUserID();
        String district = user.getDistrict();
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO RestViewCount (RestaurantrestId, userID, date, ditrict) "
                    + "VALUES ("+restId+", "+userId+", SYSDATE, "+district+")";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Restaurant> getRestaurantByOwnerId(int ownerId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Restaurant> restaurantBeans = new ArrayList();
        Restaurant restaurantBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Restaurant WHERE ownerId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, ownerId);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurantBean = new Restaurant();
                restaurantBean.setRestId(rs.getInt("restId"));
                restaurantBean.setName(rs.getString("name"));
                restaurantBean.setOwnerId(rs.getInt("ownerId"));
                restaurantBean.setRestIcon(rs.getString("restIcon"));
                restaurantBean.setAddress(rs.getString("address"));
                restaurantBean.setDescription(rs.getString("description"));
                restaurantBeans.add(restaurantBean);
            }

            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return restaurantBeans;
    }

    public Restaurant getRestaurantByRestId(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Restaurant restaurant = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Restaurant WHERE restId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurant = new Restaurant();
                restaurant.setRestId(rs.getInt("restId"));
                restaurant.setName(rs.getString("name"));
                restaurant.setOwnerId(rs.getInt("ownerId"));
                restaurant.setRestIcon(rs.getString("restIcon"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDescription(rs.getString("description"));
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return restaurant;
    }

    public boolean editRestaurantRecord(Restaurant restNewInfo) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean editSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "update Restaurant set name = ?, restIcon =?, address =?, description = ? WHERE restId=?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, restNewInfo.getName());
            pStmnt.setString(2, restNewInfo.getRestIcon());
            pStmnt.setString(3, restNewInfo.getAddress());
            pStmnt.setString(4, restNewInfo.getDescription());
            pStmnt.setInt(5, restNewInfo.getRestId());
            int row = pStmnt.executeUpdate();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);
            if (row == 1) {
                editSuccess = true;
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return editSuccess;
    }
        public boolean delRestaurantById(int id) {
        Statement stmnt = null;
        Connection cnnct = null;
        boolean delSuccess = false;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "DELETE FROM Restaurant WHERE restId=" + id;
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
            delSuccess = true;
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return delSuccess;
    }

}
