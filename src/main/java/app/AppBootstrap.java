package app;

import app.infra.SQLiteDataBaseConfig;
import app.infra.FlywayConfig;
import app.infra.HibernateUtil;
import app.ui.main.ScreenMainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppBootstrap extends Application {

    @Override
    public void start(Stage primaryStage) {

        SQLiteDataBaseConfig.initialize();
        FlywayConfig.migrate();
        HibernateUtil.initialize();

        ScreenMainWindow screenMainWindow = new ScreenMainWindow(primaryStage);
        screenMainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
