package app.ui.pane.right;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaneRightState {

    /* Title */

    private final StringProperty title =
            new SimpleStringProperty();

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }
}
