package com.example.tableorder.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tableorder.model.Menu;
import com.example.tableorder.model.Order;
import com.example.tableorder.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String showMenu(Model model, 
                           @RequestParam(value = "tab", defaultValue = "recommended") String tab,
                           @RequestParam(value = "tableNumber", defaultValue = "1") int tableNumber) {
        List<Menu> menus;
        List<String> recommendedIds = Arrays.asList("1", "6", "11", "7", "12");
        if ("recommended".equals(tab)) {
            menus = orderService.getRecommendedMenus(recommendedIds.stream().map(Long::valueOf).toList());
        } else {
            menus = orderService.getMenusByCategory(tab);
        }
        model.addAttribute("menus", menus);
        model.addAttribute("currentTab", tab);
        model.addAttribute("tableNumber", tableNumber);
        return "menu";
    }

    @PostMapping("/order")
    public String placeOrder(@RequestParam("tableNumber") int tableNumber,
                             @RequestParam(value = "menuIds", required = false) List<Long> menuIds,
                             @RequestParam Map<String, String> allParams,
                             Model model) {
        orderService.saveOrder(tableNumber, menuIds, allParams);
        List<Order> recentOrders = orderService.getRecentOrders(tableNumber);
        double totalPrice = orderService.calculateTotalPrice(recentOrders);

        model.addAttribute("orders", recentOrders);
        model.addAttribute("totalPrice", totalPrice);
        return "order_confirmation";
    }

    @GetMapping("/order-history")
    public String showOrderHistory(@RequestParam("tableNumber") int tableNumber, Model model) {
        List<Order> orders = orderService.getOrderHistory(tableNumber);
        double totalPrice = orderService.calculateTotalPrice(orders);

        model.addAttribute("orders", orders);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("tableNumber", tableNumber);
        return "order_history";
    }
}