package app.ui.topic.card;

import app.application.topic.TopicDTO;
import app.ui.main.ModeUpdateItem;
import app.ui.main.ScreenMainController;
import app.ui.main.ScreenMainState;
import app.ui.message.MessageConfirmController;
import app.ui.message.MessageConfirmWindow;
import app.ui.message.MessageInfoController;
import app.ui.message.MessageInfoWindow;
import app.ui.pane.left.PaneLeftController;
import app.ui.pane.right.PaneRightController;
import app.ui.pane.right.topics.PaneTopicsController;
import app.ui.topic.register.RegisterTopicController;
import app.ui.topic.register.RegisterTopicWindow;
import app.ui.util.BusinessException;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class CardTopicController {

    private final Stage stage;
    private final TopicDTO topic;
    private final ScreenMainState screenMainState;
    private final ScreenMainController screenMainController;
    private final PaneLeftController paneLeftController;
    private final PaneRightController paneRightController;
    private final PaneTopicsController paneTopicsController;

    private final CardTopicService service = new CardTopicService();

    private Parent root;

    public CardTopicController(
            Stage stage, TopicDTO topic,
            ScreenMainState screenMainState,
            ScreenMainController screenMainController,
            PaneLeftController paneLeftController,
            PaneRightController paneRightController,
            PaneTopicsController paneTopicsController) {

        this.stage = stage;
        this.topic = topic;
        this.screenMainState = screenMainState;
        this.screenMainController = screenMainController;
        this.paneLeftController = paneLeftController;
        this.paneRightController = paneRightController;
        this.paneTopicsController = paneTopicsController;

        PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");

        this.paneTopicsController.getCardSelection().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            root.pseudoClassStateChanged(
                    SELECTED,
                    topic.equals(newValue)
            );
        });
    }

    public TopicDTO getTopic() {
        return topic;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public void select() {
        paneTopicsController.getCardSelection().select(topic);
    }

    public void openTopic() {
        if (!screenMainController.confirmChangeStudyOrTopic())
            return;

        screenMainController.openItemSelected(topic);
        paneRightController.loadTabsDocument();
    }

    public void removeTopic() {
        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente remover o tópico selecionado: \n" +
                topic.getTitle() + "?\n" +
                "todos os subtópicos pertencentes a ele\n" +
                "também serão removidos!"
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.removeTopic(topic);
            screenMainController.reloadScreen(topic, ModeUpdateItem.REMOVE);
            screenMainState.refreshItemSelected();
        }
    }

    public void renameTopic() {
        RegisterTopicController registerTopicController = new RegisterTopicController();
        registerTopicController.setTopicDto(topic);

        RegisterTopicWindow registerTopicWindow = new RegisterTopicWindow(stage, registerTopicController);
        registerTopicWindow.showScreen();

        screenMainController.reloadScreen(topic, ModeUpdateItem.UPDATE);
        screenMainState.refreshItemSelected();
    }

    public void moveTopicToTopic(Long idTopicDragged) {
        if (idTopicDragged == null) {
            return;
        }

        TopicDTO topicDragged = service.loadSimpleTopic(idTopicDragged);

        MessageConfirmController controller = new MessageConfirmController();
        controller.setConfirm(false);
        controller.setMsgUser(
                "Deseja realmente mover o tópico selecionado?\n" +
                "Origem: " + topicDragged.getTitle() + "\n" +
                "Destino: " + topic.getTitle() + "\n" +
                "Todos os sub tópicos também serão movidos."
        );

        MessageConfirmWindow window = new MessageConfirmWindow(stage, controller);
        window.showScreen();

        if (controller.getConfirm()) {
            service.moveTopicToTopic(topicDragged, topic);
            screenMainState.refreshItemSelected();
        }
    }

    public void moveStudyToTopic(Long idStudyDragged) {

    }

    public void moveOneLevelUp() {
        try {
            service.moveOneLevelUp(topic);
            screenMainState.refreshItemSelected();
        } catch (BusinessException e) {
            MessageInfoController controller = new MessageInfoController();
            controller.setMsgUser(e.getMessage());
            MessageInfoWindow window = new MessageInfoWindow(stage, controller);
            window.showScreen();
        }
    }

    public void convertToStudy() {
        service.convertTopicToStudy(topic.getId());
        screenMainState.refreshItemSelected();
        paneLeftController.refreshStudies();
        MessageInfoController controller = new MessageInfoController();
        controller.setMsgUser(
                "Tópico convertido com sucesso!"
        );
        MessageInfoWindow window = new MessageInfoWindow(stage, controller);
        window.showScreen();
    }

}
