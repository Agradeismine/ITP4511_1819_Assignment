/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author YipYi
 */
public class Menu implements Serializable {

    private int restId;
    private int imgId;
    private String imgName;
    private String menuType;
    private String menuPath;
    private Date menuStartTime;
    private Date menuEndTime;

    public Menu() {
    }

    public Menu(int restId, int imgId, String imgName, String menuType, String menuPath, Date menuStartTime, Date menuEndTime) {
        this.restId = restId;
        this.imgId = imgId;
        this.imgName = imgName;
        this.menuType = menuType;
        this.menuPath = menuPath;
        this.menuStartTime = menuStartTime;
        this.menuEndTime = menuEndTime;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }

    public Date getMenuStartTime() {
        return menuStartTime;
    }

    public void setMenuStartTime(Date menuStartTime) {
        this.menuStartTime = menuStartTime;
    }

    public Date getMenuEndTime() {
        return menuEndTime;
    }

    public void setMenuEndTime(Date menuEndTime) {
        this.menuEndTime = menuEndTime;
    }
}
