/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import com.mysql.jdbc.Connection;
import ict.bean.Menu;
import ict.bean.Restaurant;
import ict.bean.UserInfo;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

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

    public int countLike(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int like = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT COUNT(*) as count FROM RestaurantComment WHERE Mood = 1 AND RestaurantrestId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                like = rs.getInt("count");
            }
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return like;
    }

    public int countDislike(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int dislike = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT COUNT(*) as count FROM RestaurantComment WHERE Mood = 0 AND RestaurantrestId = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                dislike = rs.getInt("count");
            }
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return dislike;
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
                restaurantBean.setTel(rs.getInt("tel"));
                restaurantBean.setViewCount(ViewCount(restaurantBean.getRestId()));
                restaurantBean.setLike(countLike(restaurantBean.getRestId()));
                restaurantBean.setDislike(countDislike(restaurantBean.getRestId()));
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
            String preQueryStatement = "SELECT DISTINCT r.restId, r.name, r.restIcon, r.address, r.description, r.tel, rt.tagName, rvc.count"
                    + " FROM Restaurant r, RestaurantTag rt, RestViewCount rvc"
                    + " WHERE r.restId = rt.RestaurantrestId"
                    + " GROUP BY r.restId;";
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
                restaurantBean.setTel(rs.getInt("tel"));
                restaurantBean.setViewCount(ViewCount(restaurantBean.getRestId()));
                restaurantBean.setLike(countLike(restaurantBean.getRestId()));
                restaurantBean.setDislike(countDislike(restaurantBean.getRestId()));
                if (restaurantBean.getName().toLowerCase().contains(name.toLowerCase())
                        || rs.getString("rt.tagName").contains(name.toLowerCase())) {
                    restaurantBeans.add(restaurantBean);
                    System.out.println(rs.getString("rt.tagName") + " : " + name);
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
        int checkUserId = 0;
        String district = "noDistrict";
        if (user != null) {
            userId = user.getUserID();
            district = user.getDistrict(); // for login
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
                checkUserId = rs.getInt("userId");
                if (RestaurantrestId == restId && user.getUserID() == checkUserId) {
                    hasRestaurantRecord = true;
                }
            }
            if (!hasRestaurantRecord) {
                preQueryStatement = "INSERT INTO RestViewCount (RestaurantrestId, userId, date, district, count) VALUES (?, ?, NOW(), ?, ?);";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, restId);
                pStmnt.setInt(2, userId);
                pStmnt.setString(3, district);
                pStmnt.setInt(4, 1);
            } else {
                preQueryStatement = "UPDATE RestViewCount SET count = count + 1 WHERE userId = ? AND RestaurantrestId = ?";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, userId);
                pStmnt.setInt(2, restId);
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
                if (RestaurantrestId == restId) {
                    viewCount += rs.getInt("count");
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
                restaurantBean.setTel(rs.getInt("tel"));
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
            rs = pStmnt.executeQuery();     //NOT -->rs = pStmnt.executeQuery(preQueryStatement);

            while (rs.next()) {
                restaurant = new Restaurant();
                restaurant.setRestId(rs.getInt("restId"));
                restaurant.setName(rs.getString("name"));
                restaurant.setOwnerId(rs.getInt("ownerId"));
                restaurant.setRestIcon(rs.getString("restIcon"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDescription(rs.getString("description"));
                restaurant.setViewCount(ViewCount(restaurant.getRestId()));
                restaurant.setTel(rs.getInt("tel"));
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

    public void addMyFavourite(int userId, int itemId, String type) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO UserFavourite (AccountuserId, favouriteId, favouriteType) VALUES (?, ?, ?);";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);
            pStmnt.setInt(2, itemId);
            pStmnt.setString(3, type);
            if (!hasMyFavouriteRecord(userId, itemId, type)) {
                pStmnt.executeUpdate();
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public boolean hasMyFavouriteRecord(int userId, int itemId, String type) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean hasRecord = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM UserFavourite where AccountuserId =? and favouriteId = ? and favouriteType=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);
            pStmnt.setInt(2, itemId);
            pStmnt.setString(3, type);
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

    public void deleteFavouriteRecord(int userId, int itemId, String type) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "DELETE FROM UserFavourite WHERE AccountuserId = ? AND favouriteId = ? AND favouriteType = ?;";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);
            pStmnt.setInt(2, itemId);
            pStmnt.setString(3, type);
            pStmnt.executeUpdate();
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Restaurant> getMyFavouriteInRestaurant(int userId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Restaurant> myFavourites_restaurant = new ArrayList<Restaurant>();
        Restaurant restaurantBean;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT r.restId, r.name, r.ownerId, r.restIcon, r.address, r.description, r.tel "
                    + "FROM UserFavourite uf, Restaurant r "
                    + "WHERE uf.AccountuserId = ? "
                    + "AND uf.favouriteType = 'restaurant' "
                    + "AND r.restId = uf.favouriteId";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                restaurantBean = new Restaurant();
                restaurantBean.setRestId(rs.getInt("r.restId"));
                restaurantBean.setName(rs.getString("r.name"));
                restaurantBean.setOwnerId(rs.getInt("r.ownerId"));
                restaurantBean.setRestIcon(rs.getString("r.restIcon"));
                restaurantBean.setAddress(rs.getString("r.address"));
                restaurantBean.setDescription(rs.getString("r.description"));
                restaurantBean.setTel(rs.getInt("r.tel"));
                restaurantBean.setViewCount(ViewCount(restaurantBean.getRestId()));
                myFavourites_restaurant.add(restaurantBean);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return myFavourites_restaurant;
    }

    public ArrayList<Menu> getMyFavouriteInMenu(int userId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList<Menu> myFavourites_menu = new ArrayList<Menu>();
        Menu menu;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT m.RestaurantrestId, m.imgId, m.imgName, m.menuType, m.menuPath, m.menuStartTime, m.menuEndTime "
                    + "FROM UserFavourite uf, Menu m "
                    + "WHERE uf.AccountuserId = ? "
                    + "AND uf.favouriteType = 'menu' "
                    + "AND m.imgId = uf.favouriteId";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userId);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                menu = new Menu();
                menu.setRestId(rs.getInt("RestaurantrestId"));
                menu.setImgId(rs.getInt("imgId"));
                menu.setImgName(rs.getString("imgName"));
                menu.setMenuType(rs.getString("menuType"));
                menu.setMenuPath(rs.getString("menuPath"));
                menu.setMenuStartTime(rs.getDate("menuStartTime"));
                menu.setMenuEndTime(rs.getDate("menuEndTime"));
                myFavourites_menu.add(menu);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return myFavourites_menu;
    }

    public double avgByMonth(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String[] ymd;
        String date;
        int year, month, day;
        int sum = 0;
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM RestViewCount WHERE RestaurantrestId = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                date = rs.getString("date");
                ymd = date.split("-");
                year = Integer.parseInt(ymd[0]);
                month = Integer.parseInt(ymd[1]);
                day = Integer.parseInt(ymd[2]);
                if (year == thisYear) {
                    sum += rs.getInt("count");
                }
            }
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sum / 12.0;
    }

    public ArrayList[] avgByDistrict(int restId) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        ArrayList districtList = new ArrayList();
        ArrayList countList = new ArrayList();
        ArrayList[] list = new ArrayList[2]; //0 - districtList . 1 - countList
        int sum = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM RestViewCount WHERE RestaurantrestId = ? GROUP BY district";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, restId);
            ResultSet rs;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                districtList.add(rs.getString("district"));
                countList.add(rs.getInt("count"));
            }
            list[0] = districtList;
            list[1] = countList;
            cnnct.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
