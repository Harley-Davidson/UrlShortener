package start;

import controllers.MainController;
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("URL Shortener");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(840);
        primaryStage.setScene(new Scene(fxmlMain, 840, 450));
        primaryStage.show();

        testData();
    }

    private void testData() {
        CollectionUrlsHistory urlsHistory = new CollectionUrlsHistory();
        urlsHistory.fillTestData();
        urlsHistory.print();
    }

}
