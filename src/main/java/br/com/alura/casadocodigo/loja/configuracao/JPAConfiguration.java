package br.com.alura.casadocodigo.loja.configuracao;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // O Spring ativa o gerenciamento de transações e já reconhece o TransactionManager
public class JPAConfiguration {

	@Bean // Anotação para que o retorno da chamada deste metódo possa ser gerenciada pelo SpringMVC
	// Método que será usado pelo Spring para gerar o EntityManager
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		// Setar onde o EntityManager encontrará nossos Models
		factoryBean.setPackagesToScan("br.com.alura.casadocodigo.loja.models");
		factoryBean.setDataSource(dataSource());
		
		// Adapter que o Hibernate disponibiliza
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setJpaProperties(aditionalProperties());

		// Retornar as configurações para o Spring poder utiliza-las.
		return factoryBean;
	}

	private Properties aditionalProperties() {
		// Properties para poder setar algumas configurações
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		return properties;
	}
	
	@Bean
	@Profile("dev")
	public DriverManagerDataSource dataSource() {
		// DataSource que contém as configurações de banco de dados
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setUrl("jdbc:mysql://localhost/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		
		return dataSource;
	}

	@Bean
	// Método que cria o TransactionManager
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
