package br.com.alura.casadocodigo.loja.configuracao;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// Servlet de configuração do SpringMVC para atender as requisições
public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppWebConfiguration.class, JPAConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" }; // Defini a URL que o Spring deve mapear / para frente
	}

}
