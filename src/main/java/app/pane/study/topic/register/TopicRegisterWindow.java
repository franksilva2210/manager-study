package app.pane.study.topic.register;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class TopicRegisterWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;
    private TopicRegisterControl controller;

    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
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

    public TopicRegisterControl getController() {
        return controller;
    }

    public void setController(TopicRegisterControl controller) {
        this.controller = controller;
    }

    public void buildAndShowScreen(Stage stageOwner) {
        buildScreen(stageOwner);
        showScreen();
    }

    public void showScreen() {
        stage.showAndWait();
    }

    public void buildScreen(Stage stageOwner) {
        buildAnchorPane();
        buildScene();
        buildStage(stageOwner);
    }

    private AnchorPane buildAnchorPane() {
        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(TopicRegisterWindow.class.getResource("TopicRegisterWindow.fxml"));
        rootFxml.setController(this.controller);

        try {
            root = rootFxml.load();
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void buildScene() {
        scene = new Scene(root);
        JMetro jMetro = new JMetro();
        jMetro.setStyle(Style.LIGHT);
        jMetro.setScene(scene);
    }

    private void buildStage(Stage stageOwner) {
        stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stageOwner);
        stage.setTitle("Cadastro de Topico");
    }
}
