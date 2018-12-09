/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.AccountRole;
import ict.bean.Comment;
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
public class AccountRoleDB {

    private String dburl = "";
    private String dbUser = "";
    private String dbPassword = "";

    public AccountRoleDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException, IOException {
        System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
        return (Connection) DriverManager.getConnection(dburl, dbUser, dbPassword);
    }

    public ArrayList<AccountRole> getAllRole() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<AccountRole> roles = new ArrayList();
        AccountRole role = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM AccountRole";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                role = new AccountRole();
                role.setRoleName(rs.getString("roleName"));
                roles.add(role);
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return roles;
    }

    public AccountRole getRoleByName(String roleName) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        AccountRole role = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM AccountRole where roleName = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, roleName);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                role = new AccountRole();
                role.setRoleName(rs.getString("roleName"));
            }
            pStmnt.close();
            cnnct.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return role;
    }

    public boolean hasThisRoleName(String roleName) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean hasRecord = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM AccountRole where roleName =?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, roleName);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                hasRecord = true;
                break;
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return hasRecord;
    }

    public boolean updateRoleByName(String oldRoleName, String newRoleName) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean editSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "update AccountRole set roleName = ? WHERE roleName=?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, newRoleName);
            pStmnt.setString(2, oldRoleName);

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
