import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {  launch(args); }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View/MainPageView.fxml"));
        Scene mainScene = new Scene(root);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/ico.png")));
        stage.setTitle("Electronical Part Tracker");
        stage.setScene(mainScene);
        stage.show();
    }
}
