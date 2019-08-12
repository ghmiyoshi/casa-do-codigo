package br.com.alura.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.casadocodigo.loja.models.CarrinhoCompras;
import br.com.alura.casadocodigo.loja.models.DadosPagamento;
import br.com.alura.casadocodigo.loja.models.Usuario;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinho;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender sender;

	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario, RedirectAttributes model) { // Callable - Faz com que ao o usuário finalizar uma compra a requisição seja feita de forma assíncrona e que somente este usuário aguarde a resposta do processamento. Desta forma os demais usuários continuam usando a aplicação sem nenhum problema.
		return () -> {

			String uri = "http://book-payment.herokuapp.com/payment";
			System.out.println(carrinho.getTotal());

			try {
				String resposta = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()),
						String.class);
				System.out.println(resposta);
				
				enviaEmailCompraProduto(usuario);
				
				model.addFlashAttribute("sucesso", resposta);
				
				return new ModelAndView("redirect:/produtos");

			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("falha", "Valor maior que o permitido");
				System.out.println(e);
				return new ModelAndView("redirect:/produtos");
			}
		};
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		email.setTo(usuario.getEmail());
		email.setText("Compra aprovada com sucesso no valor de R$ " + carrinho.getTotal());
		email.setFrom("compras@casadocodigo.com.br");
		
		sender.send(email);
	}

}
