package start;

import interfaces.impls.CollectionUrlsHistory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
        primaryStage.setTitle("URL Shortener");
        primaryStage.setMinHeight(400);
        primaryStage.setMinWidth(700);
        primaryStage.setScene(new Scene(root, 780, 450));
        primaryStage.show();

        testData();
    }

    private void testData() {
        CollectionUrlsHistory urlsHistory = new CollectionUrlsHistory();
        urlsHistory.fillTestData();
        urlsHistory.print();
    }

}
