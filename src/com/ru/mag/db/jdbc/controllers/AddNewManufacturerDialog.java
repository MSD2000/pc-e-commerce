package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.ru.mag.db.jdbc.database.Database.executeQuery;

public class AddNewManufacturerDialog {
    @FXML public TextField txtNewManufacturerName;
    @FXML public TextField txtNewManufacturerCountry;
    @FXML public Button btnSaveNewManufacturer;
    @FXML public Button btnCancelDialog;

    public void btnSaveNewManufacturer_Click(ActionEvent actionEvent) throws SQLException {

        String newManufacturerName = txtNewManufacturerName.getText();
        String newManufacturerCountry = txtNewManufacturerCountry.getText();

        executeQuery(SQLQueries.InsertManufacturer, newManufacturerName, newManufacturerCountry);

        String tableContent = "";

        ResultSet resultSet = executeQuery(SQLQueries.SelectManufacturers);

        if (resultSet.next())
            do {
                int manufacturerID = Integer.parseInt(resultSet.getString("MANUFACTURERID"));
                String manufacturerName = resultSet.getString("MANUFACTURERNAME");
                String manufacturerCountry = resultSet.getString("COUNTRY");

                tableContent = manufacturerID + " | " + manufacturerName + " | " + manufacturerCountry + "\n";

            } while (resultSet.next());
        else
            System.out.println("No results found.");


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("1 Row successfully added!");
        alert.setContentText(tableContent);
        alert.showAndWait();
    }

    public void btnCancelDialog_Click(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelDialog.getScene().getWindow();
        stage.close();
    }
}
