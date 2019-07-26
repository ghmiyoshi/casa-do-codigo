package br.com.alura.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.models.Produto;

@RestController
public class RelatorioController {

	@Autowired
	private ProdutoDAO produtoDao;
	
	@RequestMapping("/relatorio-produtos")
	public List<Produto> listaProdutos() {
		List<Produto> listar = produtoDao.listar();
		return listar;
	}
}
