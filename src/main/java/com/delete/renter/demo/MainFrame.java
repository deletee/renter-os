package com.delete.renter.demo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class MainFrame {
    private Stage secondStage;
    private BorderPane borderPane;
    private Scene scene;

    public MainFrame(){
        secondStage = new Stage();
        borderPane = new BorderPane();
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        scene = new Scene(borderPane, screenWidth *0.8, screenHeight *0.8);
    }

    private void initMenu(){
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("文件");
        Menu menuSteel = new Menu("钢管管理");
        Menu menufastening = new Menu("扣件管理");
        Menu menuHelp = new Menu("帮助");

        //File 子菜单
        MenuItem saveMenuItem = new MenuItem("备份");
        MenuItem returnMenuItem = new MenuItem("还原");
        MenuItem exitMenuItem = new MenuItem("退出");
        menuFile.getItems().addAll(saveMenuItem,returnMenuItem,new SeparatorMenuItem(),exitMenuItem);
        exitMenuItem.setOnAction(event -> Platform.exit());

        MenuItem steelPipeRecordItem = new MenuItem("信息录入");
        MenuItem steelPipeLendItem = new MenuItem("出借明细");
        MenuItem steelPipeReturnItem = new MenuItem("归还明细");
        MenuItem steelPipeSettleItem = new MenuItem("钢管结算");
        menuSteel.getItems().addAll(steelPipeRecordItem,new SeparatorMenuItem(),steelPipeLendItem,steelPipeReturnItem,steelPipeSettleItem);

        MenuItem fasteningRecordItem = new MenuItem("信息录入");
        MenuItem fasteningLendItem = new MenuItem("出借明细");
        MenuItem fasteningReturnItem = new MenuItem("归还明细");
        MenuItem fasteningSettleItem = new MenuItem("扣件结算");
        menufastening.getItems().addAll(fasteningRecordItem,new SeparatorMenuItem(),fasteningLendItem,fasteningReturnItem,fasteningSettleItem);

        menuBar.getMenus().addAll(menuFile, menuSteel, menufastening, menuHelp);
        borderPane.setTop(menuBar);
    }

    @SuppressWarnings("unchecked")
    private void initContent(){

        BorderPane subBorderPane = new BorderPane();
        String title = "钢管管理 >> 钢管出借明细";
        Label label = new Label(title);
        label.setFont(new Font("Tahoma", 18));
        label.setAlignment(Pos.TOP_CENTER);
        label.setPadding(new Insets(15, (borderPane.getWidth()-30*title.length())/2, 15, (borderPane.getWidth()-30*title.length())/2));
        subBorderPane.setTop(label);
        String[] columnNames = {"序号","工地","规格","借数","小计米数","小计金额","出借日期","备注"};
        List tableColumnList = new ArrayList<TableColumn<Map, String>>();
        Callback<TableColumn<Map, String>, TableCell<Map, String>>
                cellFactoryForMap = (TableColumn<Map, String> p) ->
                new TextFieldTableCell(new StringConverter() {
                    @Override
                    public String toString(Object t) {
                        return t.toString();
                    }
                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                });

        for (String columnName:columnNames) {
            TableColumn<Map, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new MapValueFactory(columnName));
            column.setMinWidth(130);
            column.setCellFactory(cellFactoryForMap);
            tableColumnList.add(column);
        }

        TableView tableView = new TableView<>(generateDataInMap(columnNames));

        tableView.setEditable(false);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getColumns().setAll(tableColumnList);
        subBorderPane.setCenter(tableView);
        borderPane.setCenter(subBorderPane);
    }

    private ObservableList<Map> generateDataInMap(String[] columnNames) {
        int max = 100;
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            for (String columnName:columnNames) {
                dataRow.put(columnName, columnName+i);
            }
            allData.add(dataRow);
        }
        return allData;
    }

    public void show(){
        secondStage.setTitle("钢模扣件管理系统");
        initMenu();
        initContent();
        secondStage.setScene(scene);
        secondStage.show();
    }
}
