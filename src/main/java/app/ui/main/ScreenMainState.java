package app.ui.main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ScreenMainState {

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
}
