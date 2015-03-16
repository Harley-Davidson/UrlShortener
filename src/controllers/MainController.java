package controllers;

import interfaces.impls.CollectionUrlsHistory;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.UrlItem;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.lang.reflect.Method;


public class MainController {
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
    public TableView tableUrlsHistory;
    @FXML
    private CustomTextField txtLongUrl;
    @FXML
    private CustomTextField txtShortUrl;
    private CollectionUrlsHistory urlsHistoryImpl = new CollectionUrlsHistory();
    private Stage mainStage;
    private Parent fxmlClearTable;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Stage clearTableDialogStage;
    private ClearTableDialogController clearTableDialogController;

//    public void setUrlsHistoryImpl(CollectionUrlsHistory urlsHistoryImpl) {
//        this.urlsHistoryImpl = urlsHistoryImpl;
//    }

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
    }

    private void fillData() {
        urlsHistoryImpl.fillTestData();
        tableUrlsHistory.setItems(urlsHistoryImpl.getUrlItemList());
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

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        UrlItem selectedUrl = (UrlItem) tableUrlsHistory.getSelectionModel().getSelectedItem();

        switch (clickedButton.getId()) {
            case "btnDeleteRow":
                urlsHistoryImpl.delete((UrlItem) tableUrlsHistory.getSelectionModel().getSelectedItem());
                break;
            case "btnCopyUrl":
                System.out.println("copy " + selectedUrl);
                break;
            case "btnClearHistory":
                urlsHistoryImpl.clearHistory();
                break;
        }
    }

    public void shortenURL(ActionEvent actionEvent) {
        System.out.println("Shortened URL");
    }

    public void copyUrlToBuffer(ActionEvent actionEvent) {
        System.out.println("URL copied");
    }

    public void goToSite(ActionEvent actionEvent) {
        System.out.println("Fancy Site");
    }

    public void clearTable(ActionEvent actionEvent) {
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
        if (clearTableDialogController.btnClearHistoryConfirm.isPressed()) urlsHistoryImpl.clearHistory();
    }

    private void initLoader() {
        try {

            fxmlLoader.setLocation(getClass().getResource("../fxml/clearTable.fxml"));
            fxmlClearTable = fxmlLoader.load();
            clearTableDialogController = fxmlLoader.getController();

        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent actionEvent) {
        urlsHistoryImpl.delete((UrlItem) tableUrlsHistory.getSelectionModel().getSelectedItem());
        webviewSitePreview.getEngine().load("");
        System.out.println("Row is deleted");
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
