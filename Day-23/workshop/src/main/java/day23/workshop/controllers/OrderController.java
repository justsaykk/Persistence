package day23.workshop.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import day23.workshop.models.Order;
import day23.workshop.services.OrderService;
import jakarta.json.Json;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderSvc;

    @GetMapping(path = "/total/{order_id}")
    public ResponseEntity<String> getOrderDetails(
            @PathVariable(name = "order_id") String orderId) {
        String responseBody = "";
        Optional<Order> req = orderSvc.getDiscountedOrderDetails(Integer.parseInt(orderId));

        if (req.isEmpty()) {
            responseBody = Json.createObjectBuilder()
                    .add("Status", "Order Not Found")
                    .build().toString();
        } else {
            responseBody = req.get().toJSON().toString();
        }

        return new ResponseEntity<String>(responseBody, HttpStatus.OK);
    }
}
