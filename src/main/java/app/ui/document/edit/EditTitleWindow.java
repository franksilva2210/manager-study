package app.ui.document.edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class EditTitleWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private EditTitleController controller;

    public EditTitleWindow(Stage stageOwner, EditTitleController controller) {
        stage = new Stage();
        stage.setTitle("Documento Markdown");
        stage.setResizable(false);

        if (stageOwner != null) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(stageOwner);
        }

        this.controller = controller;
        controller.setStage(stage);

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(EditTitleWindow.class.getResource("EditTitleWindow.fxml"));
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

    public void showScreen() {
        stage.showAndWait();
    }

}
