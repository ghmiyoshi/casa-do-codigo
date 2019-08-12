package br.com.alura.casadocodigo.loja.configuracao;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Profile("prod")
public class JPAProductionConfiguration {

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
	public DriverManagerDataSource dataSource() {
		// DataSource que contém as configurações de banco de dados
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		environment.getProperty("DATABASE_URL");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setUrl("jdbc:mysql://localhost/casadocodigo");

		return dataSource;
	}

}
