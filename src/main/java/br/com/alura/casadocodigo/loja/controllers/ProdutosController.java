package br.com.alura.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;

@Controller
public class ProdutosController {

	@Autowired // Pede para o Spring uma instância desse objeto que foi anotado (Injeção de dependência)
	private ProdutoDAO produtoDao;

	@RequestMapping("/produtos/form")
	public ModelAndView form() {
		// ModelAndView - Além de retornar uma página, possibilita enviar objetos de qualquer classe para essas páginas
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		System.out.println("Acessando form de produtos");

		return modelAndView;
	}

	@RequestMapping("/produtos")
	public String grava(Produto produto) {
		System.out.println(produto);
		produtoDao.gravar(produto);
		return "ok";
	}

}
