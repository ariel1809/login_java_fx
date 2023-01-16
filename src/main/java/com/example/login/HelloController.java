package com.example.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button bt_login;
    @FXML
    private Button bt_sign_up;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_login.setOnAction(event -> DBUtils.loginUser(event,tf_username.getText(),tf_password.getText()));

        bt_sign_up.setOnAction(event -> DBUtils.changeScene(event, "sign-up.fxml", "Sign Up!", null, null));
    }
}