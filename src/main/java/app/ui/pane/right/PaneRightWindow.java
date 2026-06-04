package app.ui.pane.right;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class PaneRightWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private PaneRightController controller;

    public PaneRightWindow(PaneRightController controller) {
        stage = new Stage();
        stage.setResizable(false);

        this.controller = controller;

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(PaneRightWindow.class.getResource("PaneRightWindow.fxml"));
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

    public AnchorPane getRoot() {
        return root;
    }

}
