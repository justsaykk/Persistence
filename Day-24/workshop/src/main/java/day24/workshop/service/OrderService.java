package day24.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import day24.workshop.model.OrderDetails;
import day24.workshop.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Transactional
    public Boolean postOrder(OrderDetails orderDetails) {
        return orderRepo.addOrder(orderDetails);
    }
}
