package com.delete.renter.data;

import java.io.Serializable;

public class SteelSettle implements Serializable,Comparable{
    private int id;
    private String buildName;
    private String owner;
    private String time;
    private String renterType;
    private float num;
    private float accNum;
    private float unitPrice;
    private int days;
    private float price;

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

    public String getRenterType() {
        return renterType;
    }

    public void setRenterType(String renterType) {
        this.renterType = renterType;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
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

    @Override
    public String toString() {
        return "SteelSettle{" +
                "id=" + id +
                ", buildName='" + buildName + '\'' +
                ", owner='" + owner + '\'' +
                ", time='" + time + '\'' +
                ", renterType='" + renterType + '\'' +
                ", num=" + num +
                ", unitPrice=" + unitPrice +
                ", days=" + days +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
