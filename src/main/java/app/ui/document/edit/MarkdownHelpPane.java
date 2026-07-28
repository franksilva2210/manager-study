package app.ui.document.edit;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MarkdownHelpPane {

    private AnchorPane root;

    public MarkdownHelpPane(MarkdownHelpController controller) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MarkdownHelpPane.fxml"));
            loader.setController(controller);

            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnchorPane getRoot() {
        return root;
    }

}
