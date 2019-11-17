package com.delete.renter.dao;


import com.delete.renter.constant.RenterType;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.utils.DateUtil;
import com.delete.renter.utils.NormCalc;
import org.junit.Test;

import java.math.BigDecimal;

public class DataHelperTest {

    @Test
    public void insertNewSteelRecord() {
        SteelRenter steelRenter = new SteelRenter();
        steelRenter.setBuildName("E工地");
        steelRenter.setOwner("席晶晶");
        steelRenter.setTime(DateUtil.LongToString(System.currentTimeMillis(),DateUtil.LONG_DATE_FORMAT));
        steelRenter.setNorms("5*9;2*9");
        steelRenter.setNum(NormCalc.calcNum("5*9;2*9"));
        steelRenter.setRenterType(RenterType.RENTER.name());
        steelRenter.setUnitPrice(0.9332223f);
        DataHelper.insertNewSteelRecord(steelRenter);
    }

    @Test
    public void querySteelRecord(){
        for (SteelRenter steelRenter: DataHelper.querySteelRecord()) {
            System.out.println(steelRenter.toString());
        }
    }

    @Test
    public void dropTable(){
        DBHandler.getInstance().dropTable();
        DBHandler.getInstance().initDB();
    }
}