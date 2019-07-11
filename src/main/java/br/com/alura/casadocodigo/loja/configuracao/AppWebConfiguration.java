package br.com.alura.casadocodigo.loja.configuracao;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.alura.casadocodigo.loja.controllers.HomeController;
import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;

@EnableWebMvc // Habilita o uso do SpringMVC no projeto
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDAO.class }) // Configuração para o Spring encontrar os controllers e daos 
public class AppWebConfiguration {

	// Metodo que ajuda o SpringMVC a encontrar as views
	// Retorna um objeto do tipo InternalResourceViewResolver (Resolvedor Interno de Recursos de View)
	// A anotação @Bean é para que o retorno da chamada deste metódo possa ser gerenciada pelo SpringMVC, sem ela a configuração não funciona
	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		InternalResourceViewResolver internalResourceView = new InternalResourceViewResolver();
		internalResourceView.setPrefix("/WEB-INF/views/"); // Define o caminho onde estão as views
		internalResourceView.setSuffix(".jsp"); // Adiciona a extensão dos arquivos de view

		return internalResourceView;
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1);
		
		return messageSource;
	}
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
}
