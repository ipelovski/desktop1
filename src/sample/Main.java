package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Settings settings;
    private State state;
    private Controller controller;
    private Scheduler scheduler;
    private WebServer webServer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        controller = new Controller(this);
        fxmlLoader.setController(controller);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        settings = new Settings();
        state = new State();
        scheduler = new Scheduler(settings, state, controller);
//        scheduler.start();

        webServer = new WebServer();
        webServer.start();
    }

    @Override
    public void stop() {
        webServer.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
