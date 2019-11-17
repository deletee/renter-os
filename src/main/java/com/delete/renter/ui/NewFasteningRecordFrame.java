package com.delete.renter.ui;

import com.delete.renter.control.NewSteelRecordControler;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class NewFasteningRecordFrame extends Application{
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("扣件信息录入-出借");
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        Flow flow = new Flow(NewSteelRecordControler.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", primaryStage);
        flow.createHandler(flowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(-1, "UNNAMED", "", Color.BLUE));
//        decorator.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(decorator, screenWidth*0.5, screenHeight*0.5);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(NewFasteningRecordFrame.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                NewFasteningRecordFrame.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                NewFasteningRecordFrame.class.getResource("/css/jfoenix-main-demo.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void show(){
        Stage stage = new Stage();
        try {
            this.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
