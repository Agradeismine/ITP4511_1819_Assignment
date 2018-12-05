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
    private int ownerId;
    private String RestIcon;
    private String address;
    private String description;
    private int viewCount;
    private String HeadorBranches;
    private int headRestId;

    public Restaurant() {
    }

    public Restaurant(int restId, String name, int ownerId, String RestIcon, String address, String description, String HeadorBranches, int headRestId) {   //with ownerId
        this.restId = restId;
        this.name = name;
        this.ownerId = ownerId;
        this.RestIcon = RestIcon;
        this.address = address;
        this.description = description;
        this.HeadorBranches = HeadorBranches;
        this.headRestId = headRestId;
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

    public String getHeadorBranches() {
        return HeadorBranches;
    }

    public void setHeadorBranches(String HeadorBranches) {
        this.HeadorBranches = HeadorBranches;
    }

    public int getHeadRestId() {
        return headRestId;
    }

    public void setHeadRestId(int headRestId) {
        this.headRestId = headRestId;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getViewCount() {
        return viewCount;
    }

}
