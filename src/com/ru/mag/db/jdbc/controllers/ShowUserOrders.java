package com.ru.mag.db.jdbc.controllers;

import com.ru.mag.db.jdbc.database.SQLQueries;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.ru.mag.db.jdbc.database.Database.executeQuery;

public class ShowUserOrders {
    @FXML public ChoiceBox cbCustomers;
    @FXML public ChoiceBox cbOrders;
    @FXML public Label lblTotalPrice;
    @FXML public TableView tvProducts;
    private int userID = 0;
    private int orderID = 0;

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

    private void initOrders(int userID) throws SQLException{

        ArrayList<String> orders = new ArrayList<>();
        ResultSet resultSet = executeQuery(SQLQueries.SelectCustomerOrders, userID);

        if (resultSet.next())
            do {
                int orderID = resultSet.getInt("ORDERID");
                String orderStatus = resultSet.getString("ORDERSTATUS");
                Float totalPrice = resultSet.getFloat("TOTALPRICE");
                String orderedOn = resultSet.getString("ORDEREDON");

                orders.add(orderID + "|" + orderStatus + "|" + totalPrice + "|" + orderedOn);

            } while (resultSet.next());
        else
            System.out.println("No results found.");

        cbOrders.setItems(FXCollections.observableArrayList(orders));

    }

    @FXML
    public void initialize () throws SQLException {
        initCustomers();
    }

    public void cbCustomers_Changed(ActionEvent actionEvent) throws SQLException{

        String customer = cbCustomers.getValue().toString();
        String[] customerValues = customer.split("\\|");
        int customerID = Integer.parseInt(customerValues[0]);

        initOrders(customerID);
    }

    public void cbOrders_Changed(ActionEvent actionEvent) throws SQLException {

        ResultSet resultSet = executeQuery(SQLQueries.SelectOrderProducts, orderID);

        if (resultSet.next())
            do {
                int orderID = resultSet.getInt("PRODUCTID");
                String productName = resultSet.getString("PRODUCTNAME");
                String modelName = resultSet.getString("MODELNAME");
                Float price = resultSet.getFloat("PRICE");
                int orderedQuantity = resultSet.getInt("ORDEREDQUANTITY");
                Float subTotal = resultSet.getFloat("SUBTOTAL");

            } while (resultSet.next());
        else
            System.out.println("No results found.");

    }
}
