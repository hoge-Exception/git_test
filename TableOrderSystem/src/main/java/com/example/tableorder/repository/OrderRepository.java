package com.example.tableorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tableorder.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}