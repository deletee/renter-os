package com.delete.renter.data;

import com.delete.renter.utils.DateUtil;

import java.io.Serializable;

public class FasteningNorm implements Serializable,Comparable<FasteningNorm>{
    private int id;
    private String name;
    private String description;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public FasteningNorm(int id, String name, String description, String time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.time = time;
    }

    public FasteningNorm(){

    }

    @Override
    public String toString() {
        return "FarsteningNorm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public int compareTo(FasteningNorm o) {
        return DateUtil.dateSub(this.time,o.time);
    }
}
