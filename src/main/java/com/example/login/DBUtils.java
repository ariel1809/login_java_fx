package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String name){
        Parent root = null;

        if (username != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformations(name);
                loggedInController.sendMail(username);
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        assert root != null;
        stage.setScene(new Scene(root,600,400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, String name, String phoneNumber){

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginDB", "root","");
            psCheckUserExist = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExist.setString(1,username);
            resultSet = psCheckUserExist.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Cet utilisateur existe déjà");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Veuillez changer ce username");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, name, phoneNumber) VALUES (?, ?, ?, ?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,name);
                psInsert.setString(4,phoneNumber);
                psInsert.executeLargeUpdate();

                changeScene(event, "logged-in.fxml", "Bienvenue!", username, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psCheckUserExist != null){
                try {
                    psCheckUserExist.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psInsert != null){
                try {
                    psInsert.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loginUser(ActionEvent event, String username, String password){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginDB", "root","");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Utilisateur introuvable");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username or password incorrect");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    System.out.println(retrievedPassword);

                    if (retrievedPassword.equals(password)){
                        changeScene(event, "logged-in.fxml", "Bienvenue!", username, null);
                    }else {
                        System.out.println("password incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Username or password incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }


            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendEmail(String email) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("stevekamga18@gmail.com", "ebndtinquknsqxtc");
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject("Test Mail Java fx");
        message.setContent("Bonjour Ariel","text/html");

        Address address = new InternetAddress(email);
        message.setRecipient(Message.RecipientType.TO, address);

        Transport.send(message);
    }
}
