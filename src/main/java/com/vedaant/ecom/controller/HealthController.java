package com.vedaant.ecom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class HealthController {
	
	
	@GetMapping("/")
	public String Health() {
		return "Springboot Working";
	}
}
