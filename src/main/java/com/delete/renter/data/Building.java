package com.delete.renter.data;

import com.delete.renter.utils.DateUtil;

import java.io.Serializable;

public class Building implements Serializable,Comparable<Building>{
    private int id;
    private String buildName;
    private String owner;
    private String phone;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Building(int id, String buildName, String owner, String phone, String time) {
        this.id = id;
        this.buildName = buildName;
        this.owner = owner;
        this.phone = phone;
        this.time = time;
    }

    public Building(){

    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", buildName='" + buildName + '\'' +
                ", owner='" + owner + '\'' +
                ", phone='" + phone + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public int compareTo(Building o) {
        return DateUtil.dateSub(this.time,o.time);
    }
}
