package com.example.tableorder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tableorder.model.Menu;
import com.example.tableorder.model.Order;
import com.example.tableorder.repository.MenuRepository;
import com.example.tableorder.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public void saveOrder(int tableNumber, List<Long> menuIds, Map<String, String> allParams) {
        if (menuIds != null && !menuIds.isEmpty()) {
            for (Long menuId : menuIds) {
                String quantityParam = allParams.get("quantity-" + menuId);
                int quantity = quantityParam != null ? Integer.parseInt(quantityParam) : 1;

                Order order = new Order();
                order.setTableNumber(tableNumber);
                order.setMenu(menuRepository.findById(menuId).orElse(null));
                order.setOrderTime(LocalDateTime.now());
                order.setQuantity(quantity);
                orderRepository.save(order);
            }
        }
    }

    public List<Order> getRecentOrders(int tableNumber) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getTableNumber() == tableNumber && o.getOrderTime().isAfter(LocalDateTime.now().minusMinutes(5)))
                .toList();
    }

    public List<Order> getOrderHistory(int tableNumber) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getTableNumber() == tableNumber)
                .toList();
    }

    public double calculateTotalPrice(List<Order> orders) {
        return orders.stream()
                .mapToDouble(o -> o.getMenu().getPrice() * o.getQuantity())
                .sum();
    }

    // 追加: メニュー取得メソッド
    public List<Menu> getMenusByCategory(String category) {
        return menuRepository.findByCategory(category);
    }

    public List<Menu> getRecommendedMenus(List<Long> recommendedIds) {
        return menuRepository.findAllById(recommendedIds);
    }
}