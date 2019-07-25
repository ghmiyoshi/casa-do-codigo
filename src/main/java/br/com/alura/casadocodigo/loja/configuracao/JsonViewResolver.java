package br.com.alura.casadocodigo.loja.configuracao;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {

	
	// Esse método retorna como view o JSON que representa os produtos. O próprio Spring tem uma classe que é da integração com a biblioteca Jackson
	// e se chama MappingJackson2JsonView. Com isso, só preciso retornar um objeto desta classe
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true); // Mantém formatação amigável ao retornar o JSON dos nossos produtos
		
		return jsonView;
	}

}
