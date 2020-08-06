package Control;

import DatabaseController.ListPartDBController;
import Model.ListPartModel;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Control.MainPageController.filterParts;

public class ListPartController implements Initializable {
    public Button deletePartButton, mainPageButton;
    public TableView<ListPartModel> listPartTableView;
    public TableColumn<ListPartModel, Integer> countColumn;
    public TableColumn<ListPartModel, String> nameColumn, specColumn;
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
            populateParts(partData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting Part information from DB." + e);
            throw e;
        }
    }

    public void populateParts(ObservableList<ListPartModel> partData) {  listPartTableView.setItems(partData);   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filterParts(listPartSearchComboBox);
        listPartTableView.setPlaceholder(new Label("No Parts to display"));

        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specColumn.setCellValueFactory(cellData -> cellData.getValue().specProperty());

        try {
            ObservableList<ListPartModel> specData = ListPartDBController.searchSpecs();
            List<String> specList = new ArrayList<>();
            List<String> valList = new ArrayList<>();
//            Map<String, ArrayList<String>> testmap = new HashMap<>();
//            testmap.put();
            for (ListPartModel specDt : specData)
                if (specList.contains(specDt.getSpec())) System.out.println("Column already exist");
                else specList.add(specDt.getSpec());


            for (String s : specList) specColumn.getColumns().add(new TableColumn<ListPartModel, String>(s));

            searchParts();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("../View/MainPageView.fxml"));
        Scene mainPageScene = new Scene(mainPage);
        Stage mainPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainPageStage.setScene(mainPageScene);
        mainPageStage.show();
    }
}
