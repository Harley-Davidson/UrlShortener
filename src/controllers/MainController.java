package controllers;

import interfaces.impls.CollectionUrlsHistory;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private Stage mainStage;
    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private ClearTableDialog clearTableDialogStage;

    public void setUrlsHistoryImpl(CollectionUrlsHistory urlsHistoryImpl) {
        this.urlsHistoryImpl = urlsHistoryImpl;
    }

    @FXML
    private void initialize() {
        columnLongUrl.setCellValueFactory(new PropertyValueFactory<UrlItem, String>("longUrl"));
        columnShortUrl.setCellValueFactory(new PropertyValueFactory<UrlItem, String>("shortUrl"));
        columnCreated.setCellValueFactory(new PropertyValueFactory<UrlItem, Long>("registrationTime"));

        initListeners();

        fillData();
    }

    private void fillData() {
        urlsHistoryImpl.fillTestData();
        tableUrlsHistory.setItems(urlsHistoryImpl.getUrlItemList());
    }

    private void initListeners() {
        urlsHistoryImpl.getUrlItemList().addListener(new ListChangeListener<UrlItem>() {
            @Override
            public void onChanged(Change<? extends UrlItem> c) {
                updateLblUrlsCount();
            }
        });

        tableUrlsHistory.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    webviewSitePreview.getEngine().load(((UrlItem) tableUrlsHistory.getSelectionModel().getSelectedItem()).getLongUrl());
                }
            }
        });
    }

    public void updateLblUrlsCount() {
        lblUrlsCount.setText("Total URLs shortened: " + urlsHistoryImpl.getUrlItemList().size());
    }

    public void showDialog(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        UrlItem selectedUrl = (UrlItem) tableUrlsHistory.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "btnDeleteRow":
                System.out.println("delete " + selectedUrl);
                break;
            case "btnCopyUrl":
                System.out.println("copy " + selectedUrl);
                break;
            case "btnGetSitePreview":
                System.out.println("preview " + selectedUrl);
                break;
        }
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
            stage.setMinWidth(450);
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

    private void initLoader() {
        try {

            fxmlLoader.setLocation(getClass().getResource("../fxml/edit.fxml"));
            fxmlEdit = fxmlLoader.load();
            clearTableDialogStage = fxmlLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent actionEvent) {
        System.out.println("Row is deleted");
    }
}
