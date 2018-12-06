/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Menu;
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
public class MenuDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public MenuDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<Menu> getRestaurantMenuByRestId(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Menu> menuBeans = new ArrayList();

        Menu menu = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Menu where RestaurantrestId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                menu = new Menu();
                menu.setRestId(rs.getInt("RestaurantrestId"));
                menu.setImgId(rs.getInt("imgId"));
                menu.setImgName(rs.getString("imgName"));
                menu.setMenuType(rs.getString("menuType"));
                menu.setMenuPath(rs.getString("menuPath"));
                menu.setMenuStartTime(rs.getDate("menuStartTime"));
                menu.setMenuEndTime(rs.getDate("menuEndTime"));
                menuBeans.add(menu);
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return menuBeans;
    }

    public Menu getMenuByImgId(int imgId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        Menu menu = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from Menu WHERE imgId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, imgId);

            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                menu = new Menu();
                menu.setRestId(rs.getInt("RestaurantrestId"));
                menu.setImgId(rs.getInt("imgId"));
                menu.setImgName(rs.getString("imgName"));
                menu.setMenuType(rs.getString("menuType"));
                menu.setMenuPath(rs.getString("menuPath"));
                menu.setMenuStartTime(rs.getDate("menuStartTime"));
                menu.setMenuEndTime(rs.getDate("menuEndTime"));
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return menu;
    }

    public boolean updateMenuRecord(Menu menuNewInfo) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean editSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "update Menu set RestaurantrestId = ?, imgName =?, menuType =?, menuPath = ?, menuStartTime = ?, menuEndTime = ? WHERE imgId=?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, menuNewInfo.getRestId());
            pStmnt.setString(2, menuNewInfo.getImgName());
            pStmnt.setString(3, menuNewInfo.getMenuType());
            pStmnt.setString(4, menuNewInfo.getMenuPath());
            pStmnt.setDate(5, menuNewInfo.getMenuStartTime());
            pStmnt.setDate(6, menuNewInfo.getMenuEndTime());
            pStmnt.setInt(7, menuNewInfo.getImgId());
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
}
