package Control;

import DatabaseController.MainPageDBController;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static DatabaseController.MainPageDBController.searchVisibleFeatures;
import static DatabaseController.MainPageDBController.visFtr;
import static Util.DataExporter.ExcelExporter;
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
    public String exportPath;

    public void searchFeatures() throws SQLException, ClassNotFoundException, NullPointerException {
        try {
            if (mainMenuSearchComboBox.getSelectionModel().isEmpty())
                mainMenuSearchComboBox.getSelectionModel().clearSelection();
            else {
                ObservableList<MainPageModel> ftrData = searchVisibleFeatures(mainMenuSearchComboBox.getValue());
                if (ftrData.isEmpty()) System.out.println("Data list is empty.");
                else {
                    summaryNameTextF.setText(visFtr.getName());
                    summaryCountTextF.setText(String.valueOf(visFtr.getCount()));
                    populateFeatures(ftrData);
                }
            }
        } catch (SQLException | ClassNotFoundException | NullPointerException e){
            logger.warning("Error occurred while getting Part Feature information from DB." + e);
            System.out.println("Error occurred while getting Part Feature information from DB." + e);
            throw e;
        }
    }

    public void populateFeatures(ObservableList<MainPageModel> featureData) {
        displayFeaturesTableView.setItems(featureData);
    }

    public void increaseCountPressed() throws SQLException, ClassNotFoundException {
        if (mainMenuSearchComboBox.getSelectionModel().isEmpty())
            System.out.println("Please select a part.");
        else {
            int increaseCount = MainPageDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
            increaseCount += Integer.parseInt(incDecTextField.getText());
            MainPageDBController.updatePartCount(mainMenuSearchComboBox.getValue(), increaseCount);
            summaryCountTextF.setText(String.valueOf(MainPageDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
            logger.info(mainMenuSearchComboBox.getValue() + "'s count increased by " + incDecTextField.getText());
        }
    }

    public void decreaseCountPressed() throws SQLException, ClassNotFoundException {
        if (mainMenuSearchComboBox.getSelectionModel().isEmpty())
            System.out.println("Please select a part.");
        else {
            int decreaseCount = MainPageDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount();
            decreaseCount -= Integer.parseInt(incDecTextField.getText());
            MainPageDBController.updatePartCount(mainMenuSearchComboBox.getValue(), decreaseCount);
            summaryCountTextF.setText(String.valueOf(MainPageDBController.searchPart(mainMenuSearchComboBox.getValue()).getCount()));
            logger.info(mainMenuSearchComboBox.getValue() + "'s count decreased by " + incDecTextField.getText());
        }
    }

    public static void filterParts(ComboBox<String> searchCombo){
        ObservableList<String> parts = null;
        try {
            parts = MainPageDBController.searchPartNames();
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
    }

    public void exportDB() throws SQLException, IOException, ClassNotFoundException {
        try {
            FileChooser fil_chooser = new FileChooser();
            fil_chooser.setInitialFileName("Leda_DB.xlsx");
            fil_chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel File", "*.xlsx"));
            File file = fil_chooser.showSaveDialog(new Stage());
            if (file != null) {
                exportPath = file.getAbsolutePath();
                logger.info("Save location selected successfully.");
            }
            else logger.warning("Save location not selected.");
        }
        catch (Exception e) {
            logger.warning("Error occurred while getting file path to save excel document.");
            e.printStackTrace();
        }
        ExcelExporter(exportPath);
    }
    public void aboutMenuItem() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/AboutView.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
//        stage.getIcons().add(new Image(MainPageController.class.getResourceAsStream("../icon.png")));
        stage.setTitle("About");
        stage.setScene(scene);
        stage.show();
    }
    public void goto_ListPartPage(ActionEvent event) throws IOException {
        Parent ListPartPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/ListPartView.fxml")));
        Scene ListPartScene = new Scene(ListPartPage);
        Stage ListPartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        ListPartStage.setScene(ListPartScene);
        ListPartStage.show();
    }
    public void goto_DefinePartPage(ActionEvent event) throws IOException {
        Parent DefinePartPage = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/DefinePartView.fxml")));
        Scene DefinePartScene = new Scene(DefinePartPage);
        Stage DefinePartStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        DefinePartStage.setScene(DefinePartScene);
        DefinePartStage.show();
    }
}
