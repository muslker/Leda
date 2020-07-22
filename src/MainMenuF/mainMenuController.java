package MainMenuF;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {
    @FXML private TextField summaryTextF;

    @FXML private TextArea incTextA;

    @FXML private TextArea decTextA;

    @FXML private Button incBut;

    @FXML private Button decBut;

    @FXML private Button newCompBut;

    @FXML private ComboBox searchComboB;


    public void filterSearchComboB(){
        ObservableList<String> items = FXCollections.observableArrayList("One", "Two", "Three", "OneTwo", "ThreeTwo", "OneTwoThree");
        FilteredList<String> filteredItems = new FilteredList<>(items);

        searchComboB.getEditor().textProperty().addListener(new InputFilter(searchComboB, filteredItems, false));
        searchComboB.setItems(filteredItems);
    }

    public void displaySelected(){
     summaryTextF.setText(searchComboB.getValue().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
