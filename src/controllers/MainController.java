package controllers;

import interfaces.impls.CollectionUrlsHistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.UrlItem;

import java.io.IOException;


public class MainController {
    @FXML
    public WebView webviewSitePreview;
    @FXML
    public Button btnGetSitePreview;
    @FXML
    public Label lblInfoLongUrl;
    @FXML
    public TextField txtLongUrl;
    @FXML
    public Button btnShortenUrl;
    @FXML
    public Label lblInfoShortURL;
    @FXML
    public Button btnCopyUrl;
    @FXML
    public TextField txtShortUrl;
    @FXML
    public Button btnGoToUrl;
    @FXML
    public Label lblUrlsCount;
    @FXML
    public Button btnClearHistory;
    @FXML
    public Button btnDeleteRow;
    @FXML
    public TableColumn<UrlItem, String> columnLongUrl;
    @FXML
    public TableColumn<UrlItem, String> columnShortUrl;
    @FXML
    public TableColumn<UrlItem, Long> columnCreated;
    @FXML
    public TableView tableUrlsHistory;
    private CollectionUrlsHistory urlsHistoryImpl = new CollectionUrlsHistory();

    @FXML
    private void initialize() {
        columnLongUrl.setCellValueFactory(new PropertyValueFactory<UrlItem, String>("longUrl"));
        columnShortUrl.setCellValueFactory(new PropertyValueFactory<UrlItem, String>("shortUrl"));
        columnCreated.setCellValueFactory(new PropertyValueFactory<UrlItem, Long>("registrationTime"));

        urlsHistoryImpl.fillTestData();
        tableUrlsHistory.setItems(urlsHistoryImpl.getUrlItemList());

        updateLblUrlsCount();

    }

    public void updateLblUrlsCount() {
        lblUrlsCount.setText("Total URLs shortened: " + urlsHistoryImpl.getUrlItemList().size());
    }

    public void shortenURL(ActionEvent actionEvent) {
        System.out.println("Shortened URL");
    }

    public void getSitePreview(ActionEvent actionEvent) {
        System.out.println("Fancy Site");
    }

    public void copyUrlToBuffer(ActionEvent actionEvent) {
        System.out.println("URL copied");
    }

    public void goToSite(ActionEvent actionEvent) {
        System.out.println("Fancy Site");
    }

    public void clearTable(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/clearTable.fxml"));
            stage.setTitle("Clear URL History");
            stage.setMinWidth(405);
            stage.setMinHeight(110);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent actionEvent) {
        System.out.println("Row is deleted");
    }
}
