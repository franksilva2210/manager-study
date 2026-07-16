package app.ui.pane.right.topics;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class CardSelectionModel<T> {

    private final ObjectProperty<T> selectedItem =
            new SimpleObjectProperty<>();

    public T getSelectedItem() {
        return selectedItem.get();
    }

    public void select(T item) {
        selectedItem.set(item);
    }

    public void clearSelection() {
        selectedItem.set(null);
    }

    public ReadOnlyObjectProperty<T> selectedItemProperty() {
        return selectedItem;
    }

    public boolean hasSelection() {
        return getSelectedItem() != null;
    }

}
