package com.delete.renter.dao;

import com.delete.renter.constant.RenterType;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.utils.NormCalc;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    public static boolean insertNewSteelRecord(SteelRenter steelRenter) {
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
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static List<SteelRenter> querySteelRecord() {
        String sql = "select * from steel";
        return querySteelRecord(sql);
    }

    public static List<SteelRenter> querySteelRecordByBuilding(String buildName) {
        String sql = "select * from steel where buildName = '" + buildName + "'";
        return querySteelRecord(sql);
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
                Float num = rs.getFloat("num");
                if (!StringUtils.isEmpty(norms) && num < 0.00001f){
                    num = NormCalc.calcNum(norms);
                }
                Float unitPrice = rs.getFloat("unitPrice");
                SteelRenter steelRenter = new SteelRenter(id, building, owner, time, norms, RenterType.getDesc(renterType), num, unitPrice);
                steelRenterList.add(steelRenter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return steelRenterList;
    }
}
