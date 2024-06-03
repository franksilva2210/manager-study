package app.pane.menutop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuTopControl implements Initializable {

    @FXML private MenuItem menuClose;
    @FXML private MenuItem menuStudy;
    @FXML private MenuItem menuViewMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuClose.setOnAction((ActionEvent event) -> {
            System.exit(0);
        });
    }
}
