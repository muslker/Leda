package Control;

import DatabaseController.ListPartDBController;
import Model.MainMenuModel;
import Util.SearchInputFilter;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

import static DatabaseController.MainMenuDBController.visFtr;
import static DatabaseController.MainMenuDBController.searchVisibleFeatures;

public class MainMenuController implements Initializable {

    @FXML
    public TextField summaryNameTextF, summaryCountTextF;
    @FXML
    public TextField incTextField, decTextField;
    @FXML
    public Button incBut, decBut, listPartPageButton, definePartPageButton;
    @FXML
    public ComboBox<String> mainMenuSearchComboBox;
    public TableView<MainMenuModel> displayFeaturesTableView;
    public TableColumn<MainMenuModel, String> displaySpecColumn, displayValueColumn;

    public void searchFeatures() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<MainMenuModel> ftrData = searchVisibleFeatures(mainMenuSearchComboBox.getValue());
            summaryNameTextF.setText(visFtr.getName());
            summaryCountTextF.setText(String.valueOf(visFtr.getCount()));
            populateFeatures(ftrData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting Part Feature information from DB." + e);
            throw e;
        }
    }

    public void populateFeatures(ObservableList<MainMenuModel> featureData) {
        displayFeaturesTableView.setItems(featureData);
    }

    public void increaseCountPressed() throws SQLException, ClassNotFoundException {
        int increaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        increaseCount +=  Integer.parseInt(incTextField.getText());
        ListPartDBController.updatePartCount(mainMenuSearchComboBox.getValue(), increaseCount);
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
    }

    public void decreaseCountPressed() throws SQLException, ClassNotFoundException {
        int decreaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        decreaseCount -=  Integer.parseInt(decTextField.getText());
        ListPartDBController.updatePartCount(mainMenuSearchComboBox.getValue(), decreaseCount);
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
    }

    public static void filterParts(ComboBox<String> searchCombo){
        ObservableList<String> parts = null;
        try {
            parts = ListPartDBController.searchPartNames();
            System.out.println(parts);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        assert parts != null;
        FilteredList<String> filteredParts = new FilteredList<>(parts);

        searchCombo.getEditor().textProperty().addListener(new SearchInputFilter(searchCombo, filteredParts, false));
        searchCombo.setItems(filteredParts);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayFeaturesTableView.setPlaceholder(new Label("Search for a Part to display here"));

        displaySpecColumn.setCellValueFactory(cellData -> cellData.getValue().specProperty());
        displayValueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());

        filterParts(mainMenuSearchComboBox);
    }

    public void goto_ListPartPage(ActionEvent event) throws IOException {
        Parent ListItemPage = FXMLLoader.load(getClass().getResource("../View/ListPartView.fxml"));
        Scene ListItemScene = new Scene(ListItemPage);
        Stage ListItemStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        ListItemStage.setScene(ListItemScene);
        ListItemStage.show();
    }
    public void goto_DefinePartPage(ActionEvent event) throws IOException {
        Parent DefineItemPage = FXMLLoader.load(getClass().getResource("../View/DefinePartView.fxml"));
        Scene DefineItemScene = new Scene(DefineItemPage);
        Stage DefineItemStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        DefineItemStage.setScene(DefineItemScene);
        DefineItemStage.show();
    }
}
