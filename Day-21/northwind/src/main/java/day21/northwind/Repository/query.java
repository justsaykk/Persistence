package day21.northwind.Repository;

public class query {
    
    public static final String SQL_GET_CUSTOMERS = "SELECT * FROM northwind.customers limit ? offset ?";
    public static final String SQL_GET_CUSTOMERBYID = "SELECT * FROM northwind.customers where id = ?";
    public static final String SQL_GET_CUSTOMER_ORDERS = "SELECT * FROM northwind.orders where customer_id = ?";
}
