package mainMenuF;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Values {
    public static ObservableList<Values> values = FXCollections.observableArrayList();

    private boolean visibility;
    private SimpleStringProperty value;


    public Values(boolean visibility, String value) {
        this.visibility = visibility;
        this.value = new SimpleStringProperty(value);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value = new SimpleStringProperty(value);
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public static ObservableList<Values> getSampleValue() {
        values.add(new Values(true, "ExValue9"));
        values.add(new Values(false, "ExValue8"));
        values.add(new Values(true, "ExValue7"));
        return values;
    }
}
