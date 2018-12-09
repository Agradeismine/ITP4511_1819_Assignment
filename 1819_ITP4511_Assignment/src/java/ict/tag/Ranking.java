/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.tag;

import ict.bean.Restaurant;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author arthurking
 */
public class Ranking extends SimpleTagSupport {

    private ArrayList<Restaurant> restaurants;

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            Restaurant rest;
            Collections.sort(restaurants);
            String[] color = {"red", "orange", "yellow", "black", "black"};
            for (int i = 0; i < 5; i++) {
                rest = restaurants.get(i);
                out.print("<div style='margin: 5px; color:" + color[i] + "; text-shadow: 2px 2px 5px grey;'>No. " + (i + 1) + " <a href='handleRestaurant?action=view&restId=" + rest.getRestId() + "'>"
                        + rest.getName() + "</a><b style='float: right;margin-right: 20px;'>Views: " + rest.getViewCount() + "</b>"
                        + "<b style='color:green; float: right; margin-right: 20px;'>Like: " + rest.getLike() + "</b><br/></div>");
            }
        } catch (IOException e) {
        } catch (Exception e) {
        }
    }
}
