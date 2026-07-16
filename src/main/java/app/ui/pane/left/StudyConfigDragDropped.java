package app.ui.pane.left;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class StudyConfigDragDropped {

    private DragDroppedService service = new DragDroppedService();

    public void configureDragDropped(
            ListView<StudyDTO> listViewStudy,
            Runnable reloadCallback
    ) {
        listViewStudy.setCellFactory(lv -> {

            ListCell<StudyDTO> cell = new ListCell<>() {

                @Override
                protected void updateItem(StudyDTO item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty || item == null ? null : item.getMatter());
                }
            };

            setOnDragOver(cell);
            setFeedBackVisual(cell);
            setOnDragDropped(cell, reloadCallback);

            return cell;
        });
    }

    private void setOnDragOver(ListCell<StudyDTO> cell) {
        cell.setOnDragOver(event -> {

            if (!cell.isEmpty() && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });
    }

    private void setFeedBackVisual(ListCell<StudyDTO> cell) {
        cell.setOnDragEntered(event -> {

            if (!cell.isEmpty()) {
                cell.setStyle(
                        "-fx-background-color: #3c78d8;" +
                        "-fx-text-fill: white;"
                );
                event.consume();
            }
        });

        cell.setOnDragExited(event -> {
            cell.setStyle("");
            event.consume();
        });
    }

    private void setOnDragDropped(
            ListCell<StudyDTO> cell,
            Runnable reloadCallback) {

        cell.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();

            if (!dragboard.hasString()) {
                return;
            }

            Long draggedId = Long.valueOf(dragboard.getString());
            TopicDTO topicDragged = service.loadSimpleTopic(draggedId);
            StudyDTO studyDestination = cell.getItem();

            service.moveTopicToStudy(topicDragged, studyDestination);
            reloadCallback.run();
            event.setDropCompleted(true);
            event.consume();
        });
    }
}
