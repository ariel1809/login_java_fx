package com.example.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button bt_logout;
    @FXML
    private Label label_welcome;
    @FXML
    private Button bt_send_mail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_logout.setOnAction(actionEvent -> DBUtils.changeScene(actionEvent,"sample.fxml","Log In!",null, null));
    }

    public void setUserInformations(String username){
        label_welcome.setText("Bienvenue "+username+" !");
    }

    public void sendMail(String username){
        bt_send_mail.setOnAction(actionEvent -> {
            try {
                DBUtils.sendEmail(username);
            } catch (MessagingException e) {
               e.printStackTrace();
            }
        });
    }
}
