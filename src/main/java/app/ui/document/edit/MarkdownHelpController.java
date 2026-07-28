package app.ui.document.edit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class MarkdownHelpController implements Initializable {

    @FXML
    private TextFlow titlesFlow;

    @FXML
    private TextFlow boldFlow;

    @FXML
    private TextFlow italicFlow;

    @FXML
    private TextFlow textScratchedFlow;

    @FXML
    private TextFlow listsFlow;

    @FXML
    private TextFlow imgFlow;

    @FXML
    private TextFlow linkFlow;

    @FXML
    private Hyperlink hiperlinkCommon;

    @FXML
    private Hyperlink hiperlinkMarkSintax;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Text titles = new Text(
                "# Título\n" +
                "## Seção\n" +
                "### Subseção"
        );

        Text bold = new Text(
                "**texto**"
        );

        Text italic = new Text(
                "*texto*"
        );

        Text textScratched = new Text(
                "~~Texto riscado~~"
        );

        Text lists = new Text(
                "- Frutas\n" +
                "  - Maça\n" +
                "  - Pera\n" +
                "1. Primeiro\n" +
                "2. Segundo\n" +
                "3. Terceiro"
        );

        Text img = new Text(
                "![Descrição opcional](caminho em disco ou URL da imagem)"
        );

        Text link = new Text(
                "[Texto do link](https://...)"
        );

        titlesFlow.getChildren().add(titles);
        boldFlow.getChildren().add(bold);
        italicFlow.getChildren().add(italic);
        textScratchedFlow.getChildren().add(textScratched);
        listsFlow.getChildren().add(lists);
        imgFlow.getChildren().add(img);
        linkFlow.getChildren().add(link);

        hiperlinkCommon.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(
                        URI.create("https://commonmark.org/help/")
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        hiperlinkMarkSintax.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(
                        URI.create("https://markdown.org/syntax/?")
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
