package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ListPartModel {

    private final SimpleStringProperty name, value;
    public SimpleIntegerProperty count;

    public ListPartModel() {
        this.name = new SimpleStringProperty();
        this.value = new SimpleStringProperty();
        this.count = new SimpleIntegerProperty();
    }

    public String getName() {   return name.get();  }

    public void setName(String name) { this.name.setValue(name); }

    public String getValue() {  return value.get(); }

    public void setValue(String value) {
        this.value.setValue(value);
    }

    public Integer getCount() { return count.get(); }
    public void setCount(Integer count) { this.count.setValue(count); }

//    public static void ItemList() {
//        for (Component item : compTable.getItems()) itemsXX.add(nameColumn.getCellObservableValue(item).getValue());
//    }
}
