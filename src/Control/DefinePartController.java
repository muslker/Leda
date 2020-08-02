package Control;

import DatabaseController.DefinePartDBController;
import DatabaseController.ListPartDBController;
import Model.DefinePartModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Control.MainMenuController.filterParts;
import static DatabaseController.DefinePartDBController.*;

public class DefinePartController implements Initializable {

    @FXML
    public TableView<DefinePartModel> definePartTableView;
    @FXML
    public TableColumn<DefinePartModel, Boolean> visibilityColumn;
    @FXML
    public TableColumn<DefinePartModel, String> valueColumn, specColumn;
    @FXML
    public Button addPartButton, addRowButton, deleteRowButton, clearButton, prevPageBut;
    @FXML
    public TextField partNameTextField, countTextField, specTextField, valueTextField;
    @FXML
    public CheckBox visibleCheckBox;
    public int idx = 0, isVisible;
    public ComboBox<String> definePartComboBox;

    public void addPartButtonPushed() throws SQLException, ClassNotFoundException {
        try {
            insertNameCount(partNameTextField.getText(), Integer.parseInt(countTextField.getText()));
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Problem occurred while inserting Part." + e);
            throw e;
        }
    }
    public void addRowButtonPushed() throws SQLException, ClassNotFoundException {
        try {
            idx = getPartID(partNameTextField.getText());
            //  isVisibleIntConverter
            if (visibleCheckBox.isSelected()) isVisible = 1;
            else isVisible = 0;
            // Checkbox değerininin true/false durumunu kontrol et
            if (specTextField.getText().equals("")) insertFeatures(idx, 0, "*", "*");
            else if (valueTextField.getText().equals("")) insertFeatures(idx, isVisible, specTextField.getText(), "*");
            else insertFeatures(idx, isVisible, specTextField.getText(), valueTextField.getText());
            searchFeatures();
            specTextField.clear();
            valueTextField.clear();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Problem occurred while inserting spec and value." + e);
            throw e;
        }
    }

    public void deleteRowButtonPushed() throws SQLException, ClassNotFoundException {
        deleteFeaturewithSpec(definePartTableView.getSelectionModel().getSelectedItem().getSpec());
        searchFeatures();
    }

    public void clearAllButtonPushed() throws SQLException, ClassNotFoundException {
        //  Temporarily disabled
//        ListPartDBController.deletePartwithName(partNameTextField.getText());
        partNameTextField.clear();
        countTextField.clear();
        specTextField.clear();
        valueTextField.clear();
        definePartComboBox.setValue(null);
        filterParts(definePartComboBox);
        searchFeatures();
    }

    public void comboBoxPushed() throws SQLException, ClassNotFoundException {
        partNameTextField.setText(definePartComboBox.getValue());
        idx = getPartID(partNameTextField.getText());
        searchFeatures();
    }

    public void searchFeatures() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<DefinePartModel> featureData = DefinePartDBController.searchFeature(idx);
            populateFeatures(featureData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting Part Feature information from DB." + e);
            throw e;
        }
    }

    public void populateFeatures(ObservableList<DefinePartModel> featureData) {
        definePartTableView.setItems(featureData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        definePartTableView.setPlaceholder(new Label("No Parts to display"));

        visibilityColumn.setCellValueFactory(cellData -> cellData.getValue().visibilityProperty().asObject());
        specColumn.setCellValueFactory(cellData -> cellData.getValue().specProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());

        definePartTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        filterParts(definePartComboBox);
    }

    public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainMenuPage = FXMLLoader.load(getClass().getResource("../View/MainMenuView.fxml"));
        Scene mainMenuScene = new Scene(mainMenuPage);
        Stage mainMenuStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }
}
