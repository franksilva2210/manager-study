package app.ui.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Duration;

public class ScrollPaneUtils {

    public void configure(ScrollPane scrollPane) {
        configureSpeedScroll(scrollPane);
        configureAutoScroll(scrollPane);
    }

    public void configureSpeedScroll(ScrollPane scrollPane) {
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            Node content = scrollPane.getContent();

            double delta = event.getDeltaY();

            double height = content.getBoundsInLocal().getHeight();

            double viewport = scrollPane.getViewportBounds().getHeight();

            double speed = 2.5;

            double value = delta * speed / (height - viewport);

            scrollPane.setVvalue(scrollPane.getVvalue() - value);

            event.consume();
        });
    }

    private void configureAutoScroll(ScrollPane scrollPane) {
        Timeline autoScrollTimeline = new Timeline(
                new KeyFrame(Duration.millis(20), e -> {
                    // o movimento será definido durante o DragOver
                })
        );

        autoScrollTimeline.setCycleCount(Animation.INDEFINITE);

        scrollPane.setOnDragOver(event -> {

            Bounds bounds = scrollPane.localToScene(
                    scrollPane.getBoundsInLocal()
            );

            double mouseY = event.getSceneY();

            double margin = 50;

            if (mouseY < bounds.getMinY() + margin) {

                scrollPane.setVvalue(
                        Math.max(0,
                                scrollPane.getVvalue() - 0.02)
                );

            } else if (mouseY > bounds.getMaxY() - margin) {

                scrollPane.setVvalue(
                        Math.min(1,
                                scrollPane.getVvalue() + 0.02)
                );
            }

            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        scrollPane.setOnDragExited(e -> autoScrollTimeline.stop());

        scrollPane.setOnDragDone(e -> autoScrollTimeline.stop());
    }
}
