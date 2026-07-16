package app.ui.util;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

public class ScrollPaneUtils {

    public void configurateScroll(ScrollPane scrollPane) {
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
}
