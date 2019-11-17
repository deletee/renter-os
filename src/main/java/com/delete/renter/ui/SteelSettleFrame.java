package com.delete.renter.ui;

import com.delete.renter.control.SteelSettleControler;
import com.delete.renter.data.SteelRenter;
import com.delete.renter.tools.FrameLoader;
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

public class SteelSettleFrame extends Application{

    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("钢管结算");

        Flow flow = new Flow(SteelSettleControler.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", primaryStage);
        flow.createHandler(flowContext).start(container);
        JFXDecorator decorator = new JFXDecorator(primaryStage, container.getView());
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(-1, "UNNAMED", "", Color.BLUE));

        Scene scene = new Scene(decorator, 920, 669);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(NewSteelRecordFrame.class.getResource("/css/jfoenix-fonts.css").toExternalForm(),
                NewSteelRecordFrame.class.getResource("/css/jfoenix-design.css").toExternalForm(),
                NewSteelRecordFrame.class.getResource("/css/jfoenix-main-demo.css").toExternalForm());
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
            setShow(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
