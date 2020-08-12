import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

import static Util.LogHandler.LogHandle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("View/MainPageView.fxml")));
        Scene mainScene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/icon.png")));
        stage.setTitle("Electronical Part Tracker");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        LogHandle();
        launch(args);
    }
}
