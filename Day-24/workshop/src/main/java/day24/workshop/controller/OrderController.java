package day24.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import day24.workshop.model.OrderDetails;
import day24.workshop.service.OrderService;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderSvc;

    @PostMapping()
    public ResponseEntity<String> postOrder(
            @RequestBody MultiValueMap<String, String> form) {
        
        Boolean orderPosted = orderSvc.postOrder(new OrderDetails(form));
        
        if (orderPosted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
