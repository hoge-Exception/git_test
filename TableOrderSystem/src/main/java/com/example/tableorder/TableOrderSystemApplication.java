package com.example.tableorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.tableorder.model.Menu;
import com.example.tableorder.model.Staff;
import com.example.tableorder.repository.MenuRepository;
import com.example.tableorder.repository.StaffRepository;
import com.example.tableorder.util.PasswordUtil;

@SpringBootApplication
public class TableOrderSystemApplication implements CommandLineRunner {

    @Autowired
    private StaffRepository staffRepository;
    
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    public static void main(String[] args) {
        SpringApplication.run(TableOrderSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 管理者アカウントが未登録の場合のみ登録
        if (staffRepository.findByUsername("admin").isEmpty()) {
            Staff admin = new Staff();
            admin.setUsername("admin");
            String hashedPassword = passwordUtil.hashPassword("hoge"); // パスワードをハッシュ化
            admin.setPassword(hashedPassword);
            staffRepository.save(admin);
            System.out.println("Admin account created with username: admin, hashed password: " + hashedPassword);
        }
        Staff admin = staffRepository.findByUsername("admin").orElse(new Staff());
        admin.setUsername("admin");
        admin.setPassword(passwordUtil.hashPassword("hoge"));
        staffRepository.save(admin);
        
     // サンプルメニュー
        if (menuRepository.count() == 0) {
            menuRepository.save(new Menu("Coffee", 3.50, "drink", "/images/coffee.jpg"));
            menuRepository.save(new Menu("Tea", 3.00, "drink", "/images/tea.jpg"));
            menuRepository.save(new Menu("Fried Chicken", 8.00, "fried", "/images/fried_chicken.jpg"));
            menuRepository.save(new Menu("Grilled Fish", 10.00, "grilled", "/images/grilled_fish.jpg"));
            System.out.println("Sample menus added");
        }
    }
}