package com.intech.test.react.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.intech.test.react.models.UserRole;

/**
 * Security конфигурация. 
 * logout - через post запрос (чуть гемморойнее обернуть в форме, но суть та же)
 * 
 * @author a.palkin
 *
 */
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
        	.formLogin()
        		.loginPage("/login")
        		.authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/"))
        	.and()
        		.logout()
//        		.logoutUrl("/logout")        		
//        		.logoutSuccessHandler(logoutSuccessHandler("/login"))
        	.and()
        	.authorizeExchange()
			.pathMatchers("/login",
	                "/logout",
	                "/favicon.ico",
	                "/webjars/**")
            .permitAll()
            .pathMatchers("/**")
            .authenticated()
            .and()
        	.csrf().disable();
		return http.build();
	}
	
	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		 UserDetails user = User.withDefaultPasswordEncoder()
				 .username("user")
				 .password("pass")
				 .roles(UserRole.USER.name())
				 .build();
	    return new MapReactiveUserDetailsService(user);
	}
	
//    public ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
//        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
//        successHandler.setLogoutSuccessUrl(URI.create(uri));
//        return successHandler;
//    }
	
	
}