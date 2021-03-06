package br.com.alura.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.infra.FileSaver;
import br.com.alura.casadocodigo.loja.models.Produto;
import br.com.alura.casadocodigo.loja.models.TipoPreco;
import br.com.alura.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos") 
public class ProdutosController {

	@Autowired // Pede para o Spring uma instância desse objeto que foi anotado (Injeção de dependência)
	private ProdutoDAO produtoDao;
	
	@Autowired
	private FileSaver fileSaver;
	
	/* O Binder é o responsável por conectar duas coisas. Por exemplo, os dados do formulário com o objeto da classe Produto
	Método para que o ProdutosController reconheça o validador, responsável por vincular o validador com o controller */
	@InitBinder
	public void initBinder(WebDataBinder binder) { 
		binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto) {
		/* ModelAndView - Além de retornar uma página, possibilita enviar objetos de
		qualquer classe para essas páginas */
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		System.out.println("Acessando form de produtos");

		return modelAndView;
	}

	/* @Valid -  Faz com que o produto seja validado
	BindingResult - Pega os erros obtidos durante a validação. Para que isso funcione, é necessário adicionar logo em seguida do objeto que será validado */
	@RequestMapping(method = RequestMethod.POST) // Method POST para gravar
	@CacheEvict(value="produtosHome", allEntries = true) // Tira do cache os produtos quando esse método for chamado através do valor(produtosHome). allEntries, todas as entradas/valores
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) { // RedirectAttributtes - Recurso do Spring que permite enviar informações entre requisições.
		System.out.println(sumario.getOriginalFilename()); 
		
		if(result.hasErrors()) {
			return form(produto);
		}
		
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		System.out.println(produto);
		
		produtoDao.gravar(produto);
		
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!"); // Atributos do tipo Flash só duram até a próxima requisição, ou seja, transportam informações de uma requisição para a outra e deixam de existir.
		 
		 /* redirect, passa um status 302 para o navegador carregar uma outra página e esquecer dos dados da requisição anterior.
		 Para evitar qualquer problema de dados reenviados, realizamos um redirect após um formulário com POST. Pois ao fazer F5 o navegador repete o ultimo request que ele realizou, e quando esse resquest é um POST, 
		 todos os dados que foram enviados também são repetidos. Se realizou um insert no banco de dados, esse insert será repetido ou mesmo se realizou alguma operação que envia e-mail, por exemplo, o e-mail será enviado duas vezes
		 ao pressionar F5 */
		 return new ModelAndView("redirect:produtos"); 
	}

	// Method GET para listar
	@RequestMapping(method = RequestMethod.GET) 
	public ModelAndView listar() {
		List<Produto> produtos = produtoDao.listar();
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);

		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) { // PathVariable - É uma variável que está no path, na url do navegador, que irá ligar no mapeamento "/detalhehttps://github.com/ghmiyoshi/casa-do-codigo.git/id"
		ModelAndView modelAndView = new ModelAndView("produtos/detalhe");

		Produto produto = produtoDao.find(id);
		modelAndView.addObject("produto", produto);
		
		return modelAndView ;
	}
	
//	@RequestMapping("/{id}")
//	@ResponseBody
//	public Produto detalheJson(@PathVariable("id") Integer id) { // PathVariable - É uma variável que está no path, na url do navegador, que irá ligar no mapeamento "/detalhehttps://github.com/ghmiyoshi/casa-do-codigo.git/id"
//		
//		return produtoDao.find(id);
//		
//	}

}
