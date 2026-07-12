package app.ui.pane.right;

import app.application.topic.TopicDTO;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

public class ConfigDragDroppedListView {

    private ConfigDragDroppedListViewService service =
            new ConfigDragDroppedListViewService();

    public void configureDragDropped(
            ListView<TopicDTO> listViewTopics,
            Stage stage,
            Runnable reloadCallback
    ) {
        listViewTopics.setCellFactory(lv -> {

            ListCell<TopicDTO> cell = new ListCell<>() {

                @Override
                protected void updateItem(TopicDTO item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty || item == null ? null : item.getTitle());
                }
            };

            setOnDragDetected(cell);
            setOnDragOver(cell);
            setFeedBackVisual(cell);
            setOnDragDropped(cell, stage, reloadCallback);

            return cell;
        });
    }

    private void setOnDragDetected(ListCell<TopicDTO> cell) {
        cell.setOnDragDetected(event -> {

            if (cell.isEmpty()) {
                return;
            }

            Dragboard dragboard = cell.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();

            content.putString(
                    cell.getItem().getId().toString()
            );

            dragboard.setContent(content);

            event.consume();
        });
    }

    private void setOnDragOver(ListCell<TopicDTO> cell) {

        cell.setOnDragOver(event -> {
            if (!cell.isEmpty()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

    }

    private void setFeedBackVisual(ListCell<TopicDTO> cell) {
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
            ListCell<TopicDTO> cell,
            Stage stage,
            Runnable reloadCallback) {

        cell.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();

            if (!dragboard.hasString()) {
                return;
            }

            Long draggedId = Long.valueOf(dragboard.getString());
            TopicDTO topicDragged = service.loadSimpleTopic(draggedId);
            TopicDTO topicDestination = cell.getItem();

            try {

                service.moveTopic(topicDragged, topicDestination);
                reloadCallback.run();
                event.setDropCompleted(true);
                event.consume();

            } catch (Exception e) {
                MessageInfoController controller = new MessageInfoController();
                controller.setMsgUser(e.getMessage());

                MessageInfoWindow window = new MessageInfoWindow(
                        stage,
                        controller
                );

                window.showScreen();

                event.setDropCompleted(false);
            }
        });
    }
}
