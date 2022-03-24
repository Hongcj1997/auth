package com.hongcj.auth.controller;

import com.hongcj.auth.model.User;
import com.hongcj.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongcj
 * @description
 * @date 3/24/22 5:02 PM
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String register(String userName,String password){
		userService.save(new User(userName,passwordEncoder.encode(password)));
		return "success";
	}
}
