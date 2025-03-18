package com.example.tableorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tableorder.model.Staff;
import com.example.tableorder.repository.StaffRepository;
import com.example.tableorder.util.PasswordUtil;

@Controller
public class StaffController {

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private PasswordUtil passwordUtil;

	@GetMapping("/register")
	public String showRegister() {
		System.out.println("Accessed /register (GET)");
		return "register";
	}

	@PostMapping("/register")
	@Transactional
	public String registerStaff(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		Staff staff = new Staff();
		staff.setUsername(username);
		staff.setPassword(passwordUtil.hashPassword(password));
		staff.setRole("ROLE_STAFF"); // ロール設定
		staffRepository.save(staff);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
}