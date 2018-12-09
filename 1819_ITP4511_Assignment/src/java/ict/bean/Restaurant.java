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
public class Restaurant implements Serializable, Comparable<Restaurant> {

    private int restId;
    private String name;
    private int ownerId;
    private String RestIcon;
    private String address;
    private String description;
    private int viewCount;
    private int tel;
    private int like;
    private int dislike;

    public Restaurant() {
    }

    public Restaurant(int restId, String name, int ownerId, String RestIcon, String address, String description, int viewCount, int tel, int like, int dislike) {
        this.restId = restId;
        this.name = name;
        this.ownerId = ownerId;
        this.RestIcon = RestIcon;
        this.address = address;
        this.description = description;
        this.viewCount = viewCount;
        this.tel = tel;
        this.like = like;
        this.dislike = dislike;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @Override
    public int compareTo(Restaurant comparee) {
        int comparer = ((Restaurant)comparee).getLike();
        return comparer - this.like;
    }



}
