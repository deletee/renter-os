package com.delete.renter.control;

import com.delete.renter.dao.DBHandler;
import com.delete.renter.data.DialogBuilder;
import com.delete.renter.data.EventModel;
import com.delete.renter.tools.FrameLoader;
import io.datafx.controller.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

@ViewController(value = "/fxml/MainFrame.fxml", title = "Material Design Example")
public class MainFrameControler implements Initializable {

    private static EventModel eventModel;

    public static EventModel getEventModel() {
        return eventModel;
    }

    public static void setEventModel(EventModel eventModel) {
        MainFrameControler.eventModel = eventModel;
    }
    @FXML
    private Label record;

    @FXML
    private Label steel;

    @FXML
    private Label fastening;

    @FXML
    private Label help;

    @FXML
    private BorderPane mainFrame;

    public void onInitDB(){
        DBHandler.getInstance().reInitDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        eventModel = new EventModel();
        eventModel.textProperty().addListener((obs, oldText, newText) -> alert(newText));
    }

    public void alert(String msg){
        new DialogBuilder(steel).setTitle("提示")
                .setMessage(msg)
                .setNegativeBtn("确定").create();
    }

    public void onZoom(){
        double topH = mainFrame.getTop().prefHeight(0.0);
        double leftW = mainFrame.getTop().prefWidth(0.0);

        double totalH = mainFrame.prefHeight(0.0);
        double totalW = mainFrame.prefWidth(0.0);

    }

    @FXML
    public void onSteelClick(){
        setEffect(steel);
        double topH = ((MenuBar)mainFrame.getTop()).getHeight();
        double leftW = ((VBox)mainFrame.getLeft()).getWidth();

        double totalH = mainFrame.getHeight();
        double totalW = mainFrame.getWidth();

        mainFrame.setCenter(FrameLoader.get(FrameLoader.STEEL_VIEW));
        ((VBox)mainFrame.getCenter()).setPrefHeight(totalH-topH);
        ((VBox)mainFrame.getCenter()).setPrefWidth(totalW-leftW);

        ChangeListener<Number> listener = (arg0, arg1, arg2) -> onDragDetect();
        mainFrame.widthProperty().addListener(listener);
        mainFrame.heightProperty().addListener(listener);
    }

    public void onFasteningClick(){
        setEffect(fastening);
        double topH = ((MenuBar)mainFrame.getTop()).getHeight();
        double leftW = ((VBox)mainFrame.getLeft()).getWidth();

        double totalH = mainFrame.getHeight();
        double totalW = mainFrame.getWidth();

        mainFrame.setCenter(FrameLoader.get(FrameLoader.FASTENING_VIEW));
        ((VBox)mainFrame.getCenter()).setPrefHeight(totalH-topH);
        ((VBox)mainFrame.getCenter()).setPrefWidth(totalW-leftW);
        ChangeListener<Number> listener = (arg0, arg1, arg2) -> onDragDetect();
        mainFrame.widthProperty().addListener(listener);
        mainFrame.heightProperty().addListener(listener);
    }

    @FXML
    private void onDragDetect(){
        double topH = ((MenuBar)mainFrame.getTop()).getHeight();
        double leftW = ((VBox)mainFrame.getLeft()).getWidth();

        double totalH = mainFrame.getHeight();
        double totalW = mainFrame.getWidth();

        ((VBox)mainFrame.getCenter()).setPrefHeight(totalH-topH);
        ((VBox)mainFrame.getCenter()).setPrefWidth(totalW-leftW);
    }

    @FXML
    public void onRecordClick(){
        setEffect(record);
    }

    public void onHelpClick(){
        setEffect(help);
    }

    public void reset(){
        record.setEffect(null);
        steel.setEffect(null);
        fastening.setEffect(null);
        help.setEffect(null);

        record.setUnderline(false);
        steel.setUnderline(false);
        fastening.setUnderline(false);
        help.setUnderline(false);

        mainFrame.setCenter(null);
    }

    private void setEffect(Label label){
        reset();
        label.setEffect(new Bloom(0.3));
        label.setUnderline(true);
    }

}
