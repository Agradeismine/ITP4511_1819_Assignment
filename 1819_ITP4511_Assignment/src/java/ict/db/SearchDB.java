/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.UserInfo;
import ict.bean.UserInfo;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author arthurking
 */
public class SearchDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public SearchDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public void saveSearchRecord(int userId, String keywords, String district) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        if (userId == 0) {
            district = "noDistrict";
        }
        try {
            cnnct = getConnection();
            String preQueryStatement;
            if (!hasSearchRecord(userId, keywords, district)) {
                System.out.println("insert");
                preQueryStatement = "INSERT INTO SearchHistory VALUES (?, 1, NOW(), ?)";
            } else {
                System.out.println("update");
                preQueryStatement = "UPDATE SearchHistory SET count = count + 1 WHERE keyword = ? AND district = ?";
            }
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, keywords);
            pStmnt.setString(2, district);
            pStmnt.executeUpdate();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasSearchRecord(int userId, String keywords, String district) {
        boolean hasRecord = false;
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int count = 0;
        if (userId == 0) {
            district = "noDistrict";
        }
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT COUNT(*) AS count FROM SearchHistory WHERE keyword = ? AND district = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, keywords);
            pStmnt.setString(2, district);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
                System.out.println("count: " + count);
                if (count >= 1) {
                    hasRecord = true;
                }
            }
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return hasRecord;
    }

    public ArrayList[] popKeywords() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList[] popSearch = new ArrayList[2];
        ArrayList keywords = new ArrayList();
        ArrayList count = new ArrayList();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM SearchHistory;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                keywords.add(rs.getString("keyword"));
                count.add(rs.getInt("count"));
            }
            cnnct.close();
            popSearch[0] = keywords;
            popSearch[1] = count;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return popSearch;
    }
}
