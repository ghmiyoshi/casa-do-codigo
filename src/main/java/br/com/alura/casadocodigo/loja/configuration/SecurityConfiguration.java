package br.com.alura.casadocodigo.loja.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.alura.casadocodigo.loja.daos.UsuarioDAO;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioDAO usuarioDao;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // antMatchers - Decido se o acesso a os requests será permitido ou bloqueado
				.antMatchers("/produtos/form").hasRole("ADMIN")
				.antMatchers("/carrinho/**").permitAll()
				.antMatchers("/pagamento/**").permitAll()
				.antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
				.antMatchers("/produtos/**").permitAll()
				.antMatchers("/").permitAll().anyRequest()
				.authenticated() // Todos os requests precisam estar autenticados
				.and().formLogin().loginPage("/login") // Se ele não estiver autenticado é direcionado para o formulário de login
				.defaultSuccessUrl("/produtos").permitAll()
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll() 
	            .logoutSuccessUrl("/login");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao).passwordEncoder(new BCryptPasswordEncoder());
	}
	
    // Forma recomendada de ignorar no filtro de segurança as requisições para recursos estáticos
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

}
