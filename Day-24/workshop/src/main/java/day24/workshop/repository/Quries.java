package day24.workshop.repository;

public class Quries {

    public static final String SQL_NEW_ORDER = """
            INSERT INTO orders (order_date, customer_name, ship_address, notes, tax)
                        values (?, ?, ? ,? ,?);

            SELECT @order_id:=LAST_INSERT_ID();

            INSERT INTO order_details (order_id, product, unit_price, discount, quantity)
                values (@order_id, ?, ?, ?, ?);
                """;
}
