package com.delete.renter.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            root = loader.load(getClass().getClassLoader().getResourceAsStream("fxml/ResultPage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("控制器间数据传递");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}