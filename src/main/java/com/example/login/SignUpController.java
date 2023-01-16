package com.example.login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button bt_sign_up;
    @FXML
    private Button bt_log_in;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_phoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (tf_username.getText().trim().isEmpty() && tf_password.getText().trim().isEmpty() && tf_name.getText().trim().isEmpty() && tf_phoneNumber.getText().trim().isEmpty()){
            bt_sign_up.setOnAction(event -> DBUtils.signUpUser(event,tf_username.getText(),tf_password.getText(),tf_name.getText(),tf_phoneNumber.getText()));
        }else {
            System.out.println("renseignez toutes les informations");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("renseignez toutes les informations");
            alert.show();
        }

        bt_log_in.setOnAction(event -> DBUtils.changeScene(event,"sample.fxml","Log In!",null, null));
    }
}
