package com.delete.renter.data;

import com.delete.renter.utils.DateUtil;

import java.io.Serializable;

public class FasteningSettle implements Serializable,Comparable<FasteningSettle>{
    private int id;
    private String buildName;
    private String owner;
    private String time;
    private String norms;
    private String renterType;
    private float unitPrice;
    private int days;
    private float num;
    private float accNum;
    private float price;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAccNum() {
        return accNum;
    }

    public void setAccNum(float accNum) {
        this.accNum = accNum;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

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

    public String getNorms() {
        return norms;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNorms(String norms) {
        this.norms = norms;
    }

    public String getRenterType() {
        return renterType;
    }

    public void setRenterType(String renterType) {
        this.renterType = renterType;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public FasteningSettle(int id, String buildName, String owner, String time, String norms, String renterType, float unitPrice) {
        this.id = id;
        this.buildName = buildName;
        this.owner = owner;
        this.time = time;
        this.norms = norms;
        this.renterType = renterType;
        this.unitPrice = unitPrice;
    }

    public FasteningSettle(){

    }

    @Override
    public String toString() {
        return "SteelRenter{" +
                "id=" + id +
                ", buildName='" + buildName + '\'' +
                ", owner='" + owner + '\'' +
                ", time='" + time + '\'' +
                ", norms='" + norms + '\'' +
                ", renterType='" + renterType + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Override
    public int compareTo(FasteningSettle o) {
        return DateUtil.dateSub(this.time,o.time);
    }
}
