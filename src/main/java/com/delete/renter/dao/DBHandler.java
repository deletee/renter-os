package com.delete.renter.dao;

import com.delete.renter.control.MainFrameControler;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用于存储DB
 */
public class DBHandler {
    private static DBHandler handler = null;

    private static final String DB_URL = "jdbc:derby:renter;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    private DBHandler(){
        createConnection();
        initDB();
    }

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        }
        catch (Exception e) {
//            MainFrameControler.getEventModel().onAction("数据库读取失败！");
            System.exit(0);
        }
    }

    public void reInitDB(){
        executeSQL("truncate table steel");
        executeSQL("truncate table fastening");
    }

    public void dropTable(){
//        executeSQL("drop table steel");
        executeSQL("drop table fastening");
    }

    public void dropTable(String tableName){
//        executeSQL("drop table steel");
        executeSQL("drop table " + tableName);
        System.out.println(tableName + " was dropped");
    }

    public void initDB() {
        List<String> tableData = new ArrayList<String>();
        try {
            Set<String> loadedTables = getDBTables(null);
            System.out.println("Already loaded tables " + loadedTables);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream inputStream = getClass().getResourceAsStream("/database/tables.xml");
            Document doc = dBuilder.parse(inputStream);
            NodeList nList = doc.getElementsByTagName("table-entry");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element entry = (Element) nNode;
                String tableName = entry.getAttribute("name");
                String query = entry.getAttribute("col-data");
                if (!loadedTables.contains(tableName.toLowerCase())) {
                    tableData.add(String.format("CREATE TABLE %s (%s)", tableName, query));
                }
            }
            if (tableData.isEmpty()) {
                System.out.println("Tables are already loaded");
            }
            else {
                System.out.println("Init new tables.");
                createTables(tableData);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBHandler getInstance() {
        if (handler == null) {
            handler = new DBHandler();
        }
        return handler;
    }

    public Connection getConnection() {
        return conn;
    }

    public ResultSet executeSQL(String sql){
        if (StringUtils.isEmpty(sql)) return null;
        if (stmt == null){
            try {
                stmt = conn.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ResultSet rs = null;
        try {
            if (stmt.execute(sql)){
                rs = stmt.getResultSet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public Set<String> getDBTables(String schema) throws SQLException {
        Set<String> set = new HashSet<String>();
        DatabaseMetaData dbmeta = conn.getMetaData();
        readDBTable(set, dbmeta, schema);
        return set;
    }

    private void readDBTable(Set<String> set, DatabaseMetaData dbmeta, String schema) throws SQLException {
        ResultSet rs = dbmeta.getTables(null, schema, null, new String[]{"TABLE"});
        while (rs.next()) {
            set.add(rs.getString("TABLE_NAME").toLowerCase());
        }
    }

    private void createTables(List<String> tableData) throws SQLException {
        Statement statement = conn.createStatement();
        statement.closeOnCompletion();
        for (String command : tableData) {
            System.out.println(command);
            statement.addBatch(command);
        }
        statement.executeBatch();
    }
}
