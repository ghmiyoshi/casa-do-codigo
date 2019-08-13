package br.com.alura.casadocodigo.loja.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

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
	public CacheManager cacheManager() { // Método gerenciador de cache para o Spring usar. É um mapa (guarda chave e valor)
		//  Guava é um framework de cache fornecido pelo Google. Essa configuração é para permitir que o cache fique ativo por 5 minutos e guarde 100 elementos
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
		.maximumSize(100)
		.expireAfterAccess(5,  TimeUnit.MINUTES);
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);
		
		return manager;
	}
	
	@Bean
	public ViewResolver contentNegotiation(ContentNegotiationManager manager) { // O manager é o responsável pela decisão de qual view será utilizada
		List<ViewResolver> viewResolvers = new ArrayList<>();
		viewResolvers.add(InternalResourceViewResolver()); // Crio uma Lista de viewResolvers e nela adiciono as duas opções de view (InternalResourceViewResolver - HTML / JsonViewResolver - JSON)
		viewResolvers.add(new JsonViewResolver());		
		
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers); // O objeto resolver recebe a lista de Resolvers 
		resolver.setContentNegotiationManager(manager); // O manager escolhe entre os viewsResolvers disponíveis
		
		return resolver;
	}
	
	// uso o método addInterceptor para adicionar um novo interceptador, ele verifica a mudança de locale do usuário
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	// o responsável por interpretar a mudança é um interceptor, armazeno o valor do locale do usuário nos cookies do navegador e carrego as páginas no idioma correto.
	// Como este método retorna um objeto a ser usado pelo Spring, preciso anotar com @Bean
	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}
	
	// o Spring possui uma interface que é responsável pelo envio de e-mail, e no @Bean que criei, usei uma classe que implementava a Interface. 
	// Para que o envio de e-mail funcione corretamente, a interface deve ser MailSender e a classe de implementação da interface deve ser JavaMailSenderImpl.
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("user@gmail.com");
		mailSender.setPassword("password");
		mailSender.setPort(587);

		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);

		mailSender.setJavaMailProperties(mailProperties);

		return mailSender;
	}
	
}
