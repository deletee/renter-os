package com.delete.renter.control;

import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.DialogBuilder;
import com.delete.renter.data.EventModel;
import com.delete.renter.data.FasteningRenter;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.ui.NewFasteningRecordFrame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FasteningViewControler implements Initializable {

    private ObservableList<FasteningRenter> fasteningRenters = FXCollections.observableArrayList();
    private ObservableSet<String> buildList = FXCollections.observableSet();

    private static EventModel eventModel;

    public static EventModel getEventModel() {
        return eventModel;
    }

    @FXML
    private ComboBox<String> buildings;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<FasteningRenter> fasteningTableView;

    @FXML
    private TableColumn<FasteningRenter, Integer> id;

    @FXML
    private TableColumn<FasteningRenter, String> buildName;

    @FXML
    private TableColumn<FasteningRenter, String> owner;

    @FXML
    private TableColumn<FasteningRenter, String> renterType;

    @FXML
    private TableColumn<FasteningRenter, String> norms;

    @FXML
    private TableColumn<FasteningRenter, Float> unitPrice;

    @FXML
    private TableColumn<FasteningRenter, String> time;

    @FXML
    private TableColumn<FasteningRenter, Float> num;

    public void onFasteningAdd(){
        new NewFasteningRecordFrame().show();
    }

    public void onFasteningSettle(){
        new NewFasteningRecordFrame().show();
    }

    public void init(){
        initCol();
        loadData();
        initBuildings();
    }

    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        buildName.setCellValueFactory(new PropertyValueFactory<>("buildName"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        renterType.setCellValueFactory(new PropertyValueFactory<>("renterType"));
        norms.setCellValueFactory(new PropertyValueFactory<>("norms"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    private void loadData() {
        fasteningRenters.clear();
        fasteningRenters.addAll(DataHelper.queryFasteningRecord());
        fasteningTableView.setItems(fasteningRenters);
    }

    private void loadData(String buildName) {
        fasteningRenters.clear();
        fasteningRenters.addAll(DataHelper.queryFasteningRecordByBuilding(buildName));
        fasteningTableView.setItems(fasteningRenters);
    }

    private void initBuildings(){
        buildList.clear();
        for (FasteningRenter fasteningRenter:fasteningRenters) {
            buildList.add(fasteningRenter.getBuildName());
        }
        ObservableList<String> tmpList = FXCollections.observableArrayList();
        tmpList.add("全部");
        tmpList.addAll(buildList);
        buildings.setItems(tmpList);
        buildings.setValue("全部");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        init();
        eventModel = new EventModel();
        eventModel.textProperty().addListener((obs, oldText, newText) -> loadData());
    }

    public void queryFasteningRecordByBuilding(){
        if (buildings.getValue().equals("全部")){
            loadData();
        }else{
            loadData(buildings.getValue());
        }
    }

    public void onFasteningRefresh(){
        init();
    }

    public void onFasteningDelete(){
        TableView.TableViewSelectionModel<FasteningRenter> fasteningRenterTableViewFocusModel =  fasteningTableView.getSelectionModel();
        FasteningRenter fasteningRenter = fasteningRenterTableViewFocusModel.getSelectedItem();
        if (fasteningRenter == null){
            DialogBuilder dialogBuilder = new DialogBuilder(fasteningTableView).setTitle("提示")
                    .setMessage("请选择一条记录删除！")
                    .setNegativeBtn("确定");
            dialogBuilder.create();
        }else{
            DialogBuilder dialogBuilder = new DialogBuilder(fasteningTableView).setTitle("提示")
                    .setMessage("确认删除?(N/Y)")
                    .setNegativeBtn("取消(N)")
                    .setPositiveBtn("确认(Y)", () -> {
                        DataHelper.deleteFasteningRecordById(fasteningRenter.getId());
                        onFasteningRefresh();
                        init();
                    });
            dialogBuilder.create();
        }
    }
}
