package com.delete.renter.control;


import io.datafx.controller.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@ViewController(value = "/fxml/newFasteningRecord.fxml", title = "Material Design Example")
public class NewFasteningRecordControler implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton returnType;

    @FXML
    private RadioButton renterType;

    @FXML
    private TextField unitprice;

    @FXML
    private Label x;

    @FXML
    private ComboBox<String> norms;

    @FXML
    private TextField curNorms;

    @FXML
    private TextField number;

    @FXML
    private TextField unitPrice;

    @FXML
    private TextField curUnitPrice;

    @FXML
    private HBox priceHbox;

    @FXML
    private Button fasteningCancel;

    @FXML
    private Label msg1;

    private final ToggleGroup group = new ToggleGroup();

    public void onFasteningAddSave(){

    }
    
    public void onFasteningAddCancel(){
        Stage stage = (Stage)fasteningCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onReturn(){
        x.setVisible(false);
        unitprice.setVisible(false);
        msg1.setVisible(false);
    }

    @FXML
    public void onRenter(){
        x.setVisible(true);
        unitprice.setVisible(true);
        msg1.setVisible(false);
    }

    public void initNorms(){
        ObservableList<String> tmpList = FXCollections.observableArrayList();
        tmpList.add("扣件1");
        tmpList.add("扣件2");
        tmpList.add("扣件3");
        norms.setItems(tmpList);
        norms.setValue("请选择规格");
    }

    public void onAddNorm(){
        String srcText = curNorms.getText();
        if(StringUtils.isEmpty(number.getText()) || StringUtils.isEmpty(norms.getValue())){
            msg1.setVisible(true);
            return;
        }
        msg1.setVisible(false);
        String normsStr = norms.getValue() + "*" + number.getText();
        if (StringUtils.isEmpty(srcText)){
            curNorms.setText(normsStr);
        }else{
            curNorms.setText(srcText+";"+normsStr);
        }
    }

    public void onClearNorm(){
        curNorms.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        returnType.setToggleGroup(group);
        renterType.setToggleGroup(group);
        initNorms();
        msg1.setVisible(false);
    }
}
