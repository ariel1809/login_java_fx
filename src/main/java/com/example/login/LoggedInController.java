package com.example.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button bt_logout;
    @FXML
    private Label label_welcome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_logout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent,"sample.fxml","Log In!",null));
    }

    public void setUserInformations(String username){
        label_welcome.setText("Bienvenue "+username+" !");
    }
}
