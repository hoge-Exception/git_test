package com.example.tableorder.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.tableorder.model.Menu;
import com.example.tableorder.repository.MenuRepository;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Value("${upload.dir}")
    private String uploadDir;

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu saveMenu(String name, double price, String category, MultipartFile image) throws IOException {
        String imageUrl = saveImage(image);
        Menu menu = new Menu(name, price, category, imageUrl);
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, String name, double price, String category, MultipartFile image) throws IOException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setName(name);
        menu.setPrice(price);
        menu.setCategory(category);
        if (!image.isEmpty()) {
            String imageUrl = saveImage(image);
            menu.setImageUrl(imageUrl);
        }
        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) return null;

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = image.getOriginalFilename().toLowerCase().replaceAll("[^a-z0-9._]", "_");
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, image.getBytes());
        return "/uploads/images/" + fileName;
    }
}