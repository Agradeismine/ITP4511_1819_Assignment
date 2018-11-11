/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;

/**
 *
 * @author YipYi
 */
public class Restaurant implements Serializable {

    private int restId;
    private String name;
    private String RestIcon;
    private String address;
    private String description;
    private int likeCount;
    private int dislikeCount;
    private String type;
    private int viewCount;

    public Restaurant() {
    }

    public Restaurant(int restId, String name, String RestIcon, String address, String description, int likeCount, int dislikeCount, String type, int viewCount) {
        this.restId=restId;
        this.name = name;
        this.RestIcon = RestIcon;
        this.address = address;
        this.description = description;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.type = type;
        this.viewCount = viewCount;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestIcon() {
        return RestIcon;
    }

    public void setRestIcon(String RestIcon) {
        this.RestIcon = RestIcon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
    
}
