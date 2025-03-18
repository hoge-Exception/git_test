package com.example.tableorder.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.tableorder.model.Menu;
import com.example.tableorder.service.MenuService;

@Controller
@RequestMapping("/menu-management")
@PreAuthorize("hasRole('STAFF')")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public String showMenuManagement(Model model) {
        model.addAttribute("menus", menuService.getAllMenus());
        return "menu_management";
    }

    @GetMapping("/add")
    public String showAddMenuForm(Model model) {
        model.addAttribute("menu", new Menu());
        return "menu_form";
    }

    @PostMapping("/add")
    public String addMenu(
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) throws IOException {
        menuService.saveMenu(name, price, category, image);
        return "redirect:/menu-management";
    }

    @GetMapping("/edit/{id}")
    public String showEditMenuForm(@PathVariable("id") Long id, Model model) {
        Menu menu = menuService.getAllMenus().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        model.addAttribute("menu", menu);
        return "menu_form";
    }

    @PostMapping("/edit/{id}")
    public String updateMenu(
            @PathVariable("id") Long id,
            @RequestParam("name") String name,
            @RequestParam("price") double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) throws IOException {
        menuService.updateMenu(id, name, price, category, image);
        return "redirect:/menu-management";
    }

    @PostMapping("/delete/{id}")
    public String deleteMenu(@PathVariable("id") Long id) {
        menuService.deleteMenu(id);
        return "redirect:/menu-management";
    }
}