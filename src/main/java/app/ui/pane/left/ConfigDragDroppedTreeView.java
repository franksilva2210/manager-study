package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import app.ui.pane.right.DragDroppedService;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ConfigDragDroppedTreeView {

    private DragDroppedService service =
            new DragDroppedService();

    public void configureDragDropped(
            TreeView<Object> treeView,
            Runnable reloadCallback
    ) {

        treeView.setCellFactory(tv -> {

            TreeCell<Object> cell = new TreeCell<>() {

                @Override
                protected void updateItem(Object item, boolean empty) {

                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                    } else if (item instanceof StudyDTO study) {
                        setText(study.getMatter());
                    } else if (item instanceof TopicDTO topic) {
                        setText(topic.getTitle());
                    }
                }
            };

            setOnDragDetected(cell);
            setOnDragOver(cell);
            setFeedBackVisual(cell);
            setOnDragDropped(cell, reloadCallback);

            return cell;
        });
    }

    private void setOnDragDetected(TreeCell<Object> cell) {
        cell.setOnDragDetected(event -> {

            if (cell.isEmpty()) {
                return;
            }

            Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);

            StudyDTO studyDrag = (StudyDTO) cell.getItem();

            ClipboardContent content = new ClipboardContent();
            content.putString(studyDrag.getId().toString());

            dragboard.setContent(content);

            event.consume();
        });
    }

    private void setOnDragOver(TreeCell<Object> cell) {
        cell.setOnDragOver(event -> {
            if (!cell.isEmpty()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

    }

    private void setFeedBackVisual(TreeCell<Object> cell) {
        cell.setOnDragEntered(event -> {
            if (!cell.isEmpty()) {
                cell.setStyle(
                        "-fx-background-color: #3c78d8;"
                );
            }
        });

        cell.setOnDragExited(event -> {
            cell.setStyle("");
        });
    }

    private void setOnDragDropped(
            TreeCell<Object> cell,
            Runnable reloadCallback) {

        cell.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();

            if (!dragboard.hasString()) {
                return;
            }

            Long draggedId = Long.valueOf(dragboard.getString());
            TopicDTO topicDragged = service.loadSimpleTopic(draggedId);
            StudyDTO studyDestination = (StudyDTO) cell.getItem();

            service.moveTopicToStudy(topicDragged, studyDestination);
            reloadCallback.run();

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
