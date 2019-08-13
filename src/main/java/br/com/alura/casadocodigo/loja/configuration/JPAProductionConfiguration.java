package br.com.alura.casadocodigo.loja.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Profile("prod")
public class JPAProductionConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public Properties aditionalProperties() {
		// Properties para poder setar algumas configurações
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		
		return properties;
	}

	@Bean
	public DriverManagerDataSource dataSource() throws URISyntaxException {
		// DataSource que contém as configurações de banco de dados
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");

		// usuario:senha@host:port:path
		URI dbUrl = new URI(environment.getProperty("DATABASE_URL"));

		dataSource.setUrl("jdbc:postgresql://" + dbUrl.getHost() + ":" + dbUrl.getPort() + dbUrl.getPath());
		dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
		dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);

		return dataSource;
	}

}
