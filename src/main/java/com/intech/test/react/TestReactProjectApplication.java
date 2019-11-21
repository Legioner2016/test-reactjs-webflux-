package com.intech.test.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.web.server.adapter.AbstractReactiveWebInitializer;

import com.intech.test.react.config.SecurityConfig;
import com.intech.test.react.config.WebFluxRoutes;
import com.intech.test.react.config.WebConfigFlux;

/**
 * Тестовое приложение SpringBoot + ReactJs
 * TODO:  - валидация (руками выполняется, автоматическая - проблема)
 * 		- @Transactional не работает (нужно какое-то решение в обход)
 * 
 * @author a.palkin
 *
 */
@SpringBootApplication
public class TestReactProjectApplication extends AbstractReactiveWebInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TestReactProjectApplication.class, args);
	}
	
    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class[]{
        		WebFluxRoutes.class,
                SecurityConfig.class,
                WebConfigFlux.class
        };
    }
	

}
