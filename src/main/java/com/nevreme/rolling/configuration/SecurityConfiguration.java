package com.nevreme.rolling.configuration;

import com.nevreme.rolling.utils.Constants;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	

	@Autowired
	@Qualifier("primaryDatasource")
	private DataSource primaryDatasource;
	
	@Autowired
	@Qualifier("secondDatasource")
	private DataSource secondDatasource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
		
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(bCryptPasswordEncoder);
	    return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider());
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(secondDatasource);
//				.passwordEncoder(bCryptPasswordEncoder);
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (System.getProperty("APP_ROOT") == null) {
			System.out.println("****SETUP NOT OK****");
			System.setProperty("APP_ROOT", Constants.APP_ROOT);
		}
		System.out.println("************APPROOT**************"+System.getProperty("APP_ROOT"));
		http.
			authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/p/**").permitAll()
				.antMatchers("/ws/**").permitAll()
				.antMatchers("/res/**").permitAll()
				.antMatchers("/public/**").permitAll()
				.antMatchers("/home/**").permitAll()
				.antMatchers("/music/**").permitAll()
				.antMatchers("/contact/**").permitAll()
				.antMatchers("/answer/**").permitAll()
				.antMatchers("/question/**").permitAll()
				.antMatchers("/movies/**").permitAll()
				.antMatchers("/sport/**").permitAll()
				.antMatchers("/culture/**").permitAll()
				.antMatchers("/video/**").permitAll()
				.antMatchers("/recommend/**").permitAll()
				.antMatchers("/text/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/editor/**").hasAnyRole(new String[]{"ADMIN"}).anyRequest()
				.authenticated()
				.antMatchers("/admin/**").hasAnyRole(new String[]{"ADMIN"}).anyRequest()
				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl(System.getProperty("APP_ROOT")+"/admin/setup")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl(System.getProperty("APP_ROOT")).and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/theme/**");
	}

}