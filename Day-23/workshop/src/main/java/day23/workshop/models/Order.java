package day23.workshop.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Order {
    private int id;
    private DateTime orderDate;
    private String customerId;
    private Double totalPrice;
    private Double costPrice;

    public Order(SqlRowSet rs) {
        this.id = rs.getInt("order_id");
        this.orderDate = new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy")
                .parseDateTime(rs.getString("order_date")));
        this.customerId = rs.getString("customer_id");
        this.totalPrice = rs.getDouble("total_price");
        this.costPrice = rs.getDouble("cost_price");
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("customerId", this.customerId)
                .add("orderDate", this.orderDate.toString())
                .add("customerId", this.customerId)
                .add("totalPrice", Double.toString(totalPrice))
                .add("costPrice", Double.toString(this.costPrice))
                .build();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double gettotalPrice() {
        return totalPrice;
    }

    public void settotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

}
