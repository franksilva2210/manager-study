package app.ui.topic.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class CardTopic {

    private AnchorPane root;
    private Label titleTopic;
    private Button bttMenuTopic;
    private Button bttOpenTopic;

    public CardTopic(CardTopicController controller) {
        buildCard(controller);
        loadController(controller);
    }

    private void buildCard(CardTopicController controller) {
        root = new AnchorPane();
        root.setPrefSize(127, 100);
        root.setMinSize(127, 100);
        root.setMaxSize(127, 100);
        root.getStylesheets().add(getClass().getResource("card.css").toExternalForm());
        root.getStyleClass().add("card");

        VBox content = new VBox();
        AnchorPane.setTopAnchor(content, 0.0);
        AnchorPane.setBottomAnchor(content, 0.0);
        AnchorPane.setLeftAnchor(content, 0.0);
        AnchorPane.setRightAnchor(content, 0.0);

        //----------------------------
        // Cabeçalho
        //----------------------------

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPrefHeight(25);
        header.setStyle("-fx-border-color: #dddddd; -fx-border-width: 0 0 1 0; -fx-background-color: #F7F7F7");

        titleTopic = new Label();
        titleTopic.setMaxWidth(Double.MAX_VALUE);
        titleTopic.setWrapText(false);
        titleTopic.setTextOverrun(OverrunStyle.ELLIPSIS);

        Tooltip tooltip = new Tooltip(controller.getTopic().getTitle());

        tooltip.setShowDelay(Duration.millis(200));
        tooltip.setShowDuration(Duration.seconds(10));
        tooltip.setHideDelay(Duration.millis(100));
        tooltip.setStyle("-fx-background-color: white");

        titleTopic.setTooltip(tooltip);

        HBox.setMargin(titleTopic, new Insets(0, 0, 0, 2));
        HBox.setHgrow(titleTopic, Priority.ALWAYS);

        bttMenuTopic = new Button();

        ImageView imageInfo = new ImageView(
                new Image(CardTopic.class.getResourceAsStream("dots.png"))
        );

        imageInfo.setFitWidth(10);
        imageInfo.setFitHeight(10);

        bttMenuTopic.setGraphic(imageInfo);
        bttMenuTopic.setFocusTraversable(false);
        bttMenuTopic.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 2;"
        );

        header.getChildren().addAll(titleTopic, bttMenuTopic);

        //----------------------------
        // Corpo
        //----------------------------

        HBox body = new HBox();
        body.setPrefHeight(60);

        //----------------------------
        // Rodapé
        //----------------------------

        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPrefHeight(25);

        ImageView image = new ImageView(
                new Image(CardTopic.class.getResourceAsStream("next.png"))
        );
        image.setFitWidth(16);
        image.setFitHeight(15);
        image.setPreserveRatio(true);

        bttOpenTopic = new Button();
        bttOpenTopic.setPrefSize(35, 21);
        bttOpenTopic.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        bttOpenTopic.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        bttOpenTopic.setGraphic(image);

        HBox.setMargin(bttOpenTopic, new Insets(0, 2, 2, 0));

        footer.getChildren().add(bttOpenTopic);

        //----------------------------

        content.getChildren().addAll(header, body, footer);

        root.getChildren().add(content);
    }

    //----------------------------
    // Controller
    //----------------------------

    private void loadController(CardTopicController controller) {

        root.setOnMouseClicked(event -> {
            controller.select();

            root.requestFocus();

            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                controller.openTopic();
            }
        });

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                controller.openTopic();
            }
        });

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {
                controller.removeTopic();
            }
        });

        titleTopic.setText(controller.getTopic().getTitle());

        bttOpenTopic.setOnAction(event -> controller.openTopic());

        configureContextMenu(controller);

        configureDragDropped(controller);
    }

    private void configureContextMenu(CardTopicController controller) {
        ConfigContextMenuCardTopic configContextMenu = new ConfigContextMenuCardTopic();
        configContextMenu.configure(controller, root, bttMenuTopic);
    }

    private void configureDragDropped(CardTopicController controller) {
        ConfigDragDroppedCardTopic configDragDroppedCardTopic = new ConfigDragDroppedCardTopic();
        configDragDroppedCardTopic.configureDragAndDropped(controller, root);
    }

    public Parent getRoot() {
        return root;
    }
}
