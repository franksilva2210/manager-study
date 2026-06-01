package app.ui.about;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class AboutWindow {

    private Stage stage;
    private Scene scene;
    private AnchorPane root;

    public AboutWindow(Stage stageOwner) {
        stage = new Stage();
        stage.setTitle("Sobre");
        stage.setResizable(false);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stageOwner);

        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(AboutWindow.class.getResource("AboutWindow.fxml"));

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
