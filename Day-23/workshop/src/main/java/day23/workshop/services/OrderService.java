package day23.workshop.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import day23.workshop.models.Order;
import day23.workshop.repository.Repo;

@Service
public class OrderService {

    @Autowired
    private Repo repo;

    public Optional<Order> getDiscountedOrderDetails(int orderId) {
        return repo.getDiscountedOrderDetails(orderId);
    }
}
