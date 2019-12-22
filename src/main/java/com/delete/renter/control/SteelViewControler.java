package com.delete.renter.control;

import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.DialogBuilder;
import com.delete.renter.data.EventModel;
import com.delete.renter.data.FasteningRenter;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.ui.NewSteelRecordFrame;
import com.delete.renter.ui.SteelSettleFrame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class SteelViewControler implements Initializable {

    private ObservableList<SteelRenter> steelRenterList = FXCollections.observableArrayList();

    private ObservableSet<String> buildList = FXCollections.observableSet();

    private static EventModel eventModel;

    public static EventModel getEventModel() {
        return eventModel;
    }

    @FXML
    private ComboBox<String> buildings;

    @FXML
    private TableView<SteelRenter> steelTableView;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<SteelRenter, Integer> id;

    @FXML
    private TableColumn<SteelRenter, String> buildName;

    @FXML
    private TableColumn<SteelRenter, String> owner;

    @FXML
    private TableColumn<SteelRenter, String> renterType;

    @FXML
    private TableColumn<SteelRenter, String> norms;

    @FXML
    private TableColumn<SteelRenter, Float> num;

    @FXML
    private TableColumn<SteelRenter, Float> unitPrice;

    @FXML
    private TableColumn<SteelRenter, String> time;

    private SteelSettleFrame steelSettleFrame;

    @FXML
    private Button steelSettle;

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

    private void initBuildings(){
        buildList.clear();
        for (SteelRenter steelRenter:steelRenterList) {
            buildList.add(steelRenter.getBuildName());
        }
        ObservableList<String> tmpList = FXCollections.observableArrayList();
        tmpList.add("全部");
        tmpList.addAll(buildList);
        buildings.setItems(tmpList);
        buildings.setValue("全部");
    }

    private void loadData() {
        steelRenterList.clear();
        steelRenterList.addAll(DataHelper.querySteelRecord());
        steelTableView.setItems(steelRenterList);
    }

    private void loadData(String buildName) {
        steelRenterList.clear();
        steelRenterList.addAll(DataHelper.querySteelRecordByBuilding(buildName));
        steelTableView.setItems(steelRenterList);
    }
    
    public void onSteelAdd(){
        new NewSteelRecordFrame().show();
    }

    public void onSteelSettle(){
        if (buildings.getValue().equals("全部")){
            DialogBuilder dialogBuilder = new DialogBuilder(steelSettle).setTitle("提示")
                    .setMessage("无法对所有记录进行计算，请选择指定工地进行结算！")
                    .setNegativeBtn("确定");
            dialogBuilder.create();
        }else{
            ObservableList<SteelRenter> steelRenterForSettleList = FXCollections.observableArrayList();
            steelRenterForSettleList.addAll(steelRenterList);
            steelRenterForSettleList.removeIf(steelRenter -> steelRenter.getBuildName().equals(buildings.getValue()));
            steelSettleFrame.show();
            SteelSettleControler.getEventModel().onAction(buildings.getValue());
        }
    }

    public void onSteelRefresh(){
        init();
    }

    public void onSteelDelete(){
        TableView.TableViewSelectionModel<SteelRenter> steelRenterTableViewFocusModel =  steelTableView.getSelectionModel();
        if (steelRenterTableViewFocusModel.getSelectedItem() == null){
            DialogBuilder dialogBuilder = new DialogBuilder(steelSettle).setTitle("提示")
                    .setMessage("请选择一条记录删除！")
                    .setNegativeBtn("确定");
            dialogBuilder.create();
        }else{
            DialogBuilder dialogBuilder = new DialogBuilder(steelSettle).setTitle("提示")
                    .setMessage("确认删除?(N/Y)")
                    .setNegativeBtn("取消(N)")
                    .setPositiveBtn("确认(Y)", () -> {
                        SteelRenter steelRenter = steelRenterTableViewFocusModel.getSelectedItem();
                        DataHelper.deleteSteelRecordById(steelRenter.getId());
                        init();
                    });
            dialogBuilder.create();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        datePicker.setValue(LocalDate.now());
        steelSettleFrame = new SteelSettleFrame();
        eventModel = new EventModel();
        eventModel.textProperty().addListener((obs, oldText, newText) -> loadData());
    }

    public void querySteelRecordByBuilding(){
        if (buildings == null || buildings.getValue().equals("全部")){
            loadData();
        }else{
            loadData(buildings.getValue());
        }
    }
}
