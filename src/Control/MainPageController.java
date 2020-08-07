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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static DatabaseController.MainPageDBController.searchVisibleFeatures;
import static DatabaseController.MainPageDBController.visFtr;
import static Util.Exporter.ExcelExporter;
import static Util.LogHandler.fh;
import static Util.LogHandler.logger;

public class MainPageController implements Initializable {

    public TextField summaryNameTextF, summaryCountTextF;
    public TextField incDecTextField;
    public Button incBut, decBut, listPartPageButton, definePartPageButton;
    public ComboBox<String> mainMenuSearchComboBox;
    public TableView<MainPageModel> displayFeaturesTableView;
    public TableColumn<MainPageModel, String> displaySpecColumn, displayValueColumn;
    public MenuBar mainMenuBar;
    public Menu fileMenu, helpMenu;
    public MenuItem exitMenuItem, aboutMenuItem, exportMenuItem, importMenuItem;

    public void searchFeatures() throws SQLException, ClassNotFoundException {
        try {
            ObservableList<MainPageModel> ftrData = searchVisibleFeatures(mainMenuSearchComboBox.getValue());
            summaryNameTextF.setText(visFtr.getName());
            summaryCountTextF.setText(String.valueOf(visFtr.getCount()));
            populateFeatures(ftrData);
        } catch (SQLException e){
            logger.warning("Error occurred while getting Part Feature information from DB." + e);
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
        logger.info(mainMenuSearchComboBox.getValue() + "'s count increased by " + incDecTextField.getText());
    }

    public void decreaseCountPressed() throws SQLException, ClassNotFoundException {
        int decreaseCount = ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
        decreaseCount -=  Integer.parseInt(incDecTextField.getText());
        ListPartDBController.updatePartCount(mainMenuSearchComboBox.getValue(), decreaseCount);
        summaryCountTextF.setText(String.valueOf(ListPartDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
        logger.info(mainMenuSearchComboBox.getValue() + "'s count decreased by " + incDecTextField.getText());
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
        logger.info("Main page loaded.");
    }

    public void exitMenuItem(){
        System.exit(0);
        fh.close();
    }

    public void exportDB() throws SQLException, IOException, ClassNotFoundException {
        ExcelExporter();
    }
    public void aboutMenuItem() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../View/AboutView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(MainPageController.class.getResourceAsStream("../icon.png")));
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
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
