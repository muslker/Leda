package mainMenuF;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addValueController implements Initializable {

    @FXML
    public TableView<Values> addValueTableView;
    @FXML
    public TableColumn<Values, Boolean> visibleColumn;
    @FXML
    public TableColumn<Values, String> valueColumn;
    @FXML
    public Button addValueButton, deleteValueButton, prevPageBut;
    @FXML
    public TextField addValueTextField, selectedItemTextField;
    @FXML
    public ComboBox<String> searchComboBox;
    @FXML
    public CheckBox visibleCheckBox;

    @FXML
    public void addButtonPushed() {
        Values newValues = new Values(visibleCheckBox.isSelected(), addValueTextField.getText());
        addValueTableView.getItems().add(newValues);
    }

    @FXML
    public void deleteButtonPushed() {
        ObservableList<Values> selectedRows, allComponent;
        allComponent = addValueTableView.getItems();

        selectedRows = addValueTableView.getSelectionModel().getSelectedItems();

        for (Values deletedValues : selectedRows)
            allComponent.remove(deletedValues);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addValueTableView.setItems(Values.getSampleValue());

        visibleColumn.setCellFactory(CheckBoxTableCell.forTableColumn(visibleColumn));
        visibleColumn.setCellValueFactory(new PropertyValueFactory<>("visibility"));

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        addValueTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainMenuPage = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuPage);
        Stage mainMenuStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }
}
