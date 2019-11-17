package com.delete.renter.control;


import com.delete.renter.constant.RenterType;
import com.delete.renter.dao.DataHelper;
import com.delete.renter.data.DialogBuilder;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.utils.NormCalc;
import com.jfoenix.controls.JFXDialog;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@ViewController(value = "/fxml/newSteelRecord.fxml", title = "Material Design Example")
public class NewSteelRecordControler implements Initializable{

    @FXMLViewFlowContext
    private ViewFlowContext context;

    public static final String CONTENT_PANE = "contentPane";

    @FXML
    private TextField buildName;

    @FXML
    private TextField owner;

    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton renterType;

    @FXML
    private RadioButton returnType;

    @FXML
    private TextField norms;

    @FXML
    private TextField num;

    @FXML
    private TextField unitPrice;

    @FXML
    private HBox unitPriceHBox;
    @FXML
    private JFXDialog dialog;

    @FXML
    private Text error1;

    @FXML
    private Text error2;

    @FXML
    private Text error3;

    @FXML
    private Text error4;

    @FXML
    private Text error5;

    @FXML
    private Button steelSave;

    @FXML
    private Button steelCancel;

    private final ToggleGroup group = new ToggleGroup();


    @FXML
    public void onSteelAddSave(){
        if (check()){
            SteelRenter steelRenter = new SteelRenter();
            steelRenter.setBuildName(buildName.getText());
            steelRenter.setOwner(owner.getText());
            steelRenter.setTime(datePicker.getValue().toString());
            steelRenter.setNorms(norms.getText());
            steelRenter.setNum(Float.parseFloat(num.getText()));
            if (renterType.isSelected()){
                steelRenter.setRenterType(RenterType.RENTER.name());
                steelRenter.setUnitPrice(Float.parseFloat(unitPrice.getText()));
            }else{
                steelRenter.setRenterType(RenterType.RETURN.name());
                steelRenter.setUnitPrice(0.0f);
            }

            DataHelper.insertNewSteelRecord(steelRenter);

            DialogBuilder dialogBuilder = new DialogBuilder(steelSave).setTitle("提示")
                    .setMessage("保存成功，")
                    .setHyperLink("点击查看",() -> {
                        Stage stage = (Stage)steelSave.getScene().getWindow();
                        stage.close();
                    })
                    .setPositiveBtn("确定", () -> {
                        FXMLLoader loader = new FXMLLoader();
                        try {
                            loader.load(getClass().getClassLoader().getResourceAsStream("fxml/steelView.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SteelViewControler steelViewControler = loader.getController();
                        steelViewControler.init();
                        Stage stage = (Stage)steelSave.getScene().getWindow();
                        stage.close();
                    });
            dialogBuilder.create();
        }
    }

    @FXML
    public void onSteelAddCancel(){
        Stage stage = (Stage)steelCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onReturn(){
        unitPriceHBox.setVisible(false);
    }

    @FXML
    public void onRenter(){
        unitPriceHBox.setVisible(true);
    }

    private boolean check(){
        if (StringUtils.isEmpty(buildName.getText())){
            error1.setVisible(true);
            return false;
        }

        if (StringUtils.isEmpty(owner.getText())){
            error2.setVisible(true);
            return false;
        }

        if (StringUtils.isEmpty(datePicker.getValue().toString())){
            error3.setVisible(true);
            return false;
        }

        if (StringUtils.isEmpty(norms.getText())){
            error4.setText("规格不能为空");
            error4.setVisible(true);
            return false;
        }

        if (renterType.isSelected() && StringUtils.isEmpty(unitPrice.getText())){
            error5.setVisible(true);
            return false;
        }

        try {
            Double.parseDouble(num.getText());
        }catch (NumberFormatException e){
            error4.setText("格式错误，请遵循计算格式");
            error4.setVisible(true);
            return false;
        }

        if (renterType.isSelected()){
            try {
                Double.parseDouble(unitPrice.getText());
            }catch (NumberFormatException e){
                error5.setText("格式错误");
                error5.setVisible(true);
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        datePicker.setValue(LocalDate.now());
        returnType.setToggleGroup(group);
        renterType.setToggleGroup(group);

        norms.textProperty().addListener((observable, oldValue, newValue) -> {
            if (StringUtils.isEmpty(newValue.trim()) || !newValue.contains("*")){
                return;
            }
            try {
                float numResult = NormCalc.calcNum(newValue);
                num.setText(String.valueOf(numResult));
                error4.setVisible(false);
            }catch (Exception e){
                System.err.println("输入格式不合法：" + newValue);
            }
        });
    }
}
