package com.delete.renter.demo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class App extends javafx.application.Application {
    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("hbox");
        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);
        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);
        return hbox;
    }

    private VBox addVBox() {
        VBox vbox = new VBox();
        vbox.getStyleClass().addAll("pane", "vbox");
        Text title = new Text("Data");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        Hyperlink options[] = new Hyperlink[] {
                new Hyperlink("Sales"),
                new Hyperlink("Marketing"),
                new Hyperlink("Distribution"),
                new Hyperlink("Costs")
        };
        for (int i = 0; i < 4; i++) {
            // Add offset to left side to indent from title
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }
        return vbox;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            int screenWidth=Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            Scene scene = new Scene(root,screenWidth*0.6,screenHeight*0.6);
            scene.getStylesheets().add(getClass().getResource("/layout.css").toExternalForm());

            HBox hbox = addHBox();
            VBox vBox = addVBox();
            hbox.getStyleClass().add("hbox");
            root.setTop(hbox);
            root.setLeft(vBox);

            javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();

            // --- Menu File
            Menu menuFile = new Menu("File");

            // --- Menu Edit
            Menu menuEdit = new Menu("Edit");

            // --- Menu View
            Menu menuView = new Menu("View");

            menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


