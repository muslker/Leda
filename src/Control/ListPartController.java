package Control;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import Model.ListPartModel;
import DatabaseController.ListPartDBController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Control.MainMenuController.filterParts;

public class ListPartController implements Initializable {
    @FXML
    public Button deletePartButton, mainPageButton;
    @FXML
    public TableView<ListPartModel> listPartTableView;
    @FXML
    public TableColumn<ListPartModel, String> nameColumn, valuesColumn, countColumn;
    @FXML
    public ComboBox<String> listPartSearchComboBox;


    public void deletePartButtonPushed() throws SQLException, ClassNotFoundException {
        try {
            ListPartDBController.deletePartwithName(listPartTableView.getSelectionModel().getSelectedItem().getName());
            searchParts();
        } catch (SQLException e) {
            System.out.println("Problem occurred while deleting Part." + e);
            throw e;
        }
    }

    public void searchParts() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<ListPartModel> partData = ListPartDBController.searchParts();
            populateComponents(partData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting Part information from DB." + e);
            throw e;
        }
    }

    public void populateComponents (ObservableList<ListPartModel> partData) {  listPartTableView.setItems(partData);   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterParts(listPartSearchComboBox);
        listPartTableView.setPlaceholder(new Label("No Parts to display"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valuesColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valuesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        countColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        listPartTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        try {
            searchParts();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainMenuPage = FXMLLoader.load(getClass().getResource("../View/MainMenuView.fxml"));
        Scene mainMenuScene = new Scene(mainMenuPage);
        Stage mainMenuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }
}
