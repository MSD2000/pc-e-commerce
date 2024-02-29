package com.ru.mag.db.jdbc.database;

public class SQLQueries {

    public static String SelectCategories = "SELECT * FROM CATEGORIES";

    public static String InsertCategory = "INSERT INTO CATEGORIES (CATEGORYID, CATEGORYNAME) " +
            "VALUES ((SELECT MAX(CATEGORYID) + 1 FROM CATEGORIES) , ?)";

    public static String SelectManufacturers = "SELECT * FROM MANUFACTURERS";

    public static String InsertManufacturer = "INSERT INTO MANUFACTURERS (MANUFACTURERID, MANUFACTURERNAME, COUNTRY) " +
            "VALUES ((SELECT MAX(MANUFACTURERID) + 1 FROM MANUFACTURERS), ?, ?)";

    public static String SelectPromocodes = "SELECT * FROM PROMOCODES";

    public static String InsertPromocode = "INSERT INTO PROMOCODES (PROMOCODEID, PROMOCODENAME, DISCOUNTPERCENTAGE, STARTDATE, ENDDATE) " +
            "VALUES ((SELECT MAX(PROMOCODEID) + 1 FROM PROMOCODES), ?, ?, ?, ?)";

    public static String SelectEmployees = "SELECT u.*, (TREAT(VALUE(u) AS EMPLOYEE_T).EmployeeRole) AS Role FROM USERS u WHERE VALUE(u) IS OF TYPE (EMPLOYEE_T)";

    public static String InsertEmployee = "INSERT INTO USERS VALUES ( EMPLOYEE_T( (SELECT MAX(USERID) + 1 FROM USERS), ?, ?, ?, ?, ?, ?) )";

    public static String SelectCustomers = "SELECT u.*, (TREAT(VALUE(u) AS CUSTOMER_T).PhoneNumber) AS phoneNumber FROM USERS u WHERE VALUE(u) IS OF TYPE (CUSTOMER_T)";

    public static String InsertCustomer = "INSERT INTO users VALUES ( CUSTOMER_T( (SELECT MAX(USERID) + 1 FROM USERS), ?, ?, ?, ?, ?, ADDRESS_T(?, ?, ?, ?, ?), ?), ?)";

    public static String SelectProducts = "SELECT PRODUCTID, PRODUCTNAME, MODELNAME, PRICE FROM PRODUCTS";

    public static String SelectProductByID = "SELECT PRODUCTID, PRODUCTNAME, MODELNAME, PRICE FROM PRODUCTS WHERE PRODUCTID = ?";

    public static String InsertCPU = "INSERT INTO PRODUCTS VALUES ( CPU_T((SELECT MAX(PRODUCTID) + 1 FROM PRODUCTS), ?, ?, MANUFACTURER_T(?, ?, ?), ?, CATEGORY_T(?, ?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) );";

    public static String InsertMotherboard = "INSERT INTO PRODUCTS VALUES ( MOTHERBOARD_T( (SELECT MAX(PRODUCTID) + 1 FROM PRODUCTS), ?, ?, MANUFACTURER_T(?, ?, ?), ?, CATEGORY_T(?, ?), ?, ?, ?, ?, ?, ?, ?, ?) );";

    public static String InsertMonitor = "INSERT INTO PRODUCTS VALUES ( KEYBOARD_T( (SELECT MAX(PRODUCTID) + 1 FROM PRODUCTS), ?, ?, MANUFACTURER_T(?, ?, ?), ?, CATEGORY_T(?, ?), ?, ?, ?, ?, ?) );";

    public static String InsertKeyboard = "INSERT INTO PRODUCTS VALUES ( MONITOR_T( (SELECT MAX(PRODUCTID) + 1 FROM PRODUCTS), ?, ?, MANUFACTURER_T(?, ?, ?), ?, CATEGORY_T(?, ?), ?, ?, ?, ?, ?) );";

    public static String SelectNextOrderID = "SELECT (MAX(ORDERID) + 1) AS NEXTORDERID FROM ORDERS";

    public static String InsertOrder = "INSERT INTO ORDERS VALUES( ORDER_T( ?, ?, ?, ?, ?, (SELECT REF(U) FROM USERS U WHERE USERID=?), (SELECT REF(PC) FROM PROMOCODES PC WHERE PROMOCODEID=?), FINANCIAL_OBLIGATION_T(?, ?, ?, ?, ?, ?) ) )";

    public static String InsertOrderProduct = "INSERT INTO ORDER_PRODUCTS VALUES( ORDER_PRODUCT_T(?, ?, ?, ?) )";

    public static String SelectCustomerOrders = "SELECT ORDERS.ORDERID, ORDERS.ORDERSTATUS, ORDERS.TOTALPRICE, ORDERS.ORDEREDON, TREAT(ORDERS.CUSTOMER AS USER_T).USERID AS USERID FROM ORDERS WHERE TREAT(ORDERS.CUSTOMER AS USER_T).USERID=?";

    public static String SelectOrderProducts = "SELECT P.PRODUCTID, P.PRODUCTNAME, P.MODELNAME, P.Price, OP.ORDEREDQUANTITY, OP.SUBTOTAL FROM PRODUCTS P INNER JOIN ORDERPRODUCTS OP ON OP.PRODUCTID = P.PRODUCTID WHERE OP.ORDERID = ?";

}
