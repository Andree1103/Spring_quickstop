package com.cibertec.saludo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsuarioController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}
