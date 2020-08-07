import Util.LogHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        LogHandler.init();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/MainPageView.fxml"));
        Scene mainScene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
        stage.setTitle("Electronical Part Tracker");
        stage.setScene(mainScene);
        stage.show();
    }
}
