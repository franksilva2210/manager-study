package app.ui.main;

import app.application.study.StudyDTO;
import app.application.topic.TopicDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

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
        if (itemSelected.get() instanceof StudyDTO studyDto) {
            setItemSelected(screenMainService.loadStudy(studyDto.getId()));
        } else if (itemSelected.get() instanceof TopicDTO topicDto) {
            setItemSelected(screenMainService.loadTopic(topicDto.getId()));
        }
    }
}
