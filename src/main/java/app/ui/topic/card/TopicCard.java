package app.ui.topic.card;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class TopicCard {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private TopicCardController controller;

    public TopicCard(TopicCardController controller) {
        stage = new Stage();
        stage.setResizable(false);

        this.controller = controller;

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(TopicCard.class.getResource("TopicCard.fxml"));
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

    public Parent getRoot() {
        return root;
    }

}
