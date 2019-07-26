package br.com.alura.casadocodigo.loja.configuracao;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()	// antMatchers - Decidimos se o acesso a os requests será permitido ou bloqueado
		.antMatchers("/produtos/form").hasRole("ADMIN")
		.antMatchers("/carrinho").permitAll()
		.antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/produtos").permitAll()
		.antMatchers("/produtos/**").permitAll()
		.antMatchers("/").permitAll()
		.anyRequest().authenticated() // Todos os requests são verificados se o usuário está autenticado
		.and().formLogin(); // Se ele não estiver autenticado é direcionado para o formulário de login
	}
	
}
