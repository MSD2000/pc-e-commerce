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

public class AddNewCategoryDialog {
    @FXML public TextField txtNewCategoryName;
    @FXML public Button btnSaveNewCategory;
    @FXML public Button btnCancelDialog;

    public void btnSaveNewCategory_Click(ActionEvent actionEvent) throws SQLException {

        String newCategoryName = txtNewCategoryName.getText();

        executeQuery(SQLQueries.InsertCategory, newCategoryName);

        String tableContent = "";

        ResultSet resultSet = executeQuery(SQLQueries.SelectCategories);

        if (resultSet.next())
            do {
                int categoryID = Integer.parseInt(resultSet.getString("CATEGORYID"));
                String categoryName = resultSet.getString("CATEGORYNAME");

                tableContent = categoryID + " | " + categoryName + "\n";

            } while (resultSet.next());
        else
            System.out.println("No results found.");


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("1 Row successfully added!");
        alert.setContentText(tableContent);
        alert.showAndWait();

    }

    public void btnCancelDialog_Click(ActionEvent actionEvent){
        Stage stage = (Stage) btnCancelDialog.getScene().getWindow();
        stage.close();
    }

}
