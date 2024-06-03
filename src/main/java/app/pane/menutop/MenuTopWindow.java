package app.pane.menutop;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuTopWindow {

    private VBox root;
    private MenuTopControl controller;

    public VBox getRoot() {
        return root;
    }

    public void setRoot(VBox root) {
        this.root = root;
    }

    public MenuTopControl getController() {
        return controller;
    }

    public void setController(MenuTopControl controller) {
        this.controller = controller;
    }

    public VBox buildRoot() {
        FXMLLoader rootFxml = new FXMLLoader();
        rootFxml.setLocation(MenuTopWindow.class.getResource("MenuTopWindow.fxml"));
        rootFxml.setController(this.controller);

        try {
            root = rootFxml.load();
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
