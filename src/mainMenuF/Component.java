package mainMenuF;

import javafx.beans.property.SimpleStringProperty;

public class Component {

    private final SimpleStringProperty name, value, count;

//    public Component(String name, String value, String count) {
    public Component() {
        this.name = new SimpleStringProperty();
        this.value = new SimpleStringProperty();
        this.count = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) { this.name.setValue(name); }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.setValue(value);
    }

    public String getCount() { return count.get(); }

    public void setCount(String count) { this.count.setValue(count); }

//    public static void ItemList() {
//        for (Component item : compTable.getItems()) itemsXX.add(nameColumn.getCellObservableValue(item).getValue());
//    }
}
