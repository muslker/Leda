package Control;

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
import DatabaseController.ListPartDBController;
import Util.SearchInputFilter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    public TextField summaryNameTextF, summaryValueTextF, summaryCountTextF;
    @FXML
    public TextArea incTextA, decTextA;
    @FXML
    public Button incBut, decBut, listPartPageButton, definePartPageButton;
    @FXML
    public ComboBox<String> mainMenuSearchComboBox;


    public void displaySearchedPart() throws SQLException, ClassNotFoundException {
        summaryNameTextF.setText(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getName());
//        summaryValueTextF.setText(ListItemDBController.searchItem(mainMenuSearchComboBox.getValue()).getValue());
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
    }

    public void increaseCountPressed() throws SQLException, ClassNotFoundException {
        int increaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        increaseCount +=  Integer.parseInt(incTextA.getText());
        ListPartDBController.updatePartCount(mainMenuSearchComboBox.getValue(), increaseCount);
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
    }

    public void decreaseCountPressed() throws SQLException, ClassNotFoundException {
        int decreaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        decreaseCount -=  Integer.parseInt(decTextA.getText());
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
