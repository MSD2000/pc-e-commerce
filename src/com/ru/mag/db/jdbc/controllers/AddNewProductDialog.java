package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.database.SQLQueries;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import static com.ru.mag.db.jdbc.database.Database.executeQuery;

public class AddNewProductDialog {

    @FXML public TextField txtNewProductName;
    @FXML public TextField txtModelName;
    @FXML public TextField txtPrice;
    @FXML public ChoiceBox cbManufacturer;
    @FXML public ChoiceBox cbCategory;
    @FXML public Tab tabCpu;
    @FXML public TextField txtCpuTransistorsNm;
    @FXML public TextField txtCpuCores;
    @FXML public TextField txtCpuThreads;
    @FXML public TextField txtCpuMinGhz;
    @FXML public TextField txtCpuMaxGhz;
    @FXML public TextField txtCpuCacheMemory;
    @FXML public TextField txtCpuTdp;
    @FXML public TextField txtCpuSocketType;
    @FXML public TextField txtCpuIGpu;
    @FXML public Tab tabMotherboard;
    @FXML public TextField txtMbFormFactor;
    @FXML public TextField txtMbSocketType;
    @FXML public TextField txtMbChipset;
    @FXML public TextField txtMbRamSlots;
    @FXML public TextField txtMbRamType;
    @FXML public TextField txtMbIO;
    @FXML public TextField txtMbInterfaces;
    @FXML public Tab tabMonitor;
    @FXML public TextField txtMonitorRefreshRate;
    @FXML public TextField txtMonitorPanelType;
    @FXML public TextField txtMonitorResolution;
    @FXML public TextField txtMonitorScreenSize;
    @FXML public Tab tabKeyboard;
    @FXML public TextField txtKeyboardConnectivity;
    @FXML public TextField txtKeyboardLayout;
    @FXML public TextField txtKeyboardSwitchType;
    @FXML public TextField txtKeyboardFormFactor;
    @FXML public Button btnSaveNewProduct;
    @FXML public Button btnCancelDialog;

    private void initCategories() throws SQLException {
        ArrayList<String> categories = new ArrayList<>();

        ResultSet resultSet = executeQuery(SQLQueries.SelectCategories);

        if (resultSet.next())
            do {
                int categoryID = Integer.parseInt(resultSet.getString("CATEGORYID"));
                String categoryName = resultSet.getString("CATEGORYNAME");

                categories.add(categoryID + "|" + categoryName);

            } while (resultSet.next());
        else
            System.out.println("No results found.");

        cbCategory.setItems(FXCollections.observableArrayList(categories));
    }

    private void initManufacturers() throws SQLException {
        ArrayList<String> manufacturers = new ArrayList<>();

        ResultSet resultSet = executeQuery(SQLQueries.SelectManufacturers);

        if (resultSet.next())
            do {
                int manufacturerID = Integer.parseInt(resultSet.getString("MANUFACTURERID"));
                String manufacturerName = resultSet.getString("MANUFACTURERNAME");
                String manufacturerCountry = resultSet.getString("COUNTRY");

                manufacturers.add(manufacturerID + "|" + manufacturerName + "|" + manufacturerCountry);

            } while (resultSet.next());
        else
            System.out.println("No results found.");

        cbManufacturer.setItems(FXCollections.observableArrayList(manufacturers));
    }

    @FXML
    public void initialize() throws SQLException {
        initManufacturers();
        initCategories();
    }

    public void btnSaveNewProduct_Click(ActionEvent actionEvent) throws SQLException {

        String productName = txtNewProductName.getText();
        String modelName = txtModelName.getText();

        String manufacturer = cbManufacturer.getValue().toString();
        String[] manufacturerValues = manufacturer.split("\\|");
        String manufacturerID = manufacturerValues[0];
        String manufacturerName = manufacturerValues[1];
        String manufacturerCounty = manufacturerValues[2];

        String price = txtPrice.getText();

        String category = cbCategory.getValue().toString();
        String[] categoryValues = category.split("\\|");
        String categoryID = categoryValues[0];
        String categoryName = categoryValues[1];

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String createdOn = dtf.format(now);

        int cores = Integer.parseInt(txtCpuCores.getText());
        int threads = Integer.parseInt(txtCpuThreads.getText());
        int minGhz = Integer.parseInt(txtCpuMinGhz.getText());
        int maxGhz = Integer.parseInt(txtCpuMaxGhz.getText());
        int transistorsNM = Integer.parseInt(txtCpuTransistorsNm.getText());
        int cacheMemory = Integer.parseInt(txtCpuCacheMemory.getText());
        int tdp = Integer.parseInt(txtCpuTdp.getText());
        String cpuSocketType = txtCpuSocketType.getText();
        String iGpu = txtCpuIGpu.getText();

        String mbFormFactor = txtMbFormFactor.getText();
        String mbSocketType = txtMbSocketType.getText();
        String chipset = txtMbChipset.getText();
        int ramSlots = Integer.parseInt(txtMbRamSlots.getText());
        String ramType = txtMbRamType.getText();
        String io = txtMbIO.getText();
        String interfaces = txtMbInterfaces.getText();

        String screenSize = txtMonitorScreenSize.getText();
        String resolution = txtMonitorResolution.getText();
        String refreshRate = txtMonitorRefreshRate.getText();
        String panelType = txtMonitorPanelType.getText();

        String layout = txtKeyboardLayout.getText();
        String switchType = txtKeyboardSwitchType.getText();
        String kbFormFactor = txtKeyboardFormFactor.getText();
        String connectivity = txtKeyboardConnectivity.getText();

        if(cores != 0){
            executeQuery(SQLQueries.InsertCPU, productName, modelName, manufacturerID, manufacturerName, manufacturerCounty, price, categoryID, categoryName, createdOn,
                    cores, threads, minGhz, maxGhz, transistorsNM, cacheMemory, tdp, cpuSocketType, iGpu);
        } else if (!Objects.equals(mbFormFactor, "")){
            executeQuery(SQLQueries.InsertMotherboard, productName, modelName, manufacturerID, manufacturerName, manufacturerCounty, price, categoryID, categoryName, createdOn,
                    mbFormFactor, mbSocketType, chipset, ramSlots, ramType, io, interfaces);
        } else if (!Objects.equals(screenSize, "")) {
            executeQuery(SQLQueries.InsertMonitor, productName, modelName, manufacturerID, manufacturerName, manufacturerCounty, price, categoryID, categoryName, createdOn,
                    screenSize, resolution, refreshRate, panelType);
        } else {
            executeQuery(SQLQueries.InsertKeyboard, productName, modelName, manufacturerID, manufacturerName, manufacturerCounty, price, categoryID, categoryName, createdOn,
                    layout, switchType, kbFormFactor, connectivity);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText("1 Product Row successfully added!");
        alert.showAndWait();

    }

    public void btnCancelDialog_Click(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelDialog.getScene().getWindow();
        stage.close();
    }
}
