package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ListPartModel {

    private StringProperty name, spec, value;
    private IntegerProperty count;
    private final SimpleIntegerProperty id;

    public ListPartModel() {
        this.name = new SimpleStringProperty();
        this.spec = new SimpleStringProperty();
        this.value = new SimpleStringProperty();
        this.count = new SimpleIntegerProperty();
        this.id = new SimpleIntegerProperty();
    }

    //  Name
    public String getName() {return name.get(); }
    public void setName(String name) { this.name = new SimpleStringProperty(name); }
    public StringProperty nameProperty() { return name; }

    //  Spec
    public String getSpec() {  return spec.get(); }
    public void setSpec(String spec) {    this.spec = new SimpleStringProperty(spec); }
    public StringProperty specProperty() { return spec; }

    //      Value
    public String getVal() {  return value.get(); }
    public void setVal(String value) {    this.value = new SimpleStringProperty(value); }
    public StringProperty valProperty() { return value; }

    //  Count
    public int getCount() {return count.get(); }
    public void setCount (int count) { this.count = new SimpleIntegerProperty(count); }
    public IntegerProperty countProperty() { return count; }

    //  ID
    public int getId() { return id.get(); }
    public void setId(int id){ this.id.set(id); }
    public IntegerProperty idProperty(){
        return id;
    }
}
