package com.delete.renter.tools;

import com.delete.renter.control.MainFrameControler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrameLoader {
    private static final String ICON_IMAGE_LOC = "/tmp/rental_entry.png";
    private static Map<String,Node> nodeCache = new ConcurrentHashMap<>();
    private static Map<String,String> nodeUrl = new HashMap<>();
    public static String STEEL_VIEW = "steelView";
    public static String FASTENING_VIEW = "fasteningView";
    public static String STEEL_SETTLE = "steelSettle";

    static {
        nodeUrl.put(STEEL_VIEW, "/fxml/steelView.fxml");
        nodeUrl.put(FASTENING_VIEW,"/fxml/fasteningView.fxml");
        nodeUrl.put(STEEL_SETTLE,"fxml/steelSettle.fxml");
    }
    private static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(ICON_IMAGE_LOC));
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String name){
        if (nodeCache.containsKey(name)){
            return (T) nodeCache.get(name);
        }else{
            return loadNode(nodeUrl.get(name));
        }
    }

    public static Object loadFrame(URL loc, String title, Stage parentStage) {
        Object controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(loc);
            Parent parent = loader.load();
            controller = loader.getController();
            Stage stage = null;
            if (parentStage != null) {
                stage = parentStage;
            } else {
                stage = new Stage(StageStyle.DECORATED);
            }
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(MainFrameControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }

    @SuppressWarnings("unchecked")
    public static <T> T loadNode(URL loc){
        FXMLLoader loader = new FXMLLoader(loc);
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) parent;
    }

    @SuppressWarnings("unchecked")
    private static <T> T loadNode(String loc){
        FXMLLoader loader = new FXMLLoader(FrameLoader.class.getClass().getResource(loc));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) parent;
    }

    public static <T> T getController(String name){
        String loc = nodeUrl.get(name);
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.load(FrameLoader.class.getClassLoader().getResourceAsStream(loc));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader.getController();
    }
}
