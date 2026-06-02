package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.scene.control.*;

import java.util.List;
import java.util.function.Consumer;

public class PaneLeftUIHelper {

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

}
