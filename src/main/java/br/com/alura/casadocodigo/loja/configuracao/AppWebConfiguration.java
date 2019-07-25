package br.com.alura.casadocodigo.loja.configuracao;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.alura.casadocodigo.loja.controllers.HomeController;
import br.com.alura.casadocodigo.loja.daos.ProdutoDAO;
import br.com.alura.casadocodigo.loja.infra.FileSaver;
import br.com.alura.casadocodigo.loja.models.CarrinhoCompras;

@EnableWebMvc // Habilita o uso do SpringMVC no projeto
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDAO.class, FileSaver.class, CarrinhoCompras.class }) // Configuração para o Spring encontrar/scannear os controllers, daos e outras classes 
@EnableCaching // Habilita o  cache, um recurso que permite guardar informações no contexto da aplicação
public class AppWebConfiguration extends WebMvcConfigurerAdapter {

	// Metodo que ajuda o SpringMVC a encontrar as views
	// Retorna um objeto do tipo InternalResourceViewResolver (Resolvedor Interno de Recursos de View)
	// A anotação @Bean é para que o retorno da chamada deste metódo possa ser gerenciada pelo SpringMVC, sem ela a configuração não funciona
	@Bean
	public InternalResourceViewResolver InternalResourceViewResolver() {
		InternalResourceViewResolver internalResourceView = new InternalResourceViewResolver();
		internalResourceView.setPrefix("/WEB-INF/views/"); // Define o caminho onde estão as views
		internalResourceView.setSuffix(".jsp"); // Adiciona a extensão dos arquivos de view

		internalResourceView.setExposedContextBeanNames("carrinhoCompras");
		
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
	
	// Método para não ter que configurar o formato da data por anotações no model
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	// Configuração do MultipartResolver para resolver esses arquivos de multiplos formatos
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	// Por padrão, o Spring MVC nega o acesso à pasta resources. Consequentemente, o Tomcat não pode carregar os arquivos CSS e a página fica sem design.
	// Por isso, a classe precisa estender a classe WebMvcConfigurerAdapter e implementar o método configureDefaultServletHandling para liberar o acesso à pasta resources
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	    configurer.enable();
	}
	
	@Bean
	public RestTemplate restTemplate() { // Método para criar uma configuração básica para que o Spring consiga criar o objeto RestTemplate corretamente
		return new RestTemplate();
	}
	
	@Bean
	public CacheManager cacheManager() { // Método gerenciador de cache para o Spring usar.
		return new ConcurrentMapCacheManager();
	}
}
