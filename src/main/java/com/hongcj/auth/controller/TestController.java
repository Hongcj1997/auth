package com.hongcj.auth.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongcj
 * @description
 * @date 3/20/22 10:46 PM
 */
@RestController
public class TestController {
	@GetMapping("anon/hello")
	public String hello() {
		return "Hello World";
	}

	@GetMapping("api/hello")
	public String apiHello() {
		return "Hello World";
	}
}
