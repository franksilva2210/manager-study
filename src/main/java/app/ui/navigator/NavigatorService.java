package app.ui.navigator;

import app.AppBootstrap;
import app.infra.HibernateUtil;
import javafx.stage.Stage;

public class NavigatorService {

    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
    }

    public static void restart() {

        try {

            HibernateUtil.shutdown();

            primaryStage.close();

            Stage newStage = new Stage();

            new AppBootstrap().start(newStage);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Erro ao reiniciar aplicação.",
                    e
            );
        }
    }

}
