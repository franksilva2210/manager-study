package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TopicCardController implements Initializable {

    @FXML
    private Button bttOpenTopic;

    @FXML
    private Label titleTopic;

    private final TopicDTO topic;
    private final ScreenMainState mainState;

    public TopicCardController(
            TopicDTO topic,
            ScreenMainState mainState) {

        this.topic = topic;
        this.mainState = mainState;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleTopic.setText(topic.getTitle());

        bttOpenTopic.setOnAction(e -> {

            mainState.setItemSelected(topic);



        });

    }
}
