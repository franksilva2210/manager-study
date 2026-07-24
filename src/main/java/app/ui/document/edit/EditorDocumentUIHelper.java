package app.ui.document.edit;

import app.ui.util.TooltipUtils;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class EditorDocumentUIHelper {

    public void showPane(Node nodeToShow, StackPane pane) {
        for (Node node : pane.getChildren()) {

            boolean show = node == nodeToShow;

            node.setVisible(show);
            node.setManaged(show);
        }
    }

    public void configurateTooltip(EditorDocumentFacade facade) {
        facade.getLblTitle().setTooltip(TooltipUtils.create("Dê dois cliques para editar o título"));
        facade.getBttPreview().setTooltip(TooltipUtils.create("Visualiza texto formatado"));
        facade.getBttEdit().setTooltip(TooltipUtils.create("Edita texto"));
        facade.getBttImport().setTooltip(TooltipUtils.create("Importa documento markdown .md do computador"));
        facade.getBttExport().setTooltip(TooltipUtils.create("Exporta documento markdown .md para computador"));
        facade.getBttRemove().setTooltip(TooltipUtils.create("Deleta documento markdown"));
        facade.getBttBold().setTooltip(TooltipUtils.create("Negrito"));
        facade.getBttItalic().setTooltip(TooltipUtils.create("Itálico"));
        facade.getBttAttachImg().setTooltip(TooltipUtils.create("Anexo de imagem"));
        facade.getBttBlocCod().setTooltip(TooltipUtils.create("Bloco de código"));
    }

}
