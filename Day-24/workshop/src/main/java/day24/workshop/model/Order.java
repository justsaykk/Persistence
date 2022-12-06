package day24.workshop.model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.MultiValueMap;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
    private int orderId;
    private DateTime date;
    private String customerName;
    private String shipAddress;
    private String notes;

    public Order(MultiValueMap<String, String> form) {
        this.date = new DateTime(DateTimeFormat
                .forPattern("YYYY-MM-DD")
                .parseDateTime(form.getFirst("date")));
        this.customerName = form.getFirst("customerName");
        this.shipAddress = form.getFirst("shipAddress");
        this.notes = form.getFirst("notes");
    }
}
