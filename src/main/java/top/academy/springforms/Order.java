package top.academy.springforms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String customer;
    private String product;
    private double price;
    private int quantity;

    // Метод для подсчёта общей стоимости
    public double getTotal() {
        return price * quantity;
    }
}