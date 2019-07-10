package com.intech.test.react.config;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * Маппинг url-ов  с обработчиками 
 * 
 * @author a.palkin
 *
 */
@Configuration
public class WebFluxRoutes {

	@Autowired
	private WebFluxHandlerFunctions handlerFunctions;
	
	@Bean
	public RouterFunction<ServerResponse> routes(){
		return RouterFunctions.route(GET("/login"), handlerFunctions::login)
				.and(
						RouterFunctions.route(GET("/"), handlerFunctions::mainPage)
				)
				.and(
						RouterFunctions.route(GET("/listUsers"), handlerFunctions::listUsers)
				)
				.and(
						RouterFunctions.route(DELETE("/deleteUser"), handlerFunctions::deleteUser)
				)
				.and(
						RouterFunctions.route(POST("/saveUser"), handlerFunctions::saveUser)
				)
				.and(
						RouterFunctions.route(GET("/longRequest"), handlerFunctions::longRequest)
				);

	}
	
}
