package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScreenMainState {

    private final ScreenMainService screenMainService = new ScreenMainService();

    /* Item selected */

    private final ObjectProperty<Object> itemSelected =
            new SimpleObjectProperty<>();

    public Object getItemSelected() {
        return itemSelected.get();
    }

    public void setItemSelected(Object value) {
        itemSelected.set(value);
    }

    public ObjectProperty<Object> itemSelectedProperty() {
        return itemSelected;
    }

    public void refreshItemSelected() {
        if (itemSelected == null) {
            return;
        }

        if (itemSelected.get() instanceof StudyDTO studyDto) {
            setItemSelected(screenMainService.loadStudy(studyDto.getId()));
        } else if (itemSelected.get() instanceof TopicDTO topicDto) {
            setItemSelected(screenMainService.loadTopic(topicDto.getId()));
        }
    }

    /* Back */

    private final ObservableList<Object> backStack =
            FXCollections.observableArrayList();

    public ObservableList<Object> getBackStack() {
        return backStack;
    }

    public void refreshBackStack(Breadcrumb breadcrumb) {
        backStack.setAll(breadcrumb.getBackStack());
    }

    /* Forward */

    private final ObservableList<Object> forwardStack =
            FXCollections.observableArrayList();

    public ObservableList<Object> getForwardStack() {
        return forwardStack;
    }

    public void refreshForwardStack(Breadcrumb breadcrumb) {
        forwardStack.setAll(breadcrumb.getForwardStack());
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
}
