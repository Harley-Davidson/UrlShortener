package start;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DialogManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        runServer();
        launch(args);
    }

    private static void runServer() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/", new RequestHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            System.out.println("Server is already running");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //load MainController and appropriate main.fxml to it
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../fxml/main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        //set stage for the main window
        primaryStage.setTitle("URL Shortener");
        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(840);
        primaryStage.setScene(new Scene(fxmlMain, 840, 450));
        primaryStage.show();

    }


    /**
     * Gets the request in the browser site address field,
     * on that page returns the informational message
     * and decrypts short URL from browser line to original long URL
     * and redirects user to original URL
     */
    static class RequestHandler implements HttpHandler {
        /**
         * Gets the history of URL shortening from .txt archive and
         * after matching with given id returns long Url
         * <p>
         * {@param id}
         * Integer
         *
         * @return String longUrl
         */
        private static String getArchive(Integer id) {
            HashMap<Integer, String> idUrlPair = new HashMap<>();
            try {
                BufferedReader in = new BufferedReader(new FileReader("../UrlShortener/src/interfaces/impls/history.txt"));

                while (true) {
                    String str = in.readLine();
                    if (str == null || str.equals("")) break;
                    String[] ar = str.split(", ");
                    idUrlPair.put(Integer.parseInt(ar[2]), ar[0]);
                }
                in.close();
            } catch (IOException e) {
                System.out.println("File Read Error");
            }
            return idUrlPair.get(id);
        }

        public void handle(HttpExchange t) throws IOException {
            String requestURL = t.getRequestURI().toString().substring(1);
            Integer id = MainController.encryptFromShortUrlToId(requestURL);
            String longUrl = getArchive(id);
            String response = "You were redirected from short URL: '".
                    concat(requestURL).concat("' to long URL: '").
                    concat(longUrl).concat("'");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            try {
                Desktop.getDesktop().browse(new URL("http://".concat(longUrl)).toURI());
            } catch (Exception e) {
                DialogManager.showInfoDialog("Empty URL field", "Please fill the Short URL field");
            }
        }
    }
}
