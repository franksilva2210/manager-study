package app.ui.document.edit;

import app.application.document.DocumentDTO;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.fxmisc.richtext.CodeArea;

import java.util.Objects;

public class EditorDocumentUIBinder {

    public static void bind(EditorDocumentFacade facade, DocumentState state) {
        bindTitle(facade.getLblTitle(), state);
        bindCodeArea(facade.getCodeArea(), state);
        bindButtonSave(facade.getBttSave(), state);
        bindButtonCancel(facade.getBttCancel(), state);
    }

    private static void bindTitle(Label lblTitle, DocumentState state) {
        lblTitle.textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            DocumentDTO dto = state.getDocumentDTO();

                            boolean isEditing = !Objects.equals(dto.getTitle(), state.getTitle())
                                    || !Objects.equals(dto.getContent(), state.getContent());

                            return isEditing ? state.getTitle() + " *" : state.getTitle();
                        },
                        state.documentDtoProperty(),
                        state.titleProperty(),
                        state.contentProperty()
                )
        );
    }

    private static void bindCodeArea(CodeArea codeArea, DocumentState state) {
        codeArea.replaceText(state.getContent());
        codeArea.getUndoManager().forgetHistory();

        codeArea.richChanges().subscribe(change -> {
            String text = codeArea.getText();
            if (!Objects.equals(text, state.getContent())) {
                state.contentProperty().set(text);
            }
        });

        state.contentProperty().addListener((obs, oldValue, newValue) -> {
            String text = codeArea.getText();
            if (!Objects.equals(text, newValue)) {
                codeArea.replaceText(newValue);
                codeArea.getUndoManager().forgetHistory();
            }
        });
    }

    private static void bindButtonSave(Button bttSave, DocumentState state) {
        bttSave.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            DocumentDTO dto = state.getDocumentDTO();

                            if (dto == null) {
                                return true;
                            }

                            boolean isNotModified = Objects.equals(dto.getTitle(), state.getTitle())
                                    && Objects.equals(dto.getContent(), state.getContent());

                            return isNotModified;
                        },
                        state.documentDtoProperty(),
                        state.titleProperty(),
                        state.contentProperty()
                )
        );
    }

    private static void bindButtonCancel(Button bttCancel, DocumentState state) {
        bttCancel.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> {
                            DocumentDTO dto = state.getDocumentDTO();

                            if (dto == null) {
                                return true;
                            }

                            boolean isNotModified = Objects.equals(dto.getTitle(), state.getTitle())
                                    && Objects.equals(dto.getContent(), state.getContent());

                            return isNotModified;
                        },
                        state.documentDtoProperty(),
                        state.titleProperty(),
                        state.contentProperty()
                )
        );
    }
}
