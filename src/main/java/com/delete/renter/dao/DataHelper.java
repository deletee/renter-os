package com.delete.renter.dao;

import com.delete.renter.constant.RenterType;
import com.delete.renter.data.Building;
import com.delete.renter.data.FasteningNorm;
import com.delete.renter.data.FasteningRenter;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.utils.NormCalc;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    public static void insertNewSteelRecord(SteelRenter steelRenter) {
        try {
            PreparedStatement statement = DBHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO STEEL(BUILDNAME,OWNER,TIME,NORMS,RENTERTYPE,NUM,UNITPRICE) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, steelRenter.getBuildName());
            statement.setString(2, steelRenter.getOwner());
            statement.setString(3, steelRenter.getTime());
            statement.setString(4, steelRenter.getNorms());
            statement.setString(5, steelRenter.getRenterType());
            statement.setFloat(6, steelRenter.getNum());
            statement.setFloat(7, NormCalc.setScale(steelRenter.getUnitPrice()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertNewFasteningRecord(FasteningRenter fasteningRenter) {
        try {
            PreparedStatement statement = DBHandler.getInstance().getConnection().prepareStatement(
                    "INSERT INTO FASTENING(BUILDNAME,OWNER,TIME,NORMS,NUM,RENTERTYPE,UNITPRICE) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, fasteningRenter.getBuildName());
            statement.setString(2, fasteningRenter.getOwner());
            statement.setString(3, fasteningRenter.getTime());
            statement.setString(4, fasteningRenter.getNorms());
            statement.setFloat(5, fasteningRenter.getNum());
            statement.setString(6, fasteningRenter.getRenterType());
            statement.setFloat(7, NormCalc.setScale(fasteningRenter.getUnitPrice()));
            statement.executeUpdate();
            System.out.println(String.format("table FASTENING has insert 1 record : {%s}", fasteningRenter));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<SteelRenter> querySteelRecord() {
        String sql = "select * from steel";
        return querySteelRecord(sql);
    }

    public static List<FasteningRenter> queryFasteningRecord() {
        String sql = "select * from fastening";
        return queryFasteningRecord(sql);
    }

    public static void deleteFasteningRecordById(int id) {
        String sql = String.format("delete from fastening where id = %d",id);
        DBHandler.getInstance().executeSQL(sql);
    }

    public static void deleteSteelRecordById(int id) {
        String sql = String.format("delete from steel where id = %d",id);
        DBHandler.getInstance().executeSQL(sql);
    }

    public static List<Building> queryBuildings() {
        String sql = "select * from building";
        return queryBuildings(sql);
    }

    public static List<FasteningNorm> queryFasteningNorm() {
        String sql = "select * from fastening_norm";
        return queryFasteningNorm(sql);
    }

    public static List<SteelRenter> querySteelRecordByBuilding(String buildName) {
        String sql = "select * from steel where buildName = '" + buildName + "'";
        return querySteelRecord(sql);
    }

    public static List<FasteningRenter> queryFasteningRecordByBuilding(String buildName) {
        String sql = "select * from fastening where buildName = '" + buildName + "'";
        return queryFasteningRecord(sql);
    }

    private static List<SteelRenter> querySteelRecord(String sql){
        List<SteelRenter> steelRenterList = new ArrayList<>();
        try {
            ResultSet rs  = DBHandler.getInstance().executeSQL(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String building = rs.getString("buildName");
                String owner = rs.getString("owner");
                String time = rs.getString("time");
                String renterType = rs.getString("renterType");
                String norms = rs.getString("norms");
                float num = rs.getFloat("num");
                if (!StringUtils.isEmpty(norms) && num < 0.00001f){
                    num = NormCalc.calcNum(norms);
                }
                float unitPrice = rs.getFloat("unitPrice");
                SteelRenter steelRenter = new SteelRenter(id, building, owner, time, norms, RenterType.getDesc(renterType), num, unitPrice);
                steelRenterList.add(steelRenter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return steelRenterList;
    }

    private static List<FasteningRenter> queryFasteningRecord(String sql){
        List<FasteningRenter> fasteningRenters = new ArrayList<>();
        try {
            ResultSet rs  = DBHandler.getInstance().executeSQL(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String building = rs.getString("buildName");
                String owner = rs.getString("owner");
                String time = rs.getString("time");
                String renterType = rs.getString("renterType");
                String norms = rs.getString("norms");
                float unitPrice = rs.getFloat("unitPrice");
                float num = rs.getFloat("num");
                FasteningRenter fasteningRenter = new FasteningRenter(id, building, owner, time, norms,num , RenterType.getDesc(renterType), unitPrice);
                fasteningRenters.add(fasteningRenter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fasteningRenters;
    }

    private static List<Building> queryBuildings(String sql){
        List<Building> buildings = new ArrayList<>();
        try {
            ResultSet rs  = DBHandler.getInstance().executeSQL(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String buildName = rs.getString("buildName");
                String owner = rs.getString("owner");
                String phone = rs.getString("phone");
                String time = rs.getString("time");
                Building building = new Building(id, buildName, owner,phone, time);
                buildings.add(building);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildings;
    }

    private static List<FasteningNorm> queryFasteningNorm(String sql){
        List<FasteningNorm> fasteningNorms = new ArrayList<>();
        try {
            ResultSet rs  = DBHandler.getInstance().executeSQL(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String owner = rs.getString("desc");
                String time = rs.getString("time");
                FasteningNorm fasteningNorm = new FasteningNorm(id, name, owner, time);
                fasteningNorms.add(fasteningNorm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fasteningNorms;
    }
}
