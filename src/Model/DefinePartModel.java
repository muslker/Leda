package Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DefinePartModel {
//    private SimpleBooleanProperty visibility;
    private BooleanProperty visibility;
    private SimpleStringProperty spec, value;
    private SimpleIntegerProperty id;

//    public DefineItemModel(boolean visibility, String spec, String value, Integer id) {
//        this.visibility = visibility;
//        this.value = new SimpleStringProperty(value);
//        this.spec = new SimpleStringProperty(spec);
//        this.id = id;
//    }
    public DefinePartModel(){
//        this.visibility = new SimpleBooleanProperty();
        this.visibility = new SimpleBooleanProperty();
        this.value = new SimpleStringProperty();
        this.spec = new SimpleStringProperty();
        this.id = new SimpleIntegerProperty();
    }

    //  Value
    public String getDefineItemValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value = new SimpleStringProperty(value);
    }
    public SimpleStringProperty valueProperty(){
        return value;
    }

    //  Spec
    public String getSpec() {   return spec.get();  }

    public void setSpec(String spec) {
        this.spec = new SimpleStringProperty(spec);
    }

    public SimpleStringProperty specProperty(){
        return spec;
    }

    //  Visibility
    public boolean getVisibility() {
        return visibility.get();
    }
    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }

//    public int getVisibility() {
//        return visibility.get();
//    }
//    public void setVisibility(int visibility) {
//        this.visibility.set(visibility);
//    }
    public BooleanProperty visibilityProperty(){
        return visibility;
    }

    //  ID
    public int getId() { return id.get(); }

    public void setId(int id){ this.id.set(id); }

    public IntegerProperty idProperty(){
        return id;
    }
}
