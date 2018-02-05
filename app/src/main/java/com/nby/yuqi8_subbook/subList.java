package com.nby.yuqi8_subbook;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yu Zhang on 2018-02-03.
 */

// This is the listview item object
// an object stores all info of item
public class subList {
    private String name;
    private Date date;
    private double charge = 0;
    private String comment;

    public subList(){
        this.name = new String("name here");
        this.date = new Date();
        this.charge = 0;
        this.comment = new String("comment here");
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setCharge(double charge) {
        this.charge = charge;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getName() {
        return name;
    }
    public Date getDate() {
        return date;
    }
    public double getcharge() {
        return charge;
    }
    public String getComment() {
        return comment;
    }

    @Override
    // This function is to show correct title of item on main user page
    public String toString(){
        SimpleDateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
        return "subscription: " + name + "\n" + " charge: " + Double.toString(charge) + "\n" + dateFormate.format(date);
    }
}
