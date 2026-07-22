package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.main.ModeUpdateItem;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.pane.right.PaneRightController;
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
    private final ScreenMainState screenMainState;
    private final ScreenMainController screenMainController;

    private final ObservableList<StudyDTO> listStudyDTO = FXCollections.observableArrayList();
    private final FilteredList<StudyDTO> listStudyFiltered = new FilteredList<>(listStudyDTO);
    private final ConfigContextMenuSide configContextMenuSide = new ConfigContextMenuSide();
    private final ConfigDragDroppedMenuSide configDragDroppedMenuSide = new ConfigDragDroppedMenuSide();
    private final PaneLeftService service = new PaneLeftService();
    private PaneRightController paneRightController;

    public PaneLeftController(
            Stage stage,
            ScreenMainState screenMainState,
            ScreenMainController screenMainController) {

        this.stage = stage;
        this.screenMainState = screenMainState;
        this.screenMainController = screenMainController;
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
                    this::removeStudy
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

        reloadScreen();
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
            screenMainController.openItemSelected(studySelectedDto);
            paneRightController.loadTabsDocument();
        }
    }

    public void renameStudy(Object objectSelected) {
        StudyDTO studyUpdateDto = (StudyDTO) objectSelected;
        StudyDTO studySelectedDto = null;

        if (screenMainState.getItemSelected() instanceof StudyDTO studyDto) {
            studySelectedDto = studyDto;
        }

        RegisterStudyController controller = new RegisterStudyController();
        controller.setStudyDto(studyUpdateDto);

        RegisterStudyWindow window = new RegisterStudyWindow(stage, controller);
        window.showScreen();

        if (studySelectedDto != null && studySelectedDto.getId().equals(studyUpdateDto.getId())) {
            screenMainState.refreshItemSelected();
            screenMainController.reloadScreen(studyUpdateDto, ModeUpdateItem.UPDATE);
        }

        reloadScreen();
    }

    private void removeStudy(Object objectDeletion) {
        StudyDTO studyDeletionDto = (StudyDTO) objectDeletion;

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente remover o estudo selecionado?\n" +
                "Todos os tópicos de: " + studyDeletionDto.getMatter() + ", também " +
                "serão removidos!"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.removeStudy(studyDeletionDto);

            screenMainState.refreshItemSelected();
            if (screenMainState.getItemSelected() == null) {
                screenMainController.reloadScreen(studyDeletionDto, ModeUpdateItem.REMOVE);
            }

            reloadScreen();
        }
    }

    public void moveTopicToStudy(Long idTopicDragged, StudyDTO studyDestination) {
        if (idTopicDragged == null || studyDestination == null) {
            return;
        }

        TopicDTO topicDragged = service.loadSimpleTopic(idTopicDragged);

        if (topicDragged.getStudyId() != null && topicDragged.getStudyId().equals(studyDestination.getId())) {
            return;
        }

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente mover o tópico selecionado?\n" +
                "Origem: " + topicDragged.getTitle() + "\n" +
                "Destino: " + studyDestination.getMatter() + "\n" +
                "Todos os sub tópicos também serão movidos."
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.moveTopicToStudy(topicDragged, studyDestination);
            screenMainState.refreshItemSelected();
        }
    }

    public void moveStudyToStudy(Long idStudyDragged, StudyDTO studyDestination) {
        if (idStudyDragged == null || studyDestination == null) {
            return;
        }

        StudyDTO studyDragged = service.loadSimpleStudy(idStudyDragged);

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente mover o tópico selecionado?\n" +
                "Origem: " + studyDragged.getMatter() + "\n" +
                "Destino: " + studyDestination.getMatter() + "\n" +
                "Todos os sub tópicos também serão movidos."
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            Long studyDraggedId = studyDragged.getId();
            Long studyDestinationId = studyDestination.getId();
            service.moveStudyToStudy(studyDraggedId, studyDestinationId);

            if (screenMainState.getItemSelected() instanceof StudyDTO studySelectedDto) {
                if (studyDragged.getId().equals(studySelectedDto.getId())) {
                    screenMainState.setItemSelected(null);
                    paneRightController.loadTabsDocument();
                } else {
                    screenMainState.refreshItemSelected();
                }
            }

            reloadScreen();
        }
    }

    public void reloadScreen() {
        listStudyDTO.clear();
        listStudyDTO.addAll(service.consultAllStudyDto());
    }
}
