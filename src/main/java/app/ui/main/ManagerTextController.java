package app.ui.main;

import app.application.study.StudyDTO;
import app.application.text.TextDTO;
import app.application.topic.TopicDTO;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
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

    @FXML
    private Button bttRemove;

    private AnchorPane paneText;

    private TextDTO textDto;
    private ManagerTextService managerTextService = new ManagerTextService();
    private Runnable refreshObjectCurrentSelected;
    private Runnable showData;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttEditText.setOnAction(event -> {
            editText();
        });

        bttRemove.setOnAction(event -> {
            removeText();
        });

        previewText();
    }

    public void editText() {
        EditorTextController editorTextController =
                new EditorTextController();

        editorTextController.setPaneText(paneText);
        editorTextController.setTextDto(textDto);
        editorTextController.setRefreshObjectCurrentSelected(refreshObjectCurrentSelected);
        editorTextController.setShowData(showData);
        editorTextController.setStage(stage);

        EditorTextWindow editorTextWindow =
                new EditorTextWindow(editorTextController);

        paneText.getChildren().setAll(editorTextWindow.getRoot());
    }

    private void removeText() {
        MessageConfirmController messageConfirmController = new MessageConfirmController();
        messageConfirmController.setMsgUser(
                "Deseja realmente remover este texto?"
        );

        MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow();
        messageConfirmWindow.setController(messageConfirmController);

        messageConfirmController.setMessageConfirmWindow(messageConfirmWindow);

        messageConfirmWindow.buildScreen(stage);
        messageConfirmWindow.showScreen();

        if (messageConfirmController.getConfirm()) {
            if (textDto.getId() != null && textDto.getId() > 0) {
                managerTextService.remove(textDto.getId());
            }
            refreshObjectCurrentSelected.run();
            showData.run();
        }
    }

    private void previewText() {
        if (textDto.getContent() == null || textDto.getContent().isEmpty()) {
            return;
        }

        String markdown = textDto.getContent();

        String htmlContent = MarkdownConverter.toHtml(markdown);

        String html = """
        <html>
            <body>
                %s
            </body>
        </html>
        """.formatted(htmlContent);

        webView.getEngine().loadContent(html);
    }

    public void editNameTab(Label label) {
        TextInputDialog dialog = new TextInputDialog(label.getText());

        dialog.setTitle("Renomear Aba");
        dialog.setHeaderText("");
        dialog.setContentText("Titulo: ");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newName -> {
            if (!newName.isBlank()) {
                textDto.setTitle(newName);
                textDto = managerTextService.save(textDto);
                label.setText(textDto.getTitle());
            }
        });
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

    public void setRefreshObjectCurrentSelected(Runnable refreshObjectCurrentSelected) {
        this.refreshObjectCurrentSelected = refreshObjectCurrentSelected;
    }

    public void setShowData(Runnable showData) {
        this.showData = showData;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
