package day21.northwind.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day21.northwind.model.Customer;
import day21.northwind.model.Orders;

import static day21.northwind.Repository.query.*;

@Repository
public class CustomerRepo {

    @Autowired
    private JdbcTemplate template;

    public List<Customer> getCustomers(Integer limit, Integer offset) {
        LinkedList<Customer> customerList = new LinkedList<>();

        final SqlRowSet rs = template.queryForRowSet(
                SQL_GET_CUSTOMERS, limit, offset);

        while (rs.next()) {
            customerList.add(new Customer(rs));
        }

        return customerList;
    }

    public Optional<Customer> getCustomerById(String id) {
        SqlRowSet rs = template.queryForRowSet(SQL_GET_CUSTOMERBYID, id);
        if (rs.first()) {
            return Optional.of(new Customer(rs));
        }
        return Optional.empty();
    }

    public Optional<List<Orders>> getCustomerOrders(String customer_id) {

        final SqlRowSet rs = template.queryForRowSet(SQL_GET_CUSTOMER_ORDERS, customer_id);

        List<Orders> listOfOrders = new LinkedList<>();

        if (!rs.first()) {
            return Optional.empty();
        }

        while (rs.next()) {
            listOfOrders.add(new Orders(rs));
        }

        return Optional.of(listOfOrders);
    }
}
