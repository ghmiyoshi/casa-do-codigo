package br.com.alura.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;

@Controller
public class ProdutosController {

	@Autowired // Pede para o Spring uma instância desse objeto que foi anotado (Injeção de
				// dependência)
	private ProdutoDAO produtoDao;

	@RequestMapping("/produtos/form")
	public ModelAndView form() {
		// ModelAndView - Além de retornar uma página, possibilita enviar objetos de
		// qualquer classe para essas páginas
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		System.out.println("Acessando form de produtos");

		return modelAndView;
	}

	@RequestMapping(value = "/produtos", method = RequestMethod.POST) // Method POST para gravar
	public String grava(Produto produto) {
		System.out.println(produto);
		produtoDao.gravar(produto);
		return "ok";
	}

	@RequestMapping(value = "/produtos", method = RequestMethod.GET) // Method GET para listar
	public ModelAndView listar() {
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);

		return modelAndView;
	}

}
