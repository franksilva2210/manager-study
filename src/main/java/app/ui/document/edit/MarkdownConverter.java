package app.ui.document.edit;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;

public class MarkdownConverter {

    private static final Parser PARSER =
            Parser.builder().build();

    private static final HtmlRenderer RENDERER =
            HtmlRenderer.builder().build();

    public static String toHtml(String markdown) {
        Node document = PARSER.parse(markdown);
        return RENDERER.render(document);
    }
}
