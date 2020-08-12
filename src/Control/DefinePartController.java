package Control;

import DatabaseController.DefinePartDBController;
import Model.DefinePartModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static Control.MainPageController.filterParts;
import static DatabaseController.DefinePartDBController.*;
import static Util.LogHandler.logger;

public class DefinePartController implements Initializable {

    public TableView<DefinePartModel> definePartTableView;
    public TableColumn<DefinePartModel, Boolean> visibilityColumn;
    public TableColumn<DefinePartModel, String> valueColumn, specColumn;
    public Button addPartButton, addRowButton, deleteRowButton, clearButton, prevPageBut;
    public TextField partNameTextField, countTextField, specTextField, valueTextField;
    public CheckBox visibleCheckBox;
    public int idx = 0, isVisible;
    public ComboBox<String> definePartComboBox;
    public String oldVal = null, oldSpec = null;

    public void addPartButtonPushed() throws SQLException, ClassNotFoundException {
        try {
            if (partNameTextField.getText().equals("")){
                System.out.println("Part name cannot be null.");
                logger.warning("Item not added. Part name cannot be null.");
            }
            else {
                definePartTableView.getColumns().clear();
                insertNameCount(partNameTextField.getText(), Integer.parseInt(countTextField.getText()));
                logger.info("New part added.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Problem occurred while inserting Part. = " + e);
            System.out.println("Problem occurred while inserting Part." + e);
            throw e;
        }
    }
    public void addRowButtonPushed() throws SQLException, ClassNotFoundException {
        try {
            if (partNameTextField.getText().equals(""))
                System.out.println("Please select a part to add feature.");
            else {
                idx = getPartID(partNameTextField.getText());
                //  isVisibleIntConverter
                if (visibleCheckBox.isSelected()) isVisible = 1;
                else isVisible = 0;
                if (specTextField.getText().equals("")) insertFeatures(idx, 0, "*", "*");
                else if (valueTextField.getText().equals(""))
                    insertFeatures(idx, isVisible, specTextField.getText(), "*");
                else insertFeatures(idx, isVisible, specTextField.getText(), valueTextField.getText());
                searchFeatures();
                specTextField.clear();
                valueTextField.clear();
                logger.info("New Feature added.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Problem occurred while inserting spec and value. = " + e);
            System.out.println("Problem occurred while inserting spec and value." + e);
            throw e;
        }
    }

    public void deleteRowButtonPushed() throws SQLException, ClassNotFoundException {
        if (definePartTableView.getSelectionModel().isEmpty())
            System.out.println("Please select a part to delete.");
        else {
            deleteFeaturewithSpec(definePartTableView.getSelectionModel().getSelectedItem().getSpec());
            searchFeatures();
            specTextField.clear();
            valueTextField.clear();
            logger.info("Part deleted.");
        }
    }

    public void clearAllButtonPushed() {
        partNameTextField.clear();
        countTextField.setText("0");
        specTextField.clear();
        valueTextField.clear();
        definePartComboBox.setValue(null);
        definePartTableView.getColumns().clear();
        logger.info("All fields cleared.");
    }

    public void getOldSpecData(){
        oldSpec = definePartTableView.getSelectionModel().getSelectedItem().getSpec();
    }
    public void setNewSpecData(TableColumn.CellEditEvent<DefinePartModel, String> specStrCellEdit) throws SQLException, ClassNotFoundException {
        definePartTableView.getSelectionModel().getSelectedItem().setSpec(specStrCellEdit.getNewValue());
        updateSpecData(specStrCellEdit.getNewValue(), oldSpec);
        logger.info("Part's Spec updated.");
    }

    public void getOldValueData(){
        oldVal = definePartTableView.getSelectionModel().getSelectedItem().getDefineItemValue();
    }
    public void setNewValueData(TableColumn.CellEditEvent<DefinePartModel, String> valStrCellEdit) throws SQLException, ClassNotFoundException {
        definePartTableView.getSelectionModel().getSelectedItem().setValue(valStrCellEdit.getNewValue());
        updateValueData(valStrCellEdit.getNewValue(), oldVal);
        logger.info("Part's Value updated.");

    }

    public void comboBoxPushed() throws SQLException, ClassNotFoundException {
        if (definePartComboBox.getSelectionModel().isEmpty())
            definePartComboBox.getSelectionModel().clearSelection();
        else {
            partNameTextField.setText(definePartComboBox.getValue());
            idx = getPartID(partNameTextField.getText());
            searchFeatures();
        }
    }

    public void searchFeatures() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<DefinePartModel> featureData = DefinePartDBController.searchFeature(idx);
            if (featureData.isEmpty()) System.out.println("Data list is empty");
            else populateFeatures(featureData);
        } catch (SQLException | ClassNotFoundException e){
            logger.warning("Error occurred while getting Part Feature information from DB. = " + e);
            System.out.println("Error occurred while getting Part Feature information from DB." + e);
            throw e;
        }
    }

    public void populateFeatures(ObservableList<DefinePartModel> featureData) { definePartTableView.setItems(featureData); }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        definePartTableView.setPlaceholder(new Label("No Parts to display"));

        visibilityColumn.setCellValueFactory(cellData -> cellData.getValue().visibilityProperty().asObject());
        specColumn.setCellValueFactory(cellData -> cellData.getValue().specProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());

        specColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        definePartTableView.editingCellProperty();
        definePartTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        filterParts(definePartComboBox);

        logger.info("DefinePart page loaded.");
    }

    public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/MainPageView.fxml")));
        Scene mainPageScene = new Scene(mainPage);
        Stage mainPageStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mainPageStage.setScene(mainPageScene);
        mainPageStage.show();
    }
}
