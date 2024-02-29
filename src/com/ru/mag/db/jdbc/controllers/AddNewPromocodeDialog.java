package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.database.SQLQueries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.ru.mag.db.jdbc.database.Database.executeQuery;

public class AddNewPromocodeDialog {

    @FXML public TextField txtNewPromocodeName;
    @FXML public TextField txtDiscountPercentage;
    @FXML public DatePicker dpStartDate;
    @FXML public DatePicker dpEndDate;
    @FXML public Button btnSaveNewPromocode;
    @FXML public Button btnCancelDialog;

    public void btnSaveNewPromocode_Click(ActionEvent actionEvent) throws SQLException {
        String newPromocodeName = txtNewPromocodeName.getText();
        int discountPercentage =  Integer.parseInt(txtDiscountPercentage.getText());
        String startDate = dpStartDate.getValue().toString();
        String endDate = dpEndDate.getValue().toString();

        executeQuery(SQLQueries.InsertPromocode, newPromocodeName, discountPercentage, startDate, endDate);

        String tableContent = "";

        ResultSet resultSet = executeQuery(SQLQueries.SelectPromocodes);

        if (resultSet.next())
            do {
                int promocodeID = Integer.parseInt(resultSet.getString("PROMOCODEID"));
                newPromocodeName = resultSet.getString("PROMOCODENAME");
                discountPercentage = Integer.parseInt(resultSet.getString("DISCOUNTPERCENTAGE"));
                startDate = resultSet.getString("STARTDATE");
                endDate = resultSet.getString("ENDDATE");

                tableContent = promocodeID + " | " + newPromocodeName + " | " + discountPercentage + " | " + startDate + " | " + endDate + "\n";

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
