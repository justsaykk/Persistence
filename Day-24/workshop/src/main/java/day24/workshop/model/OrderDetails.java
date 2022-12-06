package day24.workshop.model;

import org.springframework.util.MultiValueMap;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderDetails extends Order {

    private int id;
    private String product;
    private Double unitPrice;
    private Double discount;
    private int quantity;
    // private String product2;
    // private Double unitPrice2;
    // private Double discount2;
    // private int quantity2;
    // private String product3;
    // private Double unitPrice3;
    // private Double discount3;
    // private int quantity3;

    public OrderDetails(MultiValueMap<String, String> form) {
        super(form);
        this.product = form.getFirst("product");
        this.unitPrice = Double.parseDouble(form.getFirst("unitPrice"));
        this.discount = Double.parseDouble(form.getFirst("discount"));
        this.quantity = Integer.parseInt(form.getFirst("quantity"));
        // this.product2 = form.getFirst("product2");
        // this.unitPrice2 = Double.parseDouble(form.getFirst("unitPrice2"));
        // this.discount2 = Double.parseDouble(form.getFirst("discount2"));
        // this.quantity2 = Integer.parseInt(form.getFirst("quantity2"));
        // this.product3 = form.getFirst("product3");
        // this.unitPrice3 = Double.parseDouble(form.getFirst("unitPrice3"));
        // this.discount3 = Double.parseDouble(form.getFirst("discount3"));
        // this.quantity3 = Integer.parseInt(form.getFirst("quantity3"));
    }
}
