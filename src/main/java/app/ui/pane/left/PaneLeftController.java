package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.PaneRightNavigator;
import app.ui.study.register.RegisterStudyController;
import app.ui.study.register.RegisterStudyWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PaneLeftController implements Initializable {

    @FXML
    private TextField txtSearchStudies;

    @FXML
    private ListView<StudyDTO> listViewStudy;

    private final Stage stage;
    private final ScreenMainState mainState;
    private final ScreenMainController screenMainController;
    private final PaneRightNavigator navigator;

    private final ObservableList<StudyDTO> listStudyDTO = FXCollections.observableArrayList();
    private final FilteredList<StudyDTO> listStudyFiltered = new FilteredList<>(listStudyDTO);
    private final ConfigContextMenuSide configContextMenuSide = new ConfigContextMenuSide();
    private final ConfigDragDroppedMenuSide configDragDroppedMenuSide = new ConfigDragDroppedMenuSide();
    private final PaneLeftService service = new PaneLeftService();
    private PaneRightController paneRightController;

    public PaneLeftController(
            Stage stage,
            ScreenMainState mainState, ScreenMainController screenMainController,
            PaneRightNavigator navigator) {

        this.stage = stage;
        this.mainState = mainState;
        this.screenMainController = screenMainController;
        this.navigator = navigator;
    }

    public void setPaneRightController(PaneRightController paneRightController) {
        this.paneRightController = paneRightController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewStudy.setItems(listStudyFiltered);

        listViewStudy.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openStudy();
            }
        });

        listViewStudy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                openStudy();
            }
        });

        listViewStudy.setCellFactory(lv -> {

            ListCell<StudyDTO> cell = new ListCell<>() {
                @Override
                protected void updateItem(StudyDTO item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty || item == null ? null : item.getMatter());
                }
            };

            configContextMenuSide.configure(
                    cell,
                    this::renameStudy,
                    this::deleteStudy
            );

            configDragDroppedMenuSide.configure(
                    cell,
                    this
            );

            return cell;
        });

        txtSearchStudies.setOnAction(event -> {
            searchStudies();
        });

        txtSearchStudies.textProperty().addListener((obs, oldValue, newValue) -> {
            searchStudies();
        });

        refreshStudies();
    }

    private void searchStudies() {
        String search = txtSearchStudies.getText();

        listStudyFiltered.setPredicate(topic ->
                search.isBlank()
                        ||
                        topic.getMatter().toLowerCase().indexOf(search.toLowerCase()) != -1
        );
    }

    private void openStudy() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        StudyDTO studySelectedDto = listViewStudy.getSelectionModel().getSelectedItem();
        if (studySelectedDto != null) {
            mainState.setItemSelected(studySelectedDto);
            mainState.refreshItemSelected();
            navigator.navigate(mainState.getItemSelected());
            paneRightController.mappingStacksNavigatorState();
            paneRightController.loadTabsDocument();
        }
    }

    public void renameStudy(Object objectSelected) {
        StudyDTO studyDto = (StudyDTO) objectSelected;

        RegisterStudyController controller = new RegisterStudyController();
        controller.setStudyDto(studyDto);

        RegisterStudyWindow window = new RegisterStudyWindow(stage, controller);
        window.showScreen();

        StudyDTO studyDtoUpdated = controller.getStudyDto();

        paneRightController.getNavigator().refreshItem(studyDtoUpdated);
        paneRightController.loadTabsDocument();

        refreshStudies();
    }

    private void deleteStudy(Object objectDeletion) {
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
            service.removeStudy(studyDeletionDto);
            refreshStudies();
            mainState.setItemSelected(null);
            paneRightController.getNavigator().removeItem(studyDeletionDto);
            paneRightController.loadTabsDocument();
        }
    }

    public void moveTopicToStudy(Long idTopicDragged, StudyDTO studyDestination) {
        if (idTopicDragged == null || studyDestination == null) {
            return;
        }

        TopicDTO topicDragged = service.loadSimpleTopic(idTopicDragged);
        if (topicDragged.getStudyId().equals(studyDestination.getId())) {
            return;
        }

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente mover o tópico:\n" +
                topicDragged.getTitle().toUpperCase() + "\n" +
                "para: " + studyDestination.getMatter().toUpperCase() + "?\n" +
                "todos os sub tópicos também serão movidos"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.moveTopicToStudy(topicDragged, studyDestination);
            mainState.refreshItemSelected();
        }
    }

    public void moveStudyToStudy(Long idStudyDragged, StudyDTO studyDestination) {

    }

    public void refreshStudies() {
        listStudyDTO.clear();
        listStudyDTO.addAll(service.consultAllStudyDto());
    }
}
