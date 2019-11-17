package com.delete.renter.ui;

import com.delete.renter.control.BuildingRecordControler;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class BuildingRecordFrame extends Application{
    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("工地信息录入");
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        Flow flow = new Flow(BuildingRecordControler.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", primaryStage);
        flow.createHandler(flowContext).start(container);

        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));

        primaryStage.setScene(new Scene(decorator, screenWidth*0.5, screenHeight*0.4));
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
