package app;

import app.ui.main.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppBootstrap extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow(primaryStage);
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
