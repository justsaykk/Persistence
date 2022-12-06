package day23.workshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day23.workshop.models.Order;

import static day23.workshop.repository.Queries.*;

import java.util.Optional;

@Repository
public class Repo {

    @Autowired
    private JdbcTemplate template;

    public Optional<Order> getDiscountedOrderDetails(int orderId) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ORDER_DETAILS, orderId);
        return rs.next() ? Optional.of(new Order(rs)) : Optional.empty();
    }
}
