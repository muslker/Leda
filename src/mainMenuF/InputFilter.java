package mainMenuF;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;

class InputFilter implements ChangeListener<String> {

    private final ComboBox<String> box;
    private final FilteredList<String> items;
    private final boolean upperCase;
    private final int maxLength;
    private final String restriction;

    public InputFilter(ComboBox<String> box, FilteredList<String> items, boolean upperCase, int maxLength,
                       String restriction) {
        this.box = box;
        this.items = items;
        this.upperCase = upperCase;
        this.maxLength = maxLength;
        this.restriction = restriction;
    }

    public InputFilter(ComboBox<String> box, FilteredList<String> items, boolean upperCase) {
        this(box, items, upperCase, -1, null);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        StringProperty value = new SimpleStringProperty(newValue);

        String selected = box.getSelectionModel().getSelectedItem();

        String selectedString = null;

        if (selected != null) {
            selectedString = selected;
        }

        if (upperCase) {
            value.set(value.get().toUpperCase());
        }

        if (maxLength >= 0 && value.get().length() > maxLength) {
            value.set(oldValue);
        }

        if (restriction != null) {
            if (!value.get().matches(restriction + "*")) {
                value.set(oldValue);
            }
        }

        if (selected != null && value.get().equals(selectedString)) {
            Platform.runLater(() -> box.getEditor().end());
        } else {
            items.setPredicate(item -> item.toUpperCase().contains(value.get().toUpperCase()));
        }

        if (!box.isShowing()) {
            if (!newValue.isEmpty() && box.isFocused()) {
                box.show();
            }
        }
        else {
            if (items.size() == 1) {
                String item = items.get(0);

                if (value.get().equals(item)) {
                    Platform.runLater(box::hide);
                }
            }
        }
        box.getEditor().setText(value.get());
    }
}