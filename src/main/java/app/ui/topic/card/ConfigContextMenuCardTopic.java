package app.ui.topic.card;

import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ConfigContextMenuCardTopic {

    public void configure(CardTopicController controller, Parent root, Button bttMenuTopic) {

        MenuItem open = new MenuItem("Abrir");
        MenuItem rename = new MenuItem("Renomear");
        MenuItem moveLevel = new MenuItem("Mover para nível acima");
        MenuItem remove = new MenuItem("Remover");

        open.setOnAction(e -> {
            controller.openTopic();
        });

        rename.setOnAction(e -> {
            controller.renameTopic();
        });

        remove.setOnAction(e -> {
            controller.removeTopic();
        });

        ContextMenu contextMenu = new ContextMenu(
                open,
                new SeparatorMenuItem(),
                rename,
                moveLevel,
                new SeparatorMenuItem(),
                remove
        );

        bttMenuTopic.setOnAction(event -> {

            controller.select();

            if (contextMenu.isShowing()) {
                contextMenu.hide();
                return;
            }

            Bounds bounds = bttMenuTopic.localToScreen(bttMenuTopic.getBoundsInLocal());

            contextMenu.show(
                    root,
                    bounds.getMinX(),
                    bounds.getMaxY()
            );
        });

        root.setOnContextMenuRequested(event -> {

            controller.select();

            contextMenu.show(
                    root,
                    event.getScreenX(),
                    event.getScreenY()
            );
        });

        root.setOnMousePressed(e -> contextMenu.hide());
    }

}
