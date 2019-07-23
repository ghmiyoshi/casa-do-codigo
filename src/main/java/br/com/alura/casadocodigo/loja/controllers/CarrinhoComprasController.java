package br.com.alura.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.models.CarrinhoCompras;
import br.com.alura.casadocodigo.loja.models.CarrinhoItem;
import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("carrinho")
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	@RequestMapping("add")
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/produtos");
		CarrinhoItem carrinhoItem = criaItem(produtoId, tipoPreco);
		 
		carrinho.add(carrinhoItem);
		
		return modelAndView ;
	}
	
	private CarrinhoItem criaItem(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = produtoDAO.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		
		return carrinhoItem;
	}

}
