package app.ui.document.edit;

import app.application.document.DocumentDTO;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.util.TooltipUtils;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditorDocumentController implements Initializable {

    @FXML
    private Button bttPreview;

    @FXML
    private Button bttEdit;

    @FXML
    private Button bttRemove;

    @FXML
    private Button bttExport;

    @FXML
    private Button bttImport;

    @FXML
    private Button bttBlocCod;

    @FXML
    private Button bttBold;

    @FXML
    private Button bttItalic;

    @FXML
    private Button bttAttachImg;

    @FXML
    private Button bttResizable;

    @FXML
    private ImageView iconResizable;

    @FXML
    private StackPane pane;

    @FXML
    private Button bttSave;

    @FXML
    private Button bttCancel;

    private TabPane tabPaneStudy;

    private Tab tab;

    private Label lblTitle;

    private WebView webView;

    private CodeArea codeArea;

    private VirtualizedScrollPane<CodeArea> scrollPaneCodeArea;

    private final ScreenMainState screenMainState;

    private final DocumentState state = new DocumentState();
    private final EditorDocumentService service = new EditorDocumentService();
    private final EditorDocumentUIHelper uiHelper = new EditorDocumentUIHelper();
    private final EditorDocumentFacade facade = new EditorDocumentFacade();
    private final BooleanProperty expanded = new SimpleBooleanProperty(false);
    private final Image maximize = new Image(getClass().getResourceAsStream("maximize.png"));
    private final Image minimize = new Image(getClass().getResourceAsStream("minimize.png"));

    private DocumentDTO documentDto;
    private Stage stage;
    private Stage stageMain;
    private Stage stageDocumentModal;

    public EditorDocumentController(ScreenMainState screenMainState) {
        this.screenMainState = screenMainState;
    }

    public void setDocumentDto(DocumentDTO documentDto) {
        this.documentDto = documentDto;
    }

    public DocumentDTO getDocumentDto() {
        return documentDto;
    }

    public DocumentState getState() {
        return state;
    }

    public void setLblTitle(Label lblTitle) {
        this.lblTitle = lblTitle;
    }

    public void setTabPaneStudy(TabPane tabPaneStudy) {
        this.tabPaneStudy = tabPaneStudy;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public void setStageMain(Stage stage) {
        this.stageMain = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblTitle.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editTitle();
            }
        });

        bttEdit.setOnAction(event -> {
            editDocument();
        });

        bttPreview.setOnAction(event -> {
            previewDocument();
        });

        bttRemove.setOnAction(event -> {
            removeDocument();
        });

        bttImport.setOnAction(event -> {
            importDocument();
        });

        bttExport.setOnAction(event -> {
            exportDocument();
        });
        
        bttBlocCod.setOnAction(event -> {
            onCodeBlock();
        });

        bttBold.setOnAction(event -> {
            onBold();
        });

        bttItalic.setOnAction(event -> {
            onItalic();
        });

        bttAttachImg.setOnAction(event -> {
            onAttachImage();
        });

        bttResizable.setOnAction(event -> {
            showDocumentWindow();
        });

        bttSave.setOnAction(event -> {
            save();
        });

        bttCancel.setOnAction(event -> {
            cancel();
        });

        createCodeArea();

        codeArea.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.S) {
                save();
                event.consume();
            }
        });

        createWebView();

        expanded.addListener((obs, oldValue, isExpanded) -> {
            iconResizable.setImage(isExpanded ? minimize : maximize);
        });

        loadFacade();

        uiHelper.configurateTooltip(facade);

        DocumentStateMapper.fillState(state, documentDto);

        EditorDocumentUIBinder.bind(facade, state);

        showInitial();

        stage = stageMain;
    }

    public void editTitle() {
        EditTitleController controller = new EditTitleController();
        controller.setTitleCurrent(state.getTitle());
        EditTitleWindow window = new EditTitleWindow(stage, controller);
        window.showScreen();

        String newTitle = controller.getNewTitle();
        if (newTitle != null) {
            state.titleProperty().set(newTitle);
        }
    }

    private void previewDocument() {
        String text = state.getContent();
        String htmlContent = MarkdownConverter.toHtml(text);

        String html = """
        <html>
            <body>
                %s
            </body>
        </html>
        """.formatted(htmlContent);

        webView.getEngine().loadContent(html);

        uiHelper.showPane(webView, pane);
    }

    private void editDocument() {
        uiHelper.showPane(scrollPaneCodeArea, pane);
    }

    private void removeDocument() {
        MessageConfirmController messageConfirmController = new MessageConfirmController();
        messageConfirmController.setConfirm(false);
        messageConfirmController.setMsgUser(
                "Deseja realmente remover este Texto?"
        );

        MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow(stage, messageConfirmController);
        messageConfirmWindow.showScreen();

        if (messageConfirmController.getConfirm()) {
            if (state.getId() != null && state.getId() > 0) {
                service.remove(state.getId());
                screenMainState.refreshItemSelected();
            }
            tabPaneStudy.getTabs().remove(tab);
        }
    }

    private void exportDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Documento");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Markdown (*.md)", "*.md")
        );
        fileChooser.setInitialFileName(
                state.getTitle() != null ? state.getTitle() + ".md" : "documento.md"
        );

        File file = fileChooser.showSaveDialog(stage);
        if (file == null) return;

        try {
            Files.writeString(file.toPath(), state.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importDocument() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importar Documento");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Markdown (*.md)", "*.md")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file == null) return;

        try {

            String fileName = file.getName();
            if (fileName.endsWith(".md")) {
                fileName = fileName.substring(0, fileName.length() - 3);
            }

            state.titleProperty().set(fileName);

            String content = Files.readString(file.toPath());
            state.contentProperty().set(content);

            editDocument();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCodeBlock() {
        String selectedText = codeArea.getSelectedText();
        if (selectedText == null || selectedText.isBlank()) {
            return;
        }

        String codeBlock = """
                
                ```
                %s
                ```
                
                """.formatted(selectedText);

        codeArea.replaceSelection(codeBlock);
    }

    private void onBold() {
        String selectedText = codeArea.getSelectedText();
        if (selectedText == null || selectedText.isBlank()) {
            return;
        }

        String boldText = "**%s**".formatted(selectedText);
        codeArea.replaceSelection(boldText);
    }

    private void onItalic() {
        String selectedText = codeArea.getSelectedText();
        if (selectedText == null || selectedText.isBlank()) {
            return;
        }

        String italicText = "*%s*".formatted(selectedText);
        codeArea.replaceSelection(italicText);
    }

    private void onAttachImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String path = file.toURI().toString();
            codeArea.replaceSelection("![](" + path + ")");
        }
    }

    private void save() {
        DocumentStateMapper.fillDTO(documentDto, state);
        documentDto = service.save(documentDto);
        DocumentStateMapper.fillState(state, documentDto);
        previewDocument();
        screenMainState.refreshItemSelected();
    }

    private void cancel() {
        MessageConfirmController messageConfirmController = new MessageConfirmController();
        messageConfirmController.setConfirm(false);
        messageConfirmController.setMsgUser(
                "Deseja realmente cancelar a edição?\n" +
                "O Texto voltará ao seu estado atual. "
        );

        MessageConfirmWindow messageConfirmWindow = new MessageConfirmWindow(stage, messageConfirmController);
        messageConfirmWindow.showScreen();

        if (messageConfirmController.getConfirm()) {
            DocumentStateMapper.fillState(state, documentDto);
            previewDocument();
        }
    }

    /* Helpers */

    private void createCodeArea() {
        codeArea = new CodeArea();
        codeArea.setWrapText(true);

        scrollPaneCodeArea = new VirtualizedScrollPane<>(codeArea);
        pane.getChildren().add(scrollPaneCodeArea);

        scrollPaneCodeArea.setVisible(false);
        scrollPaneCodeArea.setManaged(false);
    }

    private void createWebView() {
        webView = new WebView();
        webView.setVisible(false);
        webView.setManaged(false);
        pane.getChildren().add(webView);
    }

    private void loadFacade() {
        facade.setTab(tab);
        facade.setLblTitle(lblTitle);
        facade.setBttPreview(bttPreview);
        facade.setBttEdit(bttEdit);
        facade.setBttImport(bttImport);
        facade.setBttExport(bttExport);
        facade.setBttRemove(bttRemove);
        facade.setBttBold(bttBold);
        facade.setBttItalic(bttItalic);
        facade.setBttAttachImg(bttAttachImg);
        facade.setBttBlocCod(bttBlocCod);
        facade.setCodeArea(codeArea);
        facade.setBttSave(bttSave);
        facade.setBttCancel(bttCancel);
        facade.setBttResizable(bttResizable);
    }

    public boolean isEditing() {
        boolean isEditing = !Objects.equals(documentDto.getTitle(), state.getTitle())
                || !Objects.equals(documentDto.getContent(), state.getContent());
        return isEditing;
    }

    private void showInitial() {
        if (state.getId() != null) {
            previewDocument();
        } else {
            editDocument();
        }
    }

    private void showDocumentWindow() {
        if (stageDocumentModal != null) {
            closeDocumentWindow();
        } else {
            openDocumentWindow();
        }
    }

    private void openDocumentWindow() {
        BorderPane root = (BorderPane) tab.getContent();
        tab.setContent(null);

        stageDocumentModal = new Stage();
        stageDocumentModal.setTitle(state.getTitle());
        stageDocumentModal.setWidth(850);
        stageDocumentModal.setHeight(600);
        stageDocumentModal.setResizable(true);
        stageDocumentModal.initModality(Modality.WINDOW_MODAL);
        stageDocumentModal.initOwner(stageMain);

        Scene scene = new Scene(root);
        JMetro jMetro = new JMetro();
        jMetro.setStyle(Style.LIGHT);
        jMetro.setScene(scene);

        stageDocumentModal.setScene(scene);

        stageDocumentModal.setOnCloseRequest(event -> {
            event.consume();
            closeDocumentWindow();
        });

        stage = stageDocumentModal;
        expanded.set(true);
        bttResizable.setTooltip(TooltipUtils.create("Minimiza tela"));

        stageDocumentModal.show();
    }

    private void closeDocumentWindow() {
        BorderPane root = (BorderPane) stageDocumentModal.getScene().getRoot();
        tab.setContent(root);

        stageDocumentModal.setScene(null);
        stageDocumentModal.close();
        stageDocumentModal = null;

        stage = stageMain;
        expanded.set(false);
        bttResizable.setTooltip(TooltipUtils.create("Maximiza tela"));

        Platform.runLater(() -> {
            root.applyCss();
            root.layout();

            pane.applyCss();
            pane.layout();

            codeArea.applyCss();
            codeArea.layout();

            scrollPaneCodeArea.applyCss();
            scrollPaneCodeArea.layout();
            scrollPaneCodeArea.requestLayout();

            webView.applyCss();
            webView.layout();
        });
    }
}
