package br.com.alura.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.models.Produto;

@Controller
public class HomeController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	// O controler retorna uma String que representa o nome da view que o SpringMVC espera receber	
	@RequestMapping("/")
	public ModelAndView index() {
		System.out.println("Entrando na HOME da Casa do Código");
		
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("home");
		modelAndView.addObject("produtos",produtos);
		
		return modelAndView;
	}

}
