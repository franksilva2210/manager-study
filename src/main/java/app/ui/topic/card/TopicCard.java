package app.ui.topic.card;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TopicCard {

    private final AnchorPane root;

    private final Label titleTopic;
    private final Button bttOpenTopic;

    public TopicCard(TopicCardController controller) {

        root = new AnchorPane();
        root.setPrefSize(160, 110);
        root.setMinSize(160, 110);
        root.setMaxSize(160, 110);
        root.setStyle("-fx-border-color: #dddddd;");

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
        header.setStyle("-fx-border-color: #dddddd; -fx-border-width: 0 0 1 0;");

        titleTopic = new Label();
        HBox.setMargin(titleTopic, new Insets(0, 0, 0, 2));

        header.getChildren().add(titleTopic);

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
                new Image(TopicCard.class.getResourceAsStream("next.png"))
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

        //----------------------------
        // Controller
        //----------------------------

        titleTopic.setText(controller.getTopic().getTitle());

        bttOpenTopic.setOnAction(e -> controller.openTopic());
    }

    public Parent getRoot() {
        return root;
    }
}
