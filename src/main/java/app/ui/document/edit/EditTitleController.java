package app.ui.document.edit;

import app.ui.util.ValidateControlFx;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTitleController implements Initializable {

    @FXML
    private Button bttCancel;

    @FXML
    private Button bttOk;

    @FXML
    private TextField txtTitle;

    private Stage stage;

    private String titleCurrent;

    private String newTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bttOk.setOnAction(event -> {
            save();
        });

        bttCancel.setOnAction(event -> {
            stage.close();
        });

        txtTitle.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                save();
            }
        });

        txtTitle.setText(titleCurrent);
    }

    private void save() {
        ValidateControlFx validate = new ValidateControlFx();
        validate.setControl(txtTitle);
        validate.setError(false);
        validate.validateControl();

        if (validate.getError()) {
            txtTitle.setPromptText("Título invalido.");
            return;
        }

        newTitle = txtTitle.getText();

        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTitleCurrent(String titleCurrent) {
        this.titleCurrent = titleCurrent;
    }

    public String getNewTitle() {
        return newTitle;
    }
}
