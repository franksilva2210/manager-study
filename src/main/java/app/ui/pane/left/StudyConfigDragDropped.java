package app.ui.pane.left;

import app.application.study.StudyDTO;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class StudyConfigDragDropped {

    public void configureDragDropped(
            ListView<StudyDTO> listViewStudy,
            PaneLeftController controller
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
            setOnDragDropped(cell, controller);

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
            PaneLeftController controller) {

        cell.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();

            if (!dragboard.hasString()) {
                return;
            }

            Long idTopicDragged = Long.valueOf(dragboard.getString());
            StudyDTO studyDestination = cell.getItem();

            controller.moveTopicToStudy(idTopicDragged, studyDestination);

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
