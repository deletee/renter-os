package com.delete.renter.control;

import com.delete.renter.constant.RenterType;
import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.EventModel;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.data.SteelSettle;
import com.delete.renter.utils.DateUtil;
import io.datafx.controller.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

@ViewController(value = "/fxml/steelSettle.fxml", title = "Material Design Example")
public class SteelSettleControler implements Initializable {

    private static EventModel eventModel;

    public static EventModel getEventModel() {
        return eventModel;
    }

    public static void setEventModel(EventModel eventModel) {
        SteelSettleControler.eventModel = eventModel;
    }

    private ObservableList<SteelSettle> steelSettleList = FXCollections.observableArrayList();

    private ObservableList<SteelSettle> setSteelRenterList(List<SteelRenter> steelRenterList) {
        Collections.sort(steelRenterList);
        ObservableList<SteelSettle> steelSettleObservableList = FXCollections.observableArrayList();

        if (steelRenterList.size() == 0) return steelSettleObservableList;
        float accNum = 0.0f;
        float currentUnitPrice = 0.0f;
        float money = 0.0f;

        Set<String> dates = new TreeSet<>();
        for (SteelRenter steelRenter:steelRenterList) {
            dates.add(steelRenter.getTime());
        }

        String buildName = steelRenterList.get(0).getBuildName();
        for (int i = 0; i < steelRenterList.size(); i++) {
            SteelRenter steelRenter = steelRenterList.get(i);
            SteelSettle steelSettle = new SteelSettle();
            steelSettle.setId(steelRenter.getId());
            steelSettle.setBuildName(steelRenter.getBuildName());
            steelSettle.setNum(steelRenter.getNum());
            steelSettle.setOwner(steelRenter.getOwner());
            steelSettle.setTime(steelRenter.getTime());
            steelSettle.setUnitPrice(steelRenter.getUnitPrice());
            steelSettle.setRenterType(steelRenter.getRenterType());
            steelSettleObservableList.add(steelSettle);

            if (steelRenter.getRenterType().equals(RenterType.RENTER.getDesc())){
                currentUnitPrice = steelRenter.getUnitPrice();
            }
            int days = DateUtil.dateSub(nextDate(dates,steelRenter.getTime()),steelRenter.getTime());
            accNum = accNum + getPositiveOrNegative(steelRenter.getRenterType()) * steelRenter.getNum();
            steelSettle.setAccNum(accNum);

            if (i < steelRenterList.size()-1){
                SteelRenter nextSteelRenter = steelRenterList.get(i+1);
                if (!steelRenter.getTime().equals(nextSteelRenter.getTime())){
                    money += accNum*currentUnitPrice*days;
                    steelSettle.setPrice(accNum*currentUnitPrice*days);
                }
            }else{
                days = 0;
                money += accNum*currentUnitPrice*days;
                steelSettle.setPrice(accNum*currentUnitPrice*days);
            }
            steelSettle.setDays(days);
        }
        settleTile.setText(String.format("正在进行%s结算，共%d条借还记录，共计金额:%.2f",buildName,steelRenterList.size(),money));
        return steelSettleObservableList;
    }

    private String nextDate(Set<String> dates, String curDate){
        Iterator<String> iterator = dates.iterator();
        String lastDate = null;
        while (iterator.hasNext()) {
            lastDate = iterator.next();
            if (curDate.equals(lastDate) && iterator.hasNext()){
                lastDate = iterator.next();
                return lastDate;
            }
        }
        return lastDate;
    }

    private int getPositiveOrNegative(String renterType){
        if (renterType.equals(RenterType.RENTER.getDesc())){
            return 1;
        }
        return -1;
    }

    @FXML
    private TableView<SteelSettle> steelSettleTableView;

    @FXML
    private TableColumn<SteelSettle, Integer> id;

    @FXML
    private TableColumn<SteelSettle, String> buildName;

    @FXML
    private TableColumn<SteelSettle, String> owner;

    @FXML
    private TableColumn<SteelSettle, String> renterType;

    @FXML
    private TableColumn<SteelSettle, Float> num;

    @FXML
    private TableColumn<SteelSettle, Float> accNum;

    @FXML
    private TableColumn<SteelSettle, Float> unitPrice;

    @FXML
    private TableColumn<SteelSettle, String> time;

    @FXML
    private TableColumn<SteelSettle, Integer> days;

    @FXML
    private TableColumn<SteelSettle, Float> price;

    @FXML
    private Label settleTile;

    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        buildName.setCellValueFactory(new PropertyValueFactory<>("buildName"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        renterType.setCellValueFactory(new PropertyValueFactory<>("renterType"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
        accNum.setCellValueFactory(new PropertyValueFactory<>("accNum"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        days.setCellValueFactory(new PropertyValueFactory<>("days"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventModel = new EventModel();
        eventModel.textProperty().addListener((obs, oldText, newText) -> initData(newText));
        initCol();
    }

    private void initData(String newText){
        steelSettleList.clear();
        steelSettleList.addAll(setSteelRenterList(DataHelper.querySteelRecordByBuilding(newText)));
        steelSettleTableView.setItems(steelSettleList);
    }
}
