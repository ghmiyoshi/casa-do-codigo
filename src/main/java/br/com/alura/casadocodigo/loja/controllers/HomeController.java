package br.com.alura.casadocodigo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	// O controler retorna uma String que representa o nome da view que o SpringMVC espera receber	
	@RequestMapping("/")
	public String index() {
		System.out.println("Entrando na HOME da Casa do Código");
		return "home";
	}

}
