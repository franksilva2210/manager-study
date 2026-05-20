package app.ui.topic.register;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class RegisterTopicWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private RegisterTopicController controller;

    public RegisterTopicWindow(Stage stageOwner, RegisterTopicController controller) {
        stage = new Stage();
        stage.setTitle("Novo Tópico");
        stage.setResizable(false);

        if (stageOwner != null) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(stageOwner);
        }

        this.controller = controller;
        controller.setStage(stage);

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(RegisterTopicWindow.class.getResource("RegisterTopicWindow.fxml"));
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

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public RegisterTopicController getController() {
        return controller;
    }

    public void showScreen() {
        stage.showAndWait();
    }

}
