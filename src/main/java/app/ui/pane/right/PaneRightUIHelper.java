package app.ui.pane.right;

import app.ui.document.edit.EditorDocumentController;
import app.ui.util.TooltipUtils;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PaneRightUIHelper {

    public EditorDocumentController verifyDocumentEditingOrNotSave(
            TabPane tabPaneStudy,
            Tab tabMain,
            Tab tabAdd) {

        for (Tab tab : tabPaneStudy.getTabs()) {
            if (tab == tabMain || tab == tabAdd) {
                continue;
            }

            EditorDocumentController editorDocumentController = (EditorDocumentController) tab.getUserData();

            if (editorDocumentController.getDocumentDto().getId() == null || editorDocumentController.isEditing()) {
                return editorDocumentController;
            }
        }

        return null;
    }

    public void configurateTooltip(Tab tabAdd) {
        tabAdd.setTooltip(TooltipUtils.create("Adiciona novo texto"));
    }
}
