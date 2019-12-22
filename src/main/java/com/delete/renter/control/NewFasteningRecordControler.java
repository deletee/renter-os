package com.delete.renter.control;


import com.delete.renter.constant.RenterType;
import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.DialogBuilder;
import com.delete.renter.data.FasteningRenter;
import io.datafx.controller.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

@ViewController(value = "/fxml/newFasteningRecord.fxml", title = "Material Design Example")
public class NewFasteningRecordControler implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField buildName;

    @FXML
    private TextField owner;

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
    private Button fasteningSave;

    @FXML
    private Label msg1;

    private float num = 0.0f;

    private final ToggleGroup group = new ToggleGroup();

    public void onFasteningAddSave(){
        if (!check()){
            return;
        }
        FasteningRenter fasteningRenter = new FasteningRenter();
        fasteningRenter.setBuildName(buildName.getText());
        fasteningRenter.setOwner(owner.getText());
        fasteningRenter.setNorms(curNorms.getText());
        if (renterType.isSelected()){
            fasteningRenter.setRenterType(RenterType.RENTER.name());
            fasteningRenter.setUnitPrice(Float.parseFloat(unitPrice.getText()));
        }else{
            fasteningRenter.setRenterType(RenterType.RETURN.name());
            fasteningRenter.setUnitPrice(0.0f);
        }
        fasteningRenter.setTime(datePicker.getValue().toString());
        fasteningRenter.setNum(num);
        DataHelper.insertNewFasteningRecord(fasteningRenter);
        DialogBuilder dialogBuilder = new DialogBuilder(fasteningSave).setTitle("提示")
                .setMessage("保存成功，")
                .setHyperLink("点击查看",() -> {
                    Stage stage = (Stage)fasteningSave.getScene().getWindow();
                    stage.close();
                    FasteningViewControler.getEventModel().onAction(String.valueOf(System.currentTimeMillis()));
                })
                .setPositiveBtn("确定", () -> {
                    Stage stage = (Stage)fasteningSave.getScene().getWindow();
                    stage.close();
                    FasteningViewControler.getEventModel().onAction(String.valueOf(System.currentTimeMillis()));
                });
        dialogBuilder.create();
    }

    public void showMsg(Control control,String msg){
        DialogBuilder dialogBuilder = new DialogBuilder(control).setTitle("提示")
                .setMessage(msg)
                .setPositiveBtn("确定", () -> {
                });
        dialogBuilder.create();
    }

    public boolean check(){
        if(StringUtils.isEmpty(buildName.getText())){
            showMsg(fasteningSave,"工地名称不能为空!");
            return false;
        }

        if(StringUtils.isEmpty(number.getText()) || StringUtils.isEmpty(norms.getValue())){
            showMsg(fasteningSave,"规格与数量不能为空！");
            return false;
        }

        if(StringUtils.isEmpty(owner.getText())){
            showMsg(fasteningSave,"工地负责人不能为空！");
            return false;
        }

        if(StringUtils.isEmpty(unitPrice.getText())){
            showMsg(fasteningSave,"单价不能为空！");
            return false;
        }
        return true;
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
        if(StringUtils.isEmpty(number.getText()) || StringUtils.isEmpty(norms.getValue())){
            showMsg(fasteningSave,"规格与数量不能为空!");
            return;
        }
        try {
            float curNum = Float.parseFloat(number.getText());
            num += curNum;
        }catch (NumberFormatException e){
            showMsg(fasteningSave,"请输入数字!");
            return;
        }
        String srcText = curNorms.getText();
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
