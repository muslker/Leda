package mainMenuF;

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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class mainMenuController implements Initializable {

    @FXML
    public TextField summaryNameTextF, summaryValueTextF, summaryCountTextF;
    @FXML
    public TextArea incTextA, decTextA;
    @FXML
    public Button incBut, decBut, newComponentPageButton, addValuePageButton;
    @FXML
    public ComboBox<String> searchComboB;

    @FXML
    public void displaySearchedItem() throws SQLException, ClassNotFoundException {
        summaryNameTextF.setText(ComponentDB.searchComponent(searchComboB.getValue()).getName());
        summaryValueTextF.setText(ComponentDB.searchComponent(searchComboB.getValue()).getValue());
        summaryCountTextF.setText(ComponentDB.searchComponent(searchComboB.getValue()).getCount());
    }

    @FXML
    public void increaseCountPressed() throws SQLException, ClassNotFoundException {
        int increaseCount = Integer.parseInt(ComponentDB.searchComponent(searchComboB.getValue()).getCount());
        increaseCount +=  Integer.parseInt(incTextA.getText());
        ComponentDB.updateCompCount(searchComboB.getValue(), String.valueOf(increaseCount));
        summaryCountTextF.setText(ComponentDB.searchComponent(searchComboB.getValue()).getCount());
    }

    @FXML
    public void decreaseCountPressed() throws SQLException, ClassNotFoundException {

        int decreaseCount = Integer.parseInt(ComponentDB.searchComponent(searchComboB.getValue()).getCount());
        decreaseCount -=  Integer.parseInt(decTextA.getText());
        ComponentDB.updateCompCount(searchComboB.getValue(), String.valueOf(decreaseCount));
        summaryCountTextF.setText(ComponentDB.searchComponent(searchComboB.getValue()).getCount());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = null;
        try {
            items = ComponentDB.searchCompNames();
            System.out.println(items);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        assert items != null;
        FilteredList<String> filteredItems = new FilteredList<>(items);

        searchComboB.getEditor().textProperty().addListener(new InputFilter(searchComboB, filteredItems, false));
        searchComboB.setItems(filteredItems);
    }

    @FXML public void goto_newComponentPage(ActionEvent event) throws IOException {
        Parent newComponentPage = FXMLLoader.load(getClass().getResource("newComponent.fxml"));
        Scene newComponentScene = new Scene(newComponentPage);
        Stage newComponentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        newComponentStage.setScene(newComponentScene);
        newComponentStage.show();
    }
    @FXML public void goto_addValuePage(ActionEvent event) throws IOException {
        Parent addValuePage = FXMLLoader.load(getClass().getResource("addValue.fxml"));
        Scene addValueScene = new Scene(addValuePage);

        Stage addValueStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        addValueStage.setScene(addValueScene);
        addValueStage.show();
    }
}
