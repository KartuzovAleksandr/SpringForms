package top.academy.springforms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.datafaker.Faker;

@Controller
public class OrderController {
    // по старинке - из списка
//    private final List<String> customers = List.of("Иван Петров", "Мария Сидорова", "Алексей Козлов", "Елена Новикова", "Дмитрий Морозов");
//    private final List<String> products = List.of("Ноутбук", "Смартфон", "Наушники", "Клавиатура", "Мышь", "Монитор", "Планшет");
    private final Faker faker = new Faker(Locale.of("ru"));
    private final Random random = new Random();

    @GetMapping("/orders")
    public String getRandomOrders(Model model) {
        List<Order> orders = new ArrayList<>();
        int count = random.nextInt(5) + 3; // от 3 до 7 случайных заказов

        for (int i = 0; i < count; i++) {
//            String customer = customers.get(random.nextInt(customers.size()));
//            String product = products.get(random.nextInt(products.size()));
            // лучше через Faker
            String customer = faker.name().fullName();
            String product = faker.commerce().productName();
            double price = 100 + (random.nextDouble() * 900); // от 100 до 1000
            int quantity = 1 + random.nextInt(5); // от 1 до 5

            orders.add(new Order(customer, product, Math.round(price * 100.0) / 100.0, quantity));
        }

        model.addAttribute("orders", orders);
        return "orders"; // имя шаблона (например, orders.html)
    }
}