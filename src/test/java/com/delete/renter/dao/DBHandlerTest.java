package com.delete.renter.dao;


import org.junit.Test;
import java.sql.SQLException;

public class DBHandlerTest {
    @Test
    public void testDB() throws SQLException {
//        System.out.println(DBHandler.getInstance().executeSQL("select * from renter.steel"));
        System.out.println(DBHandler.getInstance().getDBTables(null));
    }
}
