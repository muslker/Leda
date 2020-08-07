package Control;

import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import static Util.LogHandler.logger;

public class AboutController implements Initializable {
    public Hyperlink githubHyperLink;
    public ImageView logoImageView;

    public void linkClicked(){
        githubHyperLink.setOnAction(t -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/muslker"));
            } catch (IOException | URISyntaxException e) {
                logger.warning("Error occurred while opening browser. = " + e);
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoImageView.setImage(new Image(AboutController.class.getResourceAsStream("/logo.png")));
        logger.info("About page loaded.");
    }
}
