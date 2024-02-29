package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import static com.ru.mag.db.jdbc.database.Database.executeQuery;

public class AddNewUserDialog {
    @FXML public PasswordField txtPassword;
    @FXML public TextField txtEmail;
    @FXML public TextField txtUserNames;
    @FXML public TabPane tcUserType;
    @FXML public TextField txtRole;
    @FXML public DatePicker dpHireDate;
    @FXML public TextField txtFullLocation;
    @FXML public TextField txtPhoneNumber;
    @FXML public TextField txtCountry;
    @FXML public TextField txtDistrict;
    @FXML public TextField txtPlace;
    @FXML public TextField txtZIPCode;
    @FXML public Button btnSaveNewUser;
    @FXML public Button btnCancelDialog;

    public void btnSaveNewUser_Click(ActionEvent actionEvent) throws SQLException {

        String userNames = txtUserNames.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String createdOn = dtf.format(now);

        String role = txtRole.getText();
        String hireDate = dpHireDate.getValue().toString();

        String phoneNumber = txtPhoneNumber.getText();
        String country = txtCountry.getText();
        String district = txtDistrict.getText();
        String place = txtPlace.getText();
        String zipCode = txtZIPCode.getText();
        String fullLocation = txtFullLocation.getText();
        int customerPoints = 100;

        if (!Objects.equals(role, "") && !hireDate.equals("")){

            executeQuery(SQLQueries.InsertEmployee, userNames, email, password, createdOn, role, hireDate);

            String tableContent = "";

            ResultSet resultSet = executeQuery(SQLQueries.SelectEmployees);

            if (resultSet.next())
                do {
                    int userID = Integer.parseInt(resultSet.getString("USERID"));
                    String usernames = resultSet.getString("USERNAMES");
                    String userEmail = resultSet.getString("EMAIL");
                    String userCreatedOn = resultSet.getString("CREATEDON");
                    String employeeRole = resultSet.getString("ROLE");

                    tableContent = userID + " | " + usernames + " | " + userEmail + " | " + userCreatedOn + " | " + employeeRole + "\n";

                } while (resultSet.next());
            else
                System.out.println("No results found.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("1 Employee Row successfully added!");
            alert.setContentText(tableContent);
            alert.showAndWait();

        }
        else{

            executeQuery(SQLQueries.InsertCustomer, userNames, email, password, createdOn, phoneNumber, country, district, place, zipCode, fullLocation, customerPoints);

            String tableContent = "";

            ResultSet resultSet = executeQuery(SQLQueries.SelectCustomers);

            if (resultSet.next())
                do {
                    int userID = Integer.parseInt(resultSet.getString("USERID"));
                    String usernames = resultSet.getString("USERNAMES");
                    String userEmail = resultSet.getString("EMAIL");
                    String userCreatedOn = resultSet.getString("CREATEDON");
                    String customerPhoneNumber = resultSet.getString("phoneNumber");

                    tableContent = userID + " | " + usernames + " | " + userEmail + " | " + userCreatedOn + " | " + customerPhoneNumber + "\n";

                } while (resultSet.next());
            else
                System.out.println("No results found.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("1 Customer Row successfully added!");
            alert.setContentText(tableContent);
            alert.showAndWait();
        }

    }

    public void btnCancelDialog_Click(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelDialog.getScene().getWindow();
        stage.close();
    }
}
