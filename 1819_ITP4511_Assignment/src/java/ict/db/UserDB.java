package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.UserInfo;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author YipYi
 */
public class UserDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public UserDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public boolean isValidUser(int userId, String pwd) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isValid = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Account WHERE userId = ? and password = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);
            pStmnt.setString(2, pwd);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {    //return true if have this row
                isValid = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isValid;
    }

    public UserInfo queryCustByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        UserInfo userBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Account WHERE userId = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                userBean = new UserInfo();
                userBean.setUserID(Integer.parseInt(rs.getString("userId")));
                userBean.setUsername(rs.getString("username"));
                userBean.setPassword(rs.getString("password"));
                userBean.setRole(rs.getString("role"));
                userBean.setSex(rs.getString("sex"));
                userBean.setDistrict(rs.getString("district"));
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userBean;
    }

    public boolean addUserInfo(String id, String user, String pwd) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO Account (ID, USERNAME, PASSWORD) VALUES (?, ?, ?);";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, id);
            pStmnt.setString(2, user);
            pStmnt.setString(3, pwd);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }

    public ArrayList<UserInfo> getAllUserInfo() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<UserInfo> userBeans = new ArrayList();

        UserInfo user = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Account;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();    //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                user = new UserInfo();
                user.setUserID(Integer.parseInt(rs.getString("userId")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setSex(rs.getString("sex"));
                user.setDistrict(rs.getString("district"));
                userBeans.add(user);
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userBeans;
    }

    public boolean updateUser(UserInfo thisUser) {

        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean editSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "update Account set username = ?, password =?, role =?, sex = ?, district = ? WHERE userId=?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, thisUser.getUsername());
            pStmnt.setString(2, thisUser.getPassword());
            pStmnt.setString(3, thisUser.getRole());
            pStmnt.setString(4, thisUser.getSex());
            pStmnt.setString(5, thisUser.getDistrict());
            pStmnt.setInt(6, thisUser.getUserID());
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

    public boolean delAccountById(int userId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String preQueryStatement;
        boolean delSuccess = false;
        try {
            cnnct = getConnection();
            preQueryStatement = "DELETE FROM Account WHERE userId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);        //imgId
            int rowCount = pStmnt.executeUpdate();

            if (rowCount >= 1) {
                delSuccess = true;
            }
            pStmnt.close();
            cnnct.close();
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
