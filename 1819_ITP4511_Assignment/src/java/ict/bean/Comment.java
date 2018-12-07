/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.bean;

import java.io.Serializable;

/**
 *
 * @author arthurking
 */
public class Comment implements Serializable{
    private int RestaurantrestId;
    private int AccountuserId;
    private boolean Mood;
    private String contents;
    private String title;
    private String mealDate;

    public Comment() {
    }

    public Comment(int RestaurantrestId, int AccountuserId, boolean Mood, String contents, String title, String mealDate) {
        this.RestaurantrestId = RestaurantrestId;
        this.AccountuserId = AccountuserId;
        this.Mood = Mood;
        this.contents = contents;
        this.title = title;
        this.mealDate = mealDate;
    }

    public int getRestaurantrestId() {
        return RestaurantrestId;
    }

    public void setRestaurantrestId(int RestaurantrestId) {
        this.RestaurantrestId = RestaurantrestId;
    }

    public int getAccountuserId() {
        return AccountuserId;
    }

    public void setAccountuserId(int AccountuserId) {
        this.AccountuserId = AccountuserId;
    }

    public boolean getMood() {
        return Mood;
    }

    public void setMood(boolean Mood) {
        this.Mood = Mood;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }
    
}
