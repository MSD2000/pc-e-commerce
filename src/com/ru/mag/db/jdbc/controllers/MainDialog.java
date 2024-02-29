package com.ru.mag.db.jdbc.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.IOException;

public class MainDialog {

    @FXML public Button btnAddNewCategory;
    @FXML public Button btnAddNewManufacturer;
    @FXML public Button btnAddNewOrder;
    @FXML public Button btnAddNewProduct;
    @FXML public Button btnAddNewPromocode;
    @FXML public Button btnAddNewUser;
    @FXML public Button btnShowUsersOrders;

    private static String generateTitle(String resource) {

        StringBuilder titleBuilder = new StringBuilder();

        for (int i = 0; i < resource.length(); i++) {
            char currentChar = resource.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                if (i > 0) {
                    titleBuilder.append(' ');
                }
                titleBuilder.append(Character.toLowerCase(currentChar));
            }
            else {
                titleBuilder.append(currentChar);
            }
        }

        return titleBuilder.toString();
    }

    private void showDialog(String resource) throws IOException {

        String title = generateTitle(resource);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../views/" + resource + ".fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    public void btnAddNewCategory_Click(ActionEvent actionEvent) throws IOException {
        showDialog("AddNewCategoryDialog");
    }

    public void btnAddNewManufacturer_Click(ActionEvent actionEvent) throws IOException {
        showDialog("AddNewManufacturerDialog");
    }

    public void btnAddNewOrder_Click(ActionEvent actionEvent) throws IOException {
        showDialog("AddNewOrderDialog");
    }

    public void btnAddNewProduct_Click(ActionEvent actionEvent) throws IOException {
        showDialog("AddNewProductDialog");
    }

    public void btnAddNewPromocode_Click(ActionEvent actionEvent) throws IOException {
        showDialog("AddNewPromocodeDialog");
    }

    public void btnAddNewUser_Click(ActionEvent actionEvent) throws IOException {
        showDialog("AddNewUserDialog");
    }

    public void btnShowUsersOrders_Click(ActionEvent actionEvent) throws IOException {
        showDialog("ShowUserOrders");
    }
}
