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
import java.util.ResourceBundle;

import static Util.LogHandler.logger;

public class ListPartController implements Initializable {
    public Button deletePartButton, mainPageButton;
    public TableView<ListPartModel> listPartTableView;
    public TableColumn<ListPartModel, Integer> countColumn;
    public TableColumn<ListPartModel, String> nameColumn;
    public TableColumn<ListPartModel, String> specColumn;

    public void deletePartButtonPushed() throws SQLException, ClassNotFoundException {
        try {
            if (listPartTableView.getSelectionModel().isEmpty())
                System.out.println("Please select a part");
            else {
                ListPartDBController.deletePartwithName(listPartTableView.getSelectionModel().getSelectedItem().getName());
                listAllParts();
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("Problem occurred while deleting Part. = " + e);
            System.out.println("Problem occurred while deleting Part." + e);
            throw e;
        }
    }

    public void listAllParts() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<ListPartModel> partData = ListPartDBController.listAllParts();
            if (partData.isEmpty()) System.out.println("Data list is empty.");
            else populateParts(partData);
        } catch (SQLException | ClassNotFoundException e){
            logger.warning("Error occurred while getting Part information from DB. = " + e);
            System.out.println("Error occurred while getting Part information from DB." + e);
            throw e;
        }
    }

    public void populateParts(ObservableList<ListPartModel> partData) {  listPartTableView.setItems(partData);   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listPartTableView.setPlaceholder(new Label("No Parts to display"));

        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specColumn.setCellValueFactory(cd -> cd.getValue().valProperty());

        try {
            listAllParts();
        } catch (SQLException | ClassNotFoundException throwables) {
            logger.warning("Exception occurred = " + throwables);
            throwables.printStackTrace();
        }
        logger.info("ListPart page loaded.");
    }

    public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainPage = FXMLLoader.load(getClass().getResource("../View/MainPageView.fxml"));
        Scene mainPageScene = new Scene(mainPage);
        Stage mainPageStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainPageStage.setScene(mainPageScene);
        mainPageStage.show();
    }
}
