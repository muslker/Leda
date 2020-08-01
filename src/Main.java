import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Util.DatabaseConnector;

import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {  DatabaseConnector.dbConnect(); launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/MainMenuView.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Electronical Component Tracker");
        stage.show();
    }
}
