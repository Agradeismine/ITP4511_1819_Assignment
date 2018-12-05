/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Restaurant;
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
            String preQueryStatement = "SELECT * FROM Restaurant;";
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
                restaurantBean.setViewCount(ViewCount(restaurantBean.getRestId()));
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
            String preQueryStatement = "SELECT DISTINCT r.restId, r.name, r.restIcon, r.address, r.description, rt.tagName, rvc.count"
                    + " FROM Restaurant r, RestaurantTag rt, RestViewCount rvc"
                    + " WHERE r.restId = rt.RestaurantrestId"
                    + " AND r.restId = rvc.RestaurantrestId;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurantBean = new Restaurant();
                restaurantBean.setRestId(rs.getInt("r.restId"));
                restaurantBean.setName(rs.getString("r.name"));
                restaurantBean.setRestIcon(rs.getString("r.restIcon"));
                restaurantBean.setAddress(rs.getString("r.address"));
                restaurantBean.setDescription(rs.getString("r.description"));
                restaurantBean.setViewCount(rs.getInt("rvc.count"));
                if (restaurantBean.getName().toLowerCase().contains(name.toLowerCase()) || 
                    rs.getString("rt.tagName").contains(name.toLowerCase())) {
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
        int userId = 0;
        int RestaurantrestId = 0;
        String district = "N/A";
        if (user != null) {
            userId = user.getUserID();
            district = user.getDistrict();
        }
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM RestViewCount;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            boolean hasRestaurantRecord = false;
            while (rs.next()) {
                RestaurantrestId = rs.getInt("RestaurantrestId");
                if (RestaurantrestId == restId && RestaurantrestId > 0) {
                    hasRestaurantRecord = true;
                }
            }
            if (hasRestaurantRecord) {
                System.out.println("update");
                preQueryStatement = "UPDATE RestViewCount SET count = count + 1 WHERE RestaurantrestId = ?;";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, restId);
            } else {
                System.out.println("insert");
                preQueryStatement = "INSERT INTO RestViewCount (RestaurantrestId, userId, date, district, count) VALUES (?, ?, ?, ?, ?);";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, restId);
                pStmnt.setInt(2, userId);
                pStmnt.setTimestamp(3, new java.sql.Timestamp(new java.util.Date().getTime()));
                pStmnt.setString(4, district);
                pStmnt.setInt(5, 0);
            }
            pStmnt.executeUpdate();
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int ViewCount(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int viewCount = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM RestViewCount WHERE RestaurantrestId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                int RestaurantrestId = rs.getInt("RestaurantrestId");
                if(RestaurantrestId == restId){
                    viewCount = rs.getInt("count");
                }
            }
            return viewCount;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
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
