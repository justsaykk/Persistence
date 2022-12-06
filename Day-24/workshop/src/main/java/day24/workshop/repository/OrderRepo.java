package day24.workshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day24.workshop.model.OrderDetails;
import static day24.workshop.repository.Quries.*;

@Repository
public class OrderRepo {

    @Autowired
    private JdbcTemplate template;

    public Boolean addOrder(OrderDetails orderDetails) {
        SqlRowSet rowsChanged = template.queryForRowSet(
                SQL_NEW_ORDER,
                orderDetails.getDate(),
                orderDetails.getCustomerName(),
                orderDetails.getShipAddress(),
                orderDetails.getNotes(),
                0.05,
                orderDetails.getProduct(),
                orderDetails.getUnitPrice(),
                orderDetails.getDiscount(),
                orderDetails.getQuantity());

        return null;
    }

}
