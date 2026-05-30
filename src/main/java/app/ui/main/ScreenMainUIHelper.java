package app.ui.main;

import app.application.study.StudyDTO;
import app.application.document.DocumentDTO;
import app.application.topic.TopicDTO;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Consumer;

public class ScreenMainUIHelper {

    public void updateNavigationButtons(
            Button bttNavigationLeft,
            Button bttNavigationRight,
            boolean canGoBack,
            boolean canGoForward
    ) {
        bttNavigationLeft.setDisable(!canGoBack);
        bttNavigationRight.setDisable(!canGoForward);
    }

    public void updateTxtHierarchyPath(TextField txtHierarchyPath, String hierarchyPath) {
        txtHierarchyPath.setText(hierarchyPath);
    }

    public void updateTitleItemMain(Label lblTitleMain, Object objectCurrentSelected) {
        if (objectCurrentSelected instanceof StudyDTO studyDto) {
            lblTitleMain.setText(studyDto.getMatter());
        } else if (objectCurrentSelected instanceof TopicDTO topicDto) {
            lblTitleMain.setText(topicDto.getTitle());
        }
    }

    public void updateListViewTopics(
            ObservableList<TopicDTO> observableListTopics,
            ListView<TopicDTO> listViewTopics,
            Object objectCurrentSelected
    ) {
        observableListTopics.clear();

        if (objectCurrentSelected instanceof StudyDTO studyDto) {
            observableListTopics.addAll(studyDto.getListTopicsDto());
        } else if (objectCurrentSelected instanceof TopicDTO topicDto) {
            observableListTopics.addAll(topicDto.getListTopicsDto());
        }

        listViewTopics.refresh();
    }

    public Tab createNewTab(
            int indexTabs,
            VBox root,
            Label lblTitle,
            DocumentDTO documentDto) {

        String title = "";

        if (documentDto.getTitle() != null && !documentDto.getTitle().isEmpty()) {
            title = documentDto.getTitle();
        } else {
            title = "Texto " + indexTabs;
            documentDto.setTitle(title);
        }

        lblTitle.setText(title);

        Tab tab = new Tab();
        tab.setClosable(true);
        tab.setContent(root);
        tab.setGraphic(lblTitle);
        tab.setText("");

        return tab;
    }

    public void initTreeView(
            TreeView<Object> treeView,
            Consumer<Object> editStudy,
            Consumer<Object> removeStudy) {

        TreeItem<Object> treeItemRoot = new TreeItem<>("Estudos");
        treeItemRoot.setExpanded(true);

        treeView.setRoot(treeItemRoot);
        treeView.setShowRoot(false);

        configureContextMenu(treeView, editStudy, removeStudy);
    }

    private void configureContextMenu(
            TreeView<Object> treeStudies,
            Consumer<Object> editStudy,
            Consumer<Object> removeStudy) {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuEditStudy = new MenuItem("Editar Estudo");
        MenuItem menuRemoveStudy = new MenuItem("Remover Estudo");

        contextMenu.getItems().addAll(menuEditStudy, menuRemoveStudy);

        treeStudies.setCellFactory(tv -> {

            TreeCell<Object> cell = new TreeCell<>() {

                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else if (item instanceof StudyDTO study) {
                        setText(study.getMatter());
                    } else if (item instanceof TopicDTO topic) {
                        setText(topic.getTitle());
                    }
                }
            };

            cell.setOnContextMenuRequested(event -> {

                if (cell.isEmpty()) {
                    return;
                }

                Object objectSelected = cell.getItem();

                treeStudies.getSelectionModel().select(cell.getTreeItem());

                menuEditStudy.setOnAction(e -> {
					editStudy.accept(objectSelected);
                });

                menuRemoveStudy.setOnAction(e -> {
					removeStudy.accept(objectSelected);
                });

                cell.setContextMenu(contextMenu);
            });

            return cell;
        });
    }

    public void generateTreeItems(TreeView<Object> treeStudies, List<StudyDTO> listStudyDTO) {
        treeStudies.getRoot().getChildren().clear();
        for (StudyDTO studyDto : listStudyDTO) {
            TreeItem<Object> treeItemStudy = new TreeItem<>(studyDto);
            treeStudies.getRoot().getChildren().add(treeItemStudy);
        }
    }

    public void updateTabs(
            TabPane tabPaneStudy,
            Tab tabFixed,
            Tab tabFixed2,
            Object objectCurrentSelected,
            Consumer<DocumentDTO> createNewTabText) {

        tabPaneStudy.getTabs().removeIf(tab -> tab != tabFixed && tab != tabFixed2);

        if (objectCurrentSelected instanceof StudyDTO studyDto) {
            for (DocumentDTO documentDto : studyDto.getListDocumentsDto()) {
                createNewTabText.accept(documentDto);
            }
        } else if(objectCurrentSelected instanceof TopicDTO topicDto) {
            for (DocumentDTO documentDto : topicDto.getListDocumentsDto()) {
                createNewTabText.accept(documentDto);
            }
        }
    }

}
