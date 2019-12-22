package com.delete.renter.control;

import com.delete.renter.constant.RenterType;
import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.EventModel;
import com.delete.renter.data.FasteningRenter;
import com.delete.renter.data.FasteningSettle;
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

@ViewController(value = "/fxml/fasteningSettle.fxml", title = "Material Design Example")
public class FasteningSettleControler implements Initializable {

    private static EventModel eventModel;

    public static EventModel getEventModel() {
        return eventModel;
    }

    public static void setEventModel(EventModel eventModel) {
        FasteningSettleControler.eventModel = eventModel;
    }

    private ObservableList<FasteningSettle> fasteningSettleList = FXCollections.observableArrayList();

    private ObservableList<FasteningSettle> setFasteningRenterList(List<FasteningRenter> fasteningRenterList) {
        Collections.sort(fasteningRenterList);
        ObservableList<FasteningSettle> fasteningSettleObservableList = FXCollections.observableArrayList();

        if (fasteningRenterList.size() == 0) return fasteningSettleObservableList;
        float accNum = 0.0f;
        float currentUnitPrice = 0.0f;
        float money = 0.0f;

        Set<String> dates = new TreeSet<>();
        for (FasteningRenter fasteningRenter:fasteningRenterList) {
            dates.add(fasteningRenter.getTime());
        }

        String buildName = fasteningRenterList.get(0).getBuildName();
        for (int i = 0; i < fasteningRenterList.size(); i++) {
            FasteningRenter fasteningRenter = fasteningRenterList.get(i);
            FasteningSettle fasteningSettle = new FasteningSettle();
            fasteningSettle.setId(fasteningRenter.getId());
            fasteningSettle.setBuildName(fasteningRenter.getBuildName());
            fasteningSettle.setNum(fasteningRenter.getNum());
            fasteningSettle.setOwner(fasteningRenter.getOwner());
            fasteningSettle.setTime(fasteningRenter.getTime());
            fasteningSettle.setUnitPrice(fasteningRenter.getUnitPrice());
            fasteningSettle.setRenterType(fasteningRenter.getRenterType());
            fasteningSettleObservableList.add(fasteningSettle);

            if (fasteningRenter.getRenterType().equals(RenterType.RENTER.getDesc())){
                currentUnitPrice = fasteningRenter.getUnitPrice();
            }
            int days = DateUtil.dateSub(nextDate(dates,fasteningRenter.getTime()),fasteningRenter.getTime());
            accNum = accNum + getPositiveOrNegative(fasteningRenter.getRenterType()) * fasteningRenter.getNum();
            fasteningSettle.setAccNum(accNum);

            if (i < fasteningRenterList.size()-1){
                FasteningRenter nextFasteningRenter = fasteningRenterList.get(i+1);
                if (!fasteningRenter.getTime().equals(nextFasteningRenter.getTime())){
                    money += accNum*currentUnitPrice*days;
                    fasteningSettle.setPrice(accNum*currentUnitPrice*days);
                }
            }else{
                days = 0;
                money += accNum*currentUnitPrice*days;
                fasteningSettle.setPrice(accNum*currentUnitPrice*days);
            }
            fasteningSettle.setDays(days);
        }
        settleTile.setText(String.format("正在进行%s结算，共%d条借还记录，共计金额:%.2f",buildName,fasteningRenterList.size(),money));
        return fasteningSettleObservableList;
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
    private TableView<FasteningSettle> fasteningSettleTableView;

    @FXML
    private TableColumn<FasteningSettle, Integer> id;

    @FXML
    private TableColumn<FasteningSettle, String> buildName;

    @FXML
    private TableColumn<FasteningSettle, String> owner;

    @FXML
    private TableColumn<FasteningSettle, String> renterType;

    @FXML
    private TableColumn<FasteningSettle, Float> num;

    @FXML
    private TableColumn<FasteningSettle, Float> accNum;

    @FXML
    private TableColumn<FasteningSettle, Float> unitPrice;

    @FXML
    private TableColumn<FasteningSettle, String> time;

    @FXML
    private TableColumn<FasteningSettle, Integer> days;

    @FXML
    private TableColumn<FasteningSettle, Float> price;

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
        fasteningSettleList.clear();
        fasteningSettleList.addAll(setFasteningRenterList(DataHelper.queryFasteningRecordByBuilding(newText)));
        fasteningSettleTableView.setItems(fasteningSettleList);
    }
}
