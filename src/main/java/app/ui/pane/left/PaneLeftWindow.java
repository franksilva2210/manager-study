package app.ui.pane.left;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class PaneLeftWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private PaneLeftController controller;

    public PaneLeftWindow(PaneLeftController controller) {
        stage = new Stage();
        stage.setResizable(false);

        this.controller = controller;

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(PaneLeftWindow.class.getResource("PaneLeftWindow.fxml"));
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

    public PaneLeftController getController() {
        return controller;
    }
}
