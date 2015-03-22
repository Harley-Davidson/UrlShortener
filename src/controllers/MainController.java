package controllers;

import interfaces.impls.CollectionUrlsHistory;
import interfaces.impls.UrlEncryption;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.UrlItem;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import utils.DialogManager;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;


public class MainController {
    public static final String SITE_ADRESS = "http://localhost:8000/";
    private static CollectionUrlsHistory urlsHistoryImpl = new CollectionUrlsHistory();
    @FXML
    public WebView webviewSitePreview;
    @FXML
    public Label lblInfoLongUrl;
    @FXML
    public Button btnShortenUrl;
    @FXML
    public Label lblInfoShortURL;
    @FXML
    public Button btnCopyUrl;
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
    public TableView<UrlItem> tableUrlsHistory;
    @FXML
    private CustomTextField txtLongUrl;
    @FXML
    private CustomTextField txtShortUrl;
    private Stage mainStage;
    private Parent fxmlClearTable;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage clearTableDialogStage;
    private ClearTableDialogController clearTableDialogController;
    private UrlItem selectedUrlItem = new UrlItem("", "");

    public static int encryptFromShortUrlToId(String shortUrlGiven) {
        return new UrlEncryption().decrypt(shortUrlGiven);
    }

    @FXML
    private void initialize() {
        columnLongUrl.setCellValueFactory(new PropertyValueFactory<UrlItem, String>("longUrl"));
        columnShortUrl.setCellValueFactory(new PropertyValueFactory<UrlItem, String>("shortUrl"));
        columnCreated.setCellValueFactory(new PropertyValueFactory<UrlItem, Long>("registrationTime"));
        setupClearButtonField(txtLongUrl);
        setupClearButtonField(txtShortUrl);
        initListeners();
        initLoader();
        fillData();
        testData();
    }

    private void initLoader() {
        try {

            fxmlLoader.setLocation(getClass().getResource("../fxml/clearTable.fxml"));
            fxmlClearTable = fxmlLoader.load();
            clearTableDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                try {
                    if (event.getClickCount() == 1) {
                        webviewSitePreview.getEngine().load("");
                        selectedUrlItem = (tableUrlsHistory.getSelectionModel().getSelectedItem());
                        String selectedUrlItemLongUrl = selectedUrlItem.getLongUrl();
                        if (selectedUrlItemLongUrl != null)
                            webviewSitePreview.getEngine().load("https://".concat(selectedUrlItemLongUrl));
                    }
                } catch (Exception e) {
                    System.out.println("Problems with URL selection for webview. Try to select once again");
                }
            }
        });
    }

    private void fillData() {
        urlsHistoryImpl.fillArchiveData();
        tableUrlsHistory.setItems(urlsHistoryImpl.getUrlItemList());
    }

    public void clearTable(ActionEvent actionEvent) {
        if (!urlsHistoryImpl.getUrlItemList().isEmpty()) {
            if (clearTableDialogStage == null) {
                clearTableDialogStage = new Stage();
                clearTableDialogStage.setTitle("Clear URL History");
                clearTableDialogStage.setMinWidth(450);
                clearTableDialogStage.setMinHeight(110);
                clearTableDialogStage.setResizable(false);
                clearTableDialogStage.setScene(new Scene(fxmlClearTable));
                clearTableDialogStage.initModality(Modality.WINDOW_MODAL);
                clearTableDialogStage.initOwner(mainStage);
                clearTableDialogStage.showAndWait();
            } else clearTableDialogStage.showAndWait();
            if (ClearTableDialogController.isConfirmed) {
                urlsHistoryImpl.clearHistory();
                webviewSitePreview.getEngine().load("");
            }
        } else DialogManager.showErrorDialog("Error", "URL shortening history is empty!");
    }

    private void setupClearButtonField(CustomTextField customTextField) {
        try {
            Method m = TextFields.class.getDeclaredMethod("setupClearButtonField", TextField.class, ObjectProperty.class);
            m.setAccessible(true);
            m.invoke(null, customTextField, customTextField.rightProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLblUrlsCount() {
        lblUrlsCount.setText("Total URLs shortened: " + (urlsHistoryImpl.getUrlItemList().size()));
    }

    public void shortenURL(ActionEvent actionEvent) {
        boolean shortUrlIsNew = true;
        if (txtLongUrl.getText().equals(""))
            DialogManager.showInfoDialog("Empty URL field", "Please fill the Long URL field");
        else if (!txtLongUrl.getText().contains(".")) {
            DialogManager.showInfoDialog("URL field", "Seems like your URL is not correct");
        } else {
            while (shortUrlIsNew) {
                UrlItem urlItem = new UrlItem(txtLongUrl.getText(), "");
                new UrlEncryption().encrypt(urlItem);
                if (!urlsHistoryImpl.getUrlItemList().isEmpty()) {
                    for (UrlItem urlItem1 : urlsHistoryImpl.getUrlItemList()) {
                        if (!urlItem1.getShortUrl().equals(urlItem.getShortUrl())) {
                            urlsHistoryImpl.add(urlItem);
                            System.out.println(urlItem.getId());
                            shortUrlIsNew = false;
                            break;
                        }
                    }
                } else {
                    urlsHistoryImpl.add(urlItem);
                    shortUrlIsNew = false;
                }
            }
        }
    }

    public void copyUrlToBuffer(ActionEvent actionEvent) {
        String textToCopy;
        if (txtShortUrl.getText().equals("")) textToCopy = selectedUrlItem.getShortUrl();
        else textToCopy = txtShortUrl.getText();
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(textToCopy);
        clpbrd.setContents(stringSelection, null);
    }

    public void goToSite(ActionEvent actionEvent) {
        int id = new UrlEncryption().decrypt(txtShortUrl.getText());
        System.out.println(id);
        for (UrlItem urlItem1 : urlsHistoryImpl.getUrlItemList()) {
            if (urlItem1.getId() == id) {
                txtLongUrl.setText(urlItem1.getLongUrl());
                break;
            }
        }
        try {
            Desktop.getDesktop().browse(new URL("http://".concat(txtLongUrl.getText())).toURI());
        } catch (Exception e) {
            DialogManager.showInfoDialog("Empty URL field", "Please fill the Short URL field");
        }
    }

    public void deleteRow(ActionEvent actionEvent) {
        UrlItem urlItem = tableUrlsHistory.getSelectionModel().getSelectedItem();
        if (urlItem == null) {
            DialogManager.showErrorDialog("Error", "URL is not selected or shortening history is empty");
        } else {
            urlsHistoryImpl.delete(urlItem);
            webviewSitePreview.getEngine().load("");
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void testData() {
        CollectionUrlsHistory urlsHistory = new CollectionUrlsHistory();
        urlsHistory.fillArchiveData();
    }

}
