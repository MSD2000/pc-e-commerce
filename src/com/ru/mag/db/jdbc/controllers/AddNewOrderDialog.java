package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.database.SQLQueries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ru.mag.db.jdbc.database.Database.executeQuery;

public class AddNewOrderDialog {

    @FXML public Button btnSaveNewOrder;

    @FXML public Button btnCancelDialog;
    @FXML public ChoiceBox cbCustomers;
    @FXML public ChoiceBox cbPromocodes;
    @FXML public ChoiceBox cbPaymentMethods;
    @FXML public Label lblTotalPrice;
    @FXML public ChoiceBox cbProducts;
    @FXML public TextField txtQuantity;
    @FXML public Button btnAddProduct;
    @FXML public TableView tvProducts;
    @FXML public TableColumn colProductID;
    @FXML public TableColumn colProductName;
    @FXML public TableColumn colModelName;
    @FXML public TableColumn colPrice;
    @FXML public TableColumn colQuantity;


    private void initCustomers() throws SQLException {

        ArrayList<String> customers = new ArrayList<>();

        ResultSet resultSet = executeQuery(SQLQueries.SelectCustomers);

        if (resultSet.next())
            do {
                int userID = Integer.parseInt(resultSet.getString("USERID"));
                String usernames = resultSet.getString("USERNAMES");
                String userEmail = resultSet.getString("EMAIL");
                String customerPhoneNumber = resultSet.getString("phoneNumber");

                customers.add(userID + "|" + usernames + "|" + userEmail + "|" + customerPhoneNumber);

            } while (resultSet.next());
        else
            System.out.println("No results found.");

        cbCustomers.setItems(FXCollections.observableArrayList(customers));
    }

    private void initPromocodes() throws SQLException {
        ArrayList<String> promocodes = new ArrayList<>();

        ResultSet resultSet = executeQuery(SQLQueries.SelectPromocodes);

        if (resultSet.next())
            do {
                int promoCodeID = Integer.parseInt(resultSet.getString("PROMOCODEID"));
                String promoCodeName = resultSet.getString("PROMOCODENAME");
                String DiscountPercentage = resultSet.getString("DISCOUNTPERCENTAGE");

                promocodes.add(promoCodeID + "|" + promoCodeName + "|" + DiscountPercentage);

            } while (resultSet.next());
        else
            System.out.println("No results found.");

        cbPromocodes.setItems(FXCollections.observableArrayList(promocodes));
    }

    private void initPaymentMethods(){
        ArrayList<String> methods = new ArrayList<>();
        methods.add("CrCard");
        methods.add("Cash");
        cbPaymentMethods.setItems(FXCollections.observableArrayList(methods));
    }

    private void initProducts() throws SQLException {
        ArrayList<String> products = new ArrayList<>();

        ResultSet resultSet = executeQuery(SQLQueries.SelectProducts);

        if (resultSet.next())
            do {
                int productID = Integer.parseInt(resultSet.getString("PRODUCTID"));
                String productName = resultSet.getString("PRODUCTNAME");
                String modelName = resultSet.getString("MODELNAME");
                float price = resultSet.getFloat("PRICE");

                products.add(productID + "|" + productName + "|" + modelName  + "|" + price);

            } while (resultSet.next());
        else
            System.out.println("No results found.");

        cbProducts.setItems(FXCollections.observableArrayList(products));
    }

    @FXML
    public void initialize() throws SQLException {
        initCustomers();
        initPromocodes();
        initPaymentMethods();
        initProducts();
    }

    private float totalPrice = 0;

    public void btnAddProduct_Click(ActionEvent actionEvent) {

        String product = cbProducts.getValue().toString();
        String[] productValues = product.split("\\|");
        int productID = Integer.parseInt(productValues[0]);
        String productName = productValues[1];
        String modelName = productValues[2];
        float price = Float.parseFloat(productValues[3]);

        int quantity = Integer.parseInt(txtQuantity.getText());

        ObservableList<String> row = FXCollections.observableArrayList();
        row.add(String.valueOf(productID));
        row.add(productName);
        row.add(modelName);
        row.add(String.valueOf(price));
        row.add(String.valueOf(quantity));

        tvProducts.getItems().add(row);

        int j=0;
        for(Object col : tvProducts.getColumns()){
            TableColumn c = (TableColumn) col;
            int finalJ = j;
            c.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                if(param.getValue().get(finalJ) != null)
                    return new SimpleStringProperty(param.getValue().get(finalJ).toString());
                return new SimpleStringProperty("Null");
            });
            j++;
        }

        totalPrice += price * quantity;

        lblTotalPrice.setText(String.valueOf(totalPrice));
    }

    public void btnSaveNewOrder_Click(ActionEvent actionEvent) throws SQLException {

        List<Integer> productIDs = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (Object rowData : tvProducts.getItems()) {
            TableColumn productID = (TableColumn) tvProducts.getColumns().get(0);
            TableColumn productQuantity = (TableColumn) tvProducts.getColumns().get(4);
            productIDs.add((Integer)productID.getCellData(rowData));
            quantities.add((Integer)productQuantity.getCellData(rowData));
        }

        int orderID = 0;
        ResultSet resultSet = executeQuery(SQLQueries.SelectNextOrderID);
        if (resultSet.next())
            do {
                orderID = Integer.parseInt(resultSet.getString("NEXTORDERID"));
            } while (resultSet.next());
        else
            System.out.println("No results found.");

        String orderStatus = "pending";

        int quantity = 0;
        for(int q : quantities) quantity += q;

        float totalPrice = Float.parseFloat(lblTotalPrice.getText());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        String orderedOn = dtf.format(now);

        String customer = cbCustomers.getValue().toString();
        String[] customerValues = customer.split("\\|");
        int customerID = Integer.parseInt(customerValues[0]);

        String promoCodes = cbPromocodes.getValue().toString();
        String[] promoCodeValues = promoCodes.split("\\|");
        int promoCodeID = Integer.parseInt(promoCodeValues[0]);

        Random random = new Random();
        int financialObligationID = random.nextInt(10000);

        String paymentMethod = cbPaymentMethods.getValue().toString();

        String paymentStatus = "pending";

        float expectedAmount = 0.0F;

        float payedAmount = 0.0F;

        String payedOn = null;

        executeQuery(SQLQueries.InsertOrder, orderID, orderStatus, quantity, totalPrice, orderedOn, customerID, promoCodeID, financialObligationID, paymentMethod, paymentStatus, totalPrice, payedAmount, payedOn);

        for(Integer productID : productIDs){

            ResultSet resultSetProduct = executeQuery(SQLQueries.SelectProductByID, productID);
            float price = 0;

            if (resultSet.next())
                do {
                    price = resultSet.getFloat("PRICE");
                } while (resultSet.next());
            else
                System.out.println("No results found.");

            executeQuery(SQLQueries.InsertOrderProduct, orderID, productID, price, quantity);
        }
    }

    public void btnCancelDialog_Click(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelDialog.getScene().getWindow();
        stage.close();
    }

}
