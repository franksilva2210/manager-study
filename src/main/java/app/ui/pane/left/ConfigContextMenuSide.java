package app.ui.pane.left;

import app.application.study.StudyDTO;
import javafx.scene.control.*;

import java.util.function.Consumer;

public class ConfigContextMenuSide {

    public void configure(
            ListCell<StudyDTO> cell,
            Consumer<StudyDTO> renameStudy,
            Consumer<StudyDTO> deleteStudy) {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuEdit = new MenuItem("Renomear");
        MenuItem menuRemove = new MenuItem("Deletar");

        contextMenu.getItems().addAll(menuEdit, menuRemove);

        menuEdit.setOnAction(e -> {
            if (cell.getItem() != null) {
                renameStudy.accept(cell.getItem());
            }
        });

        menuRemove.setOnAction(e -> {
            if (cell.getItem() != null) {
                deleteStudy.accept(cell.getItem());
            }
        });

        cell.emptyProperty().addListener((obs, oldValue, empty) ->
                cell.setContextMenu(empty ? null : contextMenu));

    }

}
