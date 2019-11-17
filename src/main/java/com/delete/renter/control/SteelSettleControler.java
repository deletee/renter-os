package com.delete.renter.control;

import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.EventModel;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.data.SteelSettle;
import io.datafx.controller.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
        ObservableList<SteelSettle> steelSettleObservableList = FXCollections.observableArrayList();
        for (SteelRenter steelRenter:steelRenterList) {
            SteelSettle steelSettle = new SteelSettle();
            steelSettle.setId(steelRenter.getId());
            steelSettle.setBuildName(steelRenter.getBuildName());
            steelSettle.setNum(steelRenter.getNum());
            steelSettle.setOwner(steelRenter.getOwner());
            steelSettle.setTime(steelRenter.getTime());
            steelSettle.setUnitPrice(steelRenter.getUnitPrice());
            steelSettle.setRenterType(steelRenter.getRenterType());
            steelSettleObservableList.add(steelSettle);
        }
        return steelSettleObservableList;
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
    private TableColumn<SteelSettle, Float> unitPrice;

    @FXML
    private TableColumn<SteelSettle, String> time;

    @FXML
    private TableColumn<SteelSettle, Integer> days;

    @FXML
    private TableColumn<SteelSettle, Float> price;

    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        buildName.setCellValueFactory(new PropertyValueFactory<>("buildName"));
        owner.setCellValueFactory(new PropertyValueFactory<>("owner"));
        renterType.setCellValueFactory(new PropertyValueFactory<>("renterType"));
        num.setCellValueFactory(new PropertyValueFactory<>("num"));
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
