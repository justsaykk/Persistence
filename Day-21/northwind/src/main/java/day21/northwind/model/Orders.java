package day21.northwind.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Orders {
    private int customer_id;
    private String order_date;
    private String shipped_date;
    private int shipper_id;
    private String ship_name;

    public Orders(SqlRowSet rs) {
        this.customer_id = rs.getInt("customer_id");
        this.order_date = rs.getString("order_date");
        this.ship_name = rs.getString("ship_name");
        this.shipped_date = rs.getString("shipped_date");
        this.shipper_id = rs.getInt("shipper_id");
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("customer_id", getCustomer_id())
                .add("order_date", strNullCheck(getOrder_date()))
                .add("ship_name", strNullCheck(getShip_name()))
                .add("shipped_date", strNullCheck(getShipped_date()))
                .add("shipped_id", intNullCheck(getShipper_id()))
                .build();
    }

    private String strNullCheck(String string) {
        if (string != null) {
            return string;
        } else {
            return "null";
        }
    }

    private String intNullCheck(Integer integer) {
        if (integer != null) {
            return Integer.toString(integer);
        } else {
            return "null";
        }
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getShipped_date() {
        return shipped_date;
    }

    public void setShipped_date(String shipped_date) {
        this.shipped_date = shipped_date;
    }

    public int getShipper_id() {
        return shipper_id;
    }

    public void setShipper_id(int shipper_id) {
        this.shipper_id = shipper_id;
    }

    public String getShip_name() {
        return ship_name;
    }

    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }
}
