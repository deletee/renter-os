package com.delete.renter.test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController implements Initializable
{
    // 必须static 类型
    public  static AppModel model = new AppModel();
    @FXML
    private Label txtView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // update text area if text in model changes:
        model.textProperty().addListener((obs, oldText, newText) -> txtView.setText(newText));
    }

    public static void setText(String text)
    {
        model.setText(text);
    }

    public void setTxtView(String newText){
        txtView.setText(newText);
    }

    // 打开文本输入窗口
    public void open() {
        // 从模板列表选择一个
        try
        {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResourceAsStream("fxml/Input.fxml"));
            Stage stage = new Stage();
            stage.setTitle("文本输入窗口");
            stage.setScene(new Scene(root));
            stage.setFocused(true);
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}