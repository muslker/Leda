package Control;

import DatabaseController.ListPartDBController;
import Model.MainPageModel;
import Util.SearchInputFilter;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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

import static DatabaseController.MainPageDBController.visFtr;
import static DatabaseController.MainPageDBController.searchVisibleFeatures;

public class MainPageController implements Initializable {

    public TextField summaryNameTextF, summaryCountTextF;
    public TextField incDecTextField;
    public Button incBut, decBut, listPartPageButton, definePartPageButton;
    public ComboBox<String> mainMenuSearchComboBox;
    public TableView<MainPageModel> displayFeaturesTableView;
    public TableColumn<MainPageModel, String> displaySpecColumn, displayValueColumn;

    public void searchFeatures() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<MainPageModel> ftrData = searchVisibleFeatures(mainMenuSearchComboBox.getValue());
            summaryNameTextF.setText(visFtr.getName());
            summaryCountTextF.setText(String.valueOf(visFtr.getCount()));
            populateFeatures(ftrData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting Part Feature information from DB." + e);
            throw e;
        }
    }

    public void populateFeatures(ObservableList<MainPageModel> featureData) {
        displayFeaturesTableView.setItems(featureData);
    }

    public void increaseCountPressed() throws SQLException, ClassNotFoundException {
        int increaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        increaseCount +=  Integer.parseInt(incDecTextField.getText());
        ListPartDBController.updatePartCount(mainMenuSearchComboBox.getValue(), increaseCount);
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
    }

    public void decreaseCountPressed() throws SQLException, ClassNotFoundException {
        int decreaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        decreaseCount -=  Integer.parseInt(incDecTextField.getText());
        ListPartDBController.updatePartCount(mainMenuSearchComboBox.getValue(), decreaseCount);
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
    }

    public static void filterParts(ComboBox<String> searchCombo){
        ObservableList<String> parts = null;
        try {
            parts = ListPartDBController.searchPartNames();
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
        Parent ListPartPage = FXMLLoader.load(getClass().getResource("../View/ListPartView.fxml"));
        Scene ListPartScene = new Scene(ListPartPage);
        Stage ListPartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        ListPartStage.setScene(ListPartScene);
        ListPartStage.show();
    }
    public void goto_DefinePartPage(ActionEvent event) throws IOException {
        Parent DefinePartPage = FXMLLoader.load(getClass().getResource("../View/DefinePartView.fxml"));
        Scene DefinePartScene = new Scene(DefinePartPage);
        Stage DefinePartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        DefinePartStage.setScene(DefinePartScene);
        DefinePartStage.show();
    }
}