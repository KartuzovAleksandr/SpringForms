package top.academy.springforms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrders(Model model) {
        if (!model.containsAttribute("order")) {
            model.addAttribute("order", new Order());
        }
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    @PostMapping
    public String addOrder(@ModelAttribute Order order) {
        orderService.addOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        return orderService.getOrderById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("orders", orderService.getAllOrders());
                    return "orders";
                })
                .orElse("redirect:/orders");
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute Order order) {
        orderService.updateOrder(order.getId(), order);
        return "redirect:/orders";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}