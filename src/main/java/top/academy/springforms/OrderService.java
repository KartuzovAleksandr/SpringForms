// OrderService— это обычный Spring-bean с аннотацией @Service
// Отвечает за бизнес-логику
// - Хранение заказов в памяти
// - Добавление, удаление, редактирование
// - Генерация случайных данных
// Не является веб-сервисом и не предоставляет HTTP API напрямую
// Браузер
// ↓ (HTTP GET /orders)
// OrderController  ←→ OrderService ←→ (данные)
// ↓
// Thymeleaf (HTML-страница) orders.html
package top.academy.springforms;

import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    private final Faker faker = new Faker(Locale.of("ru"));
    private final Random random = new Random();

    private final ConcurrentHashMap<String, Order> orderMap = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0);

    @PostConstruct
    public void init() {
        // Генерируем 5 случайных заказов при старте
        for (int i = 0; i < 5; i++) {
            String id = String.valueOf(idCounter.incrementAndGet());
            String customer = faker.name().fullName();
            String product = faker.commerce().productName();
            // цена в диапазоне
            double price = Double.parseDouble(faker.commerce().price(10, 2000));
            // double price = 10 + (random.nextDouble() * 2000); // от 10 до 2010
            // Округление до 2 знаков
            // price = Math.round(price * 100.0) / 100.0;
            int quantity = 1 + random.nextInt(10); // от 1 до 10

            Order order = new Order(id, customer, product, price, quantity);
            orderMap.put(id, order);
        }
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public java.util.Optional<Order> getOrderById(String id) {
        return java.util.Optional.ofNullable(orderMap.get(id));
    }

    public void addOrder(Order order) {
        String id = String.valueOf(idCounter.incrementAndGet());
        order.setId(id);
        orderMap.put(id, order);
    }

    public void updateOrder(String id, Order order) {
        if (orderMap.containsKey(id)) {
            order.setId(id);
            orderMap.put(id, order);
        }
    }

    public void deleteOrder(String id) {
        orderMap.remove(id);
    }
}