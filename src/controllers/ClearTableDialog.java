package controllers;

/**
 * Created by Max on 14.03.2015.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClearTableDialog {
    public boolean answerClicked;

    @FXML
    public Button btnClearHistoryConfirm;

    @FXML
    public Button btnClearHistoryCancel;

    public void clearTableConfirmed(ActionEvent actionEvent) {
        clearTableClose(actionEvent);

    }

    public void clearTableClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public boolean isAnswerClicked() {
        return answerClicked;
    }
}
