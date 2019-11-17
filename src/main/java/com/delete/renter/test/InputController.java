package com.delete.renter.test;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class InputController
{
    @FXML
    private TextField textEnter;

    // action event handler for button:
    @FXML
    private void sendText(String text) {
        // 获取结果界面控制器
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.load(getClass().getClassLoader().getResourceAsStream("fxml/ResultPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultController control = loader.getController();
        control.setTxtView(text);
        // 设置结果界面内容
        ResultController.model.setText(textEnter.getText());
    }

    public void confirm(ActionEvent actionEvent)
    {
        //改变模板设置控制器的模板名列表属性，触发观察者
        String inputContent=textEnter.getText();
        sendText(inputContent);
//        ResultController.setText(inputContent);
        //关闭窗口
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

}