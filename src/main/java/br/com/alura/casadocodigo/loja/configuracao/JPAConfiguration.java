package br.com.alura.casadocodigo.loja.configuracao;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
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

		// Adapter que o Hibernate disponibiliza
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		factoryBean.setJpaVendorAdapter(vendorAdapter);

		// DataSource que contém as configurações de banco de dados
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("");
		dataSource.setUrl("jdbc:mysql://localhost/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		factoryBean.setDataSource(dataSource);

		// Properties para poder setar algumas configurações
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		factoryBean.setJpaProperties(properties);

		// Setar onde o EntityManager encontrará nossos Models
		factoryBean.setPackagesToScan("br.com.alura.casadocodigo.loja.models");

		// Retornar as configurações para o Spring poder utiliza-las.
		return factoryBean;
	}

	@Bean
	// Método que cria o TransactionManager
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
