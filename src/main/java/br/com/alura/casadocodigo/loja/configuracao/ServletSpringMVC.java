package br.com.alura.casadocodigo.loja.configuracao;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
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
	
	// Registrando filtro para resolver erros de encoding
	// Toda requisição que chegar para o Spring ele transforma para UTF-8 
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		
		return new Filter[] {characterEncodingFilter}; 
	}

}
