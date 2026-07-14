package app.ui.pane.right;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PaneRightUIState {

    /* Back */

    private final ObservableList<Object> backStack =
            FXCollections.observableArrayList();

    public ObservableList<Object> getBackStack() {
        return backStack;
    }

    /* Forward */

    private final ObservableList<Object> forwardStack =
            FXCollections.observableArrayList();

    public ObservableList<Object> getForwardStack() {
        return forwardStack;
    }

    /* Hierarchy path */

    private final StringProperty hierarchyPath =
            new SimpleStringProperty();

    public String getHierarchyPath() {
        return hierarchyPath.get();
    }

    public StringProperty hierarchyPathProperty() {
        return hierarchyPath;
    }

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
