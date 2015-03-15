package controllers;

/**
 * Created by Max on 14.03.2015.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ClearTable {
    public boolean answerClicked;

    @FXML
    public Button btnClearHistoryConfirm;

    @FXML
    public Button btnClearHistoryCancel;

    public void clearTableConfirmed(ActionEvent actionEvent) {
        System.out.println("Clearance confirmed");
        answerClicked = true;
    }

    public void clearTableNotConfirmed(ActionEvent actionEvent) {
        System.out.println("Clearance rejected");
        answerClicked = true;
    }

    public boolean isAnswerClicked() {
        return answerClicked;
    }
}
