/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Restaurant;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                restaurantBean.setRestIcon(rs.getString("restIcon"));
                restaurantBean.setAddress(rs.getString("address"));
                restaurantBean.setDescription(rs.getString("description"));
                restaurantBean.setViewCount(rs.getInt("viewCount"));
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
    
        public ArrayList<Restaurant> queryRestById(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Restaurant> restaurantBeans = new ArrayList();
        Restaurant restaurantBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Restaurant where ownerId=?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurantBean = new Restaurant();
                restaurantBean.setRestId(rs.getInt("restId"));
                restaurantBean.setName(rs.getString("name"));
                restaurantBean.setRestIcon(rs.getString("restIcon"));
                restaurantBean.setAddress(rs.getString("address"));
                restaurantBean.setDescription(rs.getString("description"));
                restaurantBean.setViewCount(rs.getInt("viewCount"));
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

}
