package br.com.alura.casadocodigo.loja.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.casadocodigo.loja.daos.UsuarioDAO;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // antMatchers - Decido se o acesso a os requests será permitido ou bloqueado
				.antMatchers("/resources/**").permitAll().antMatchers("/produtos/form").hasRole("ADMIN")
				.antMatchers("/carrinho/**").permitAll().antMatchers("/pagamento/**").permitAll()
				.antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
				.antMatchers("/produtos/**").permitAll().antMatchers("/").permitAll().anyRequest()
				.authenticated() // Todos os requests precisam estar autenticados
				.and().formLogin(); // Se ele não estiver autenticado é direcionado para o formulário de login
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao).passwordEncoder(new BCryptPasswordEncoder());
	}

}
