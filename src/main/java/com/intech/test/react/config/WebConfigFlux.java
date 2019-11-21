package com.intech.test.react.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/*
 * TODO: resources from webjars (static works, from webjars - don`t)
 */
@Configuration
@EnableWebFlux
//@EnableAutoConfiguration(exclude = ThymeleafAutoConfiguration.class)
@ComponentScan(basePackages = "com.intech.test.react")
@EnableJpaRepositories(basePackages = {"com.intech.test.react.repository"})
public class WebConfigFlux implements ApplicationContextAware, WebFluxConfigurer {

    private ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.ctx = context;
    }

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {

        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.ctx);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(false);
        resolver.setCheckExistence(false);
        return resolver;

    }

    @Bean
    public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
        // We override here the SpringTemplateEngine instance that would otherwise be
        // instantiated by
        // Spring Boot because we want to apply the SpringWebFlux-specific context
        // factory, link builder...
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver thymeleafChunkedAndDataDrivenViewResolver() {
        final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        viewResolver.setResponseMaxChunkSizeBytes(16384); // OUTPUT BUFFER size limit
        return viewResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafChunkedAndDataDrivenViewResolver());
    }
    
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        	.addResourceHandler("/webjars/**")
        	.addResourceLocations("/webjars/")
        	.resourceChain(true);
      
	}

}