package app.ui.roadmap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class RoadMapWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private RoadMapController controller;

    public RoadMapWindow(Stage stageOwner, RoadMapController controller) {
        stage = new Stage();
        stage.setTitle("Mapa Mental/Arvore Visual Hierarquica");
        stage.setResizable(false);

        if (stageOwner != null) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(stageOwner);
        }

        this.controller = controller;
        controller.setStage(stage);

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(RoadMapWindow.class.getResource("RoadMapWindow.fxml"));
        rootFxml.setController(controller);

        try {
            root = rootFxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(root);
        JMetro jMetro = new JMetro();
        jMetro.setStyle(Style.LIGHT);
        jMetro.setScene(scene);

        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public RoadMapController getController() {
        return controller;
    }

    public void setController(RoadMapController controller) {
        this.controller = controller;
    }

    public void showScreen() {
        stage.showAndWait();
    }
}
