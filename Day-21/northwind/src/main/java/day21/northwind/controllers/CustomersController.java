package day21.northwind.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import day21.northwind.Repository.CustomerRepo;
import day21.northwind.model.Customer;
import day21.northwind.model.Orders;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

/**
 * CustomersController
 */
@RestController
@RequestMapping(path = "/api/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomersController {

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping
    public ResponseEntity<String> getCustomers(
            @RequestParam(name = "limit", defaultValue = "5") Integer limit,
            @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        List<Customer> customerList = customerRepo.getCustomers(limit, offset);

        JsonArrayBuilder resArr = Json.createArrayBuilder();

        for (Customer customer : customerList) {
            resArr.add(customer.toJSON());
        }

        return new ResponseEntity<>(resArr.build().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<String> getCustomerId(
            @PathVariable("id") String id) {
        Optional<Customer> customer = customerRepo.getCustomerById(id);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(customer.get().toJSON().toString(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}/orders")
    public ResponseEntity<String> getCustomerOrder(@PathVariable("id") String id) {
        
        Optional<List<Orders>> opt = customerRepo.getCustomerOrders(id);

        if (opt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        JsonArrayBuilder resArr = Json.createArrayBuilder();

        for (Orders order : opt.get()) {
            resArr.add(order.toJSON());
        }
        
        return new ResponseEntity<>(resArr.build().toString(), HttpStatus.OK);
    }
}