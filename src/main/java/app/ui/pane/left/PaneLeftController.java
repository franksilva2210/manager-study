package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.right.PaneRightController;
import app.ui.study.register.RegisterStudyController;
import app.ui.study.register.RegisterStudyWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaneLeftController implements Initializable {

    @FXML
    private TextField txtSearchStudies;

    @FXML
    private TreeView<Object> treeView;

    private Stage stage;
    private List<StudyDTO> listStudyDTO = new ArrayList<>();
    private PaneLeftService paneLeftService = new PaneLeftService();
    private PaneLeftUIHelper uiHelper = new PaneLeftUIHelper();
    private PaneRightController paneRightController;

    public PaneLeftController(Stage stage) {
        this.stage = stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setPaneRightController(PaneRightController paneRightController) {
        this.paneRightController = paneRightController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectItemMenuLeft();
            }
        });

        treeView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                selectItemMenuLeft();
            }
        });

        txtSearchStudies.setOnAction(event -> {
            searchStudies();
        });

        txtSearchStudies.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && txtSearchStudies.getText().isBlank()) {
                refreshStudies();
            }
        });

        initUI();

        refreshStudies();
    }

    private void searchStudies() {
        String search = txtSearchStudies.getText();
        List<StudyDTO> listPossible = new ArrayList<>();

        if (!search.equals("")) {

            for (StudyDTO studyDto : listStudyDTO) {
                if (studyDto.getMatter().toLowerCase().indexOf(search.toLowerCase()) != -1) {
                    listPossible.add(studyDto);
                }
            }

            if (listPossible.size() > 0) {
                listStudyDTO.clear();
                listStudyDTO.addAll(listPossible);
                uiHelper.generateTreeItems(treeView, listStudyDTO);
            }

        } else {
            refreshStudies();
        }
    }

    private void selectItemMenuLeft() {
        TreeItem<Object> itemSelected = treeView.getSelectionModel().getSelectedItem();
        if (itemSelected != null) {
            paneRightController.openStudy(itemSelected.getValue());
        }
    }

    public void editStudy(Object objectSelected) {
        StudyDTO studyDto = (StudyDTO) objectSelected;

        RegisterStudyController controller = new RegisterStudyController();
        controller.setStudyDto(studyDto);

        RegisterStudyWindow window = new RegisterStudyWindow(stage, controller);
        window.showScreen();

        StudyDTO studyDtoUpdated = controller.getStudyDto();

        paneRightController.getNavigator().refreshItem(studyDtoUpdated);
        paneRightController.loadDataScreen();

        refreshStudies();
    }

    private void removeStudy(Object objectDeletion) {
        StudyDTO studyDeletionDto = (StudyDTO) objectDeletion;

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente remover o estudo selecionado?\n" +
                "Todos os tópicos de: " + studyDeletionDto.getMatter().toUpperCase() + ", também " +
                "serão removidos!"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            paneLeftService.removeStudy(studyDeletionDto);
            refreshStudies();
            paneRightController.getNavigator().removeItem(studyDeletionDto);
            paneRightController.loadDataScreen();
        }
    }

    public void refreshStudies() {
        listStudyDTO.clear();
        listStudyDTO.addAll(paneLeftService.consultAllStudyDto());
        uiHelper.generateTreeItems(treeView, listStudyDTO);
    }

    private void initUI() {
        uiHelper.initTreeView(treeView, this::editStudy, this::removeStudy);
    }
}
