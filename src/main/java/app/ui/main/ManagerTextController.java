package app.ui.main;

import app.application.study.StudyDTO;
import app.application.text.TextDTO;
import app.application.topic.TopicDTO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerTextController implements Initializable {

    @FXML
    private VBox boxMain;

    @FXML
    private VBox boxView;

    @FXML
    private WebView webView;

    @FXML
    private Button bttEditText;

    private AnchorPane paneText;

    private TextDTO textDto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttEditText.setOnAction(event -> {
            editText();
        });

    }

    public void editText() {
        EditorTextController editorTextController =
                new EditorTextController();

        editorTextController.setPaneText(paneText);
        editorTextController.setTextDto(textDto);

        EditorTextWindow editorTextWindow =
                new EditorTextWindow(editorTextController);

        paneText.getChildren().setAll(editorTextWindow.getRoot());
    }

    public void setHtml(String html) {
        html = html.replaceAll(
                "contenteditable\\s*=\\s*\"true\"",
                ""
        );

        html = html.replaceAll(
                "<script[^>]*>.*?</script>",
                ""
        );

        webView.getEngine().loadContent(html);
    }

    public void setPaneText(AnchorPane paneText) {
        this.paneText = paneText;
    }

    public TextDTO getTextDto() {
        return textDto;
    }

    public void setTextDto(TextDTO textDto) {
        this.textDto = textDto;
    }

    public void setTopicDto(TopicDTO topicDto) {
        textDto.setTopicId(topicDto.getId());
    }

    public void setStudyDto(StudyDTO studyDto) {
        textDto.setStudyId(studyDto.getId());
    }
}
