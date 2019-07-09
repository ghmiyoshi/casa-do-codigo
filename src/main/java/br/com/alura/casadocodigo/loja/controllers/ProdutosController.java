package br.com.alura.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("produtos")
public class ProdutosController {

	@Autowired // Pede para o Spring uma instância desse objeto que foi anotado (Injeção de
				// dependência)
	private ProdutoDAO produtoDao;

	@RequestMapping("/form")
	public ModelAndView form() {
		// ModelAndView - Além de retornar uma página, possibilita enviar objetos de
		// qualquer classe para essas páginas
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		System.out.println("Acessando form de produtos");

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST) // Method POST para gravar
	public ModelAndView gravar(Produto produto, RedirectAttributes redirectAttributes) { // RedirectAttributtes - Recurso do Spring que permite enviar informações entre requisições.
		System.out.println(produto);
		produtoDao.gravar(produto);
		
		 redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!"); // Atributos do tipo Flash só duram até a próxima requisição, ou seja, transportam informações de uma requisição para a outra e deixam de existir.
		 return new ModelAndView("redirect:produtos"); // redirect, passa um status 302 para o navegador carregar uma outra página e esquecer dos dados da requisição anterior.
		 // resolve bug que duplica os registros ao ser pressionado f5
	}

	@RequestMapping(method = RequestMethod.GET) // Method GET para listar
	public ModelAndView listar() {
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);

		return modelAndView;
	}

}
