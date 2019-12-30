package com.delete.renter.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.AbstractMap;
import java.util.Map.Entry;

public class MenuSample extends Application {

    final PageData[] pages = new PageData[] {
            new PageData("Apple",
                    "The apple is the pomaceous fruit of the apple tree, species Malus "
                            + "domestica in the rose family (Rosaceae). It is one of the most "
                            + "widely cultivated tree fruits, and the most widely known of "
                            + "the many members of genus Malus that are used by humans. "
                            + "The tree originated in Western Asia, where its wild ancestor, "
                            + "the Alma, is still found today.",
                    "Malus domestica"),
            new PageData("Hawthorn",
                    "The hawthorn is a large genus of shrubs and trees in the rose "
                            + "family, Rosaceae, native to temperate regions of the Northern "
                            + "Hemisphere in Europe, Asia and North America. "
                            + "The name hawthorn was "
                            + "originally applied to the species native to northern Europe, "
                            + "especially the Common Hawthorn C. monogyna, and the unmodified "
                            + "name is often so used in Britain and Ireland.",
                    "Crataegus monogyna"),
            new PageData("Ivy",
                    "The ivy is a flowering plant in the grape family (Vitaceae) native to"
                            + " eastern Asia in Japan, Korea, and northern and eastern China. "
                            + "It is a deciduous woody vine growing to 30 m tall or more given "
                            + "suitable support,  attaching itself by means of numerous small "
                            + "branched tendrils tipped with sticky disks.",
                    "Parthenocissus tricuspidata"),
            new PageData("Quince",
                    "The quince is the sole member of the genus Cydonia and is native to "
                            + "warm-temperate southwest Asia in the Caucasus region. The "
                            + "immature fruit is green with dense grey-white pubescence, most "
                            + "of which rubs off before maturity in late autumn when the fruit "
                            + "changes color to yellow with hard, strongly perfumed flesh.",
                    "Cydonia oblonga")
    };

    final String[] viewOptions = new String[] {
            "Title",
            "Binomial name",
            "Picture",
            "Description"
    };

    final Entry[] effects = new Entry[] {
            new AbstractMap.SimpleEntry<>("Sepia Tone", new SepiaTone()),
            new AbstractMap.SimpleEntry<>("Glow", new Glow()),
            new AbstractMap.SimpleEntry<>("Shadow", new DropShadow())
    };

    final ImageView pic = new ImageView();
    final Label name = new Label();
    final Label binName = new Label();
    final Label description = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Menu Sample");
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 400, 350);
        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");

        // --- Menu View
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        menuBar.prefWidthProperty().bind(stage.widthProperty());//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/javafx/javafx_menu.html
        borderPane.setTop(menuBar);
        stage.setScene(scene);
        stage.show();
    }


    private class PageData {
        String name;
        String description;
        String binNames;
        Image image;
        PageData(String name, String description, String binNames) {
            this.name = name;
            this.description = description;
            this.binNames = binNames;
//            image = new Image(getClass().getResourceAsStream(name + ".jpg"));
        }
    }
}