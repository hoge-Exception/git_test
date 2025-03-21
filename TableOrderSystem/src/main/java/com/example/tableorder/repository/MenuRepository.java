package com.example.tableorder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tableorder.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategory(String category);
}