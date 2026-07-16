package app.ui.topic.card;

import app.application.topic.TopicDTO;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class CardTopicConfigDragDropped {

    private final DropShadow dragShadow = new DropShadow();

    public CardTopicConfigDragDropped() {
        configureDragShadow();
    }

    public void configureDragAndDropped(CardTopicController controller, Parent root) {
        configureDragDetected(controller, root);
        configureDragDone(root);
        configureDragOver(controller, root);
        configureDragEntered(root);
        configureDragExited(root);
        configureDragDropped(controller, root);
    }

    private void configureDragDetected(CardTopicController controller, Parent root) {
        root.setOnDragDetected(event -> {

            Dragboard dragboard = root.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(controller.getTopic().getId().toString());

            dragboard.setContent(content);

            // efeito visual do card sendo movido
            root.setOpacity(0.5);
            root.setScaleX(0.95);
            root.setScaleY(0.95);
            root.setEffect(dragShadow);

            event.consume();
        });
    }

    private void configureDragDone(Parent root) {
        root.setOnDragDone(event -> {

            root.setOpacity(1);
            root.setScaleX(1);
            root.setScaleY(1);
            root.setEffect(null);

            event.consume();
        });
    }

    private void configureDragOver(CardTopicController controller, Parent root) {
        root.setOnDragOver(event -> {

            Dragboard dragboard = event.getDragboard();

            if (dragboard.hasString()) {

                Long draggedId = Long.valueOf(dragboard.getString());

                if (!draggedId.equals(controller.getTopic().getId())) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            }

            event.consume();
        });
    }

    private void configureDragEntered(Parent root) {
        root.setOnDragEntered(event -> {

            root.setStyle(
                    "-fx-background-color: #3c78d8;"
            );

        });
    }

    private void configureDragExited(Parent root) {
        root.setOnDragExited(event -> {

            root.setStyle(
                    "-fx-border-color: #cccccc;"
            );

            event.consume();

        });
    }

    private void configureDragDropped(CardTopicController controller, Parent root) {
        root.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();

            if (!dragboard.hasString()) {
                return;
            }

            Long draggedId = Long.valueOf(dragboard.getString());
            TopicDTO topicDragged = controller.getService().loadSimpleTopic(draggedId);
            TopicDTO topicDestination = controller.getTopic();

            controller.moveTopicToTopic(topicDragged, topicDestination);

            event.setDropCompleted(true);
            event.consume();
        });
    }

    private void configureDragShadow() {
        dragShadow.setRadius(15);
        dragShadow.setOffsetX(0);
        dragShadow.setOffsetY(5);
    }
}
