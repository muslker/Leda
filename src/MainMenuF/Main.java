package MainMenuF;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        stage.setTitle("Electronical Component Tracker");
        stage.setScene(new Scene(root, 300, 275));

        stage.show();
        stage.setWidth(500);
        stage.setHeight(500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
