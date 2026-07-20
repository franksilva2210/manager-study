package app.ui.pane.left;

import app.application.study.StudyDTO;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ConfigDragDroppedMenuSide {

    public void configure(
            ListCell<StudyDTO> cell,
            PaneLeftController controller) {

        setOnDragDetected(cell);
        setOnDragOver(cell);
        setFeedBackVisual(cell);
        configureDragDone(cell);
        setOnDragDropped(cell, controller);
    }

    private void setOnDragDetected(ListCell<StudyDTO> cell) {
        cell.setOnDragDetected(event -> {

            if (cell.isEmpty()) {
                return;
            }

            Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString("STUDY:" + cell.getItem().getId().toString());

            dragboard.setContent(content);

            event.consume();
        });
    }

    private void setOnDragOver(ListCell<StudyDTO> cell) {
        cell.setOnDragOver(event -> {
            if (cell.isEmpty()) {
                return;
            }

            Dragboard dragboard = event.getDragboard();
            if (!dragboard.hasString()) {
                return;
            }

            String value = dragboard.getString();

            if (value.startsWith("TOPIC:")) {
                event.acceptTransferModes(TransferMode.MOVE);
            } else if (value.startsWith("STUDY:")) {
                Long draggedIdStudy = Long.valueOf(value.substring(6));
                if (!draggedIdStudy.equals(cell.getItem().getId())) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
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

    private void configureDragDone(ListCell<StudyDTO> cell) {
        cell.setOnDragDone(event -> {
            cell.setStyle("");
        });
    }

    private void setOnDragDropped(
            ListCell<StudyDTO> cell,
            PaneLeftController controller) {

        cell.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();
            if (!dragboard.hasString()) {
                return;
            }

            String value = dragboard.getString();
            Long idDragged = Long.valueOf(value.substring(6));
            StudyDTO studyDestination = cell.getItem();

            if (value.startsWith("TOPIC:")) {
                controller.moveTopicToStudy(idDragged, studyDestination);
            } else if (value.startsWith("STUDY:")) {
                controller.moveStudyToStudy(idDragged, studyDestination);
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
