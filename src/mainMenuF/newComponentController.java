package mainMenuF;

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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class newComponentController implements Initializable {
    @FXML
    public Button addComponentButton, deleteComponentButton, mainPageButton;
    @FXML
    public TextField name_CTextF, value_CTextF, count_CTextF;
    @FXML
    public TableView<Component> newComponentTableView;
    @FXML
    public TableColumn<Component, String> nameColumn, valuesColumn, countColumn;

    public void goto_mainPage(ActionEvent event) throws IOException {
        Parent mainMenuPage = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuPage);
        Stage mainMenuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }

    public void addButtonPushed(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ComponentDB.insertComponent(name_CTextF.getText(), value_CTextF.getText(), count_CTextF.getText());
        } catch (SQLException e) {
            System.out.println("Problem occurred while inserting employee " + e);
            throw e;
        }
    }

    public void deleteButtonPushed(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            ComponentDB.deleteComponentwithName(name_CTextF.getText());
        } catch (SQLException e) {
            System.out.println("Problem occurred while deleting employee " + e);
            throw e;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valuesColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valuesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        countColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        newComponentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
