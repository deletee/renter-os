package com.delete.renter.control;

import com.delete.renter.data.SteelRenter;
import com.delete.renter.ui.NewFasteningRecordFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FasteningViewControler implements Initializable {
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<SteelRenter> fasteningTableView;

    public void onFasteningAdd(){
        new NewFasteningRecordFrame().show();
    }

    public void onFasteningSettle(){
        new NewFasteningRecordFrame().show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
    }

    public void queryFasteningRecordByBuilding(){

    }

    public void onFasteningRefresh(){

    }
}
