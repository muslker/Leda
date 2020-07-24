package mainMenuF;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {

    @FXML
    public TextField summaryTextF;
    @FXML
    public TextArea incTextA, decTextA;
    @FXML
    public Button incBut, decBut, newComponentPageButton, addValuePageButton;
    @FXML
    public ComboBox<String> searchComboB;

    public void displaySelected() throws SQLException, ClassNotFoundException {
        summaryTextF.setText(ComponentDB.searchComponent("abc").getName());
    }

    public void goto_newComponentPage(ActionEvent event) throws IOException {
        Parent newComponentPage = FXMLLoader.load(getClass().getResource("newComponent.fxml"));
        Scene newComponentScene = new Scene(newComponentPage);
        Stage newComponentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        newComponentStage.setScene(newComponentScene);
        newComponentStage.show();
    }

    public void goto_addValuePage(ActionEvent event) throws IOException {
        Parent addValuePage = FXMLLoader.load(getClass().getResource("addValue.fxml"));
        Scene addValueScene = new Scene(addValuePage);

        Stage addValueStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addValueStage.setScene(addValueScene);
        addValueStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = FXCollections.observableArrayList("One", "Two", "Three", "OneTwo", "ThreeTwo", "OneTwoThree");
        FilteredList<String> filteredItems = new FilteredList<>(items);

        searchComboB.getEditor().textProperty().addListener(new InputFilter(searchComboB, filteredItems, false));
        searchComboB.setItems(filteredItems);
    }
}
