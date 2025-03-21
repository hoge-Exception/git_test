package com.example.tableorder.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 文字列を受け取り、ハッシュ化した値を返すメソッド
    public String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // ハッシュ値と平文が一致するか確認するメソッド（ログイン時に役立つ）
    public boolean matches(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}