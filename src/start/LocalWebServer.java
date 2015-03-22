package start;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controllers.MainController;
import utils.DialogManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Max on 17.03.2015.
 */
public class LocalWebServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static String getArchive(Integer id) {
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

    static class MyHandler implements HttpHandler {
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