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

    public void updateStacksProperty(Breadcrumb breadcrumb) {
        backStack.setAll(breadcrumb.getBackStack());
        forwardStack.setAll(breadcrumb.getForwardStack());
    }

    public void loadItemSelected(Object value, Breadcrumb breadcrumb) {
        setItemSelected(value);
        refreshItemSelected();
        updateStacksProperty(breadcrumb);
    }
}
