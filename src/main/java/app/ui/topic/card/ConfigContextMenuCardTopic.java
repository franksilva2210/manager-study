package app.ui.topic.card;

import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ConfigContextMenuCardTopic {

    public void configure(CardTopicController controller, Parent root) {

        MenuItem open = new MenuItem("Abrir");
        MenuItem rename = new MenuItem("Renomear");
        MenuItem newTopic = new MenuItem("Mover nível acima");
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
                newTopic,
                new SeparatorMenuItem(),
                remove
        );

        root.setOnContextMenuRequested(event -> {

            controller.select();

            contextMenu.show(
                    root,
                    event.getScreenX(),
                    event.getScreenY()
            );
        });
    }

}
