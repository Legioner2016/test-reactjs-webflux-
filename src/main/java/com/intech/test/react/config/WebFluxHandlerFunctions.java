package com.intech.test.react.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.intech.test.react.bean.MyResult;
import com.intech.test.react.bean.NewUserBean;
import com.intech.test.react.bean.ProgressStatus;
import com.intech.test.react.models.User;
import com.intech.test.react.models.UserRole;
import com.intech.test.react.repository.UserRepository;
import com.intech.test.react.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Обработчики для webFlux функций
 * 
 * @author a.palkin
 *
 */
@Component
public class WebFluxHandlerFunctions {

	
	@Autowired
	private UserRepository 	userRepository;
	
	//Тестировал проверить применение @Valid - не прокатило
	@Autowired
	private Validator 		validator;

	@Autowired
	private UserService 	userService;
	
	//Для теста длительного запроса
	private int percentage = 0;
	
	private class TestThread implements Runnable {

		@Override
		public void run() {
			while (percentage < 100) {
				try {
					Thread.currentThread().sleep(2500);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				percentage += (int)(Math.random() * 10) + 1;
				if (percentage > 100) percentage = 100;
			}
		}
	}
	
	//Форма логина
    public Mono<ServerResponse> login(ServerRequest request)
    {
        Map<String,Object> model = new HashMap<>();
        if (request.queryParam("error").isPresent()) {
        	model.put("loginError", true);
        } 
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("login", model);
    }
    
    //Основная страница приложения
    public Mono<ServerResponse> mainPage(ServerRequest request) {
        Map<String,Object> model = new HashMap<>();
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("index", model);
    }
    
    //Список пользователей (ajax)
    public Mono<ServerResponse> listUsers(ServerRequest request) {
        return ServerResponse.ok()
        				.contentType(MediaType.APPLICATION_JSON_UTF8)
        				.body(Flux.fromIterable(userRepository.findAll()), User.class);
    }
    
    //Удалить пользователя  (ajax)
    @Transactional(readOnly = false)
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
    	try {
        	if (request.queryParam("id").isPresent()) {
        		userRepository.deleteById(Integer.parseInt(request.queryParam("id").get()));
        	} 
        	else {
        		throw new Exception("no user found");
        	}
    	} catch (Exception ex) {
            return ServerResponse.ok()
    				.contentType(MediaType.APPLICATION_JSON_UTF8)
    				.body(Mono.just(new MyResult(false, ex.getMessage())), MyResult.class);
    	}
        return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(new MyResult(true, null)), MyResult.class);
    }

    //Добавить нового пользователя (ajax)
    @Transactional(readOnly = false)
    public Mono<ServerResponse> saveUser(ServerRequest request) {
    	try {
    		Mono<@Valid NewUserBean> bean = request.bodyToMono(NewUserBean.class);
    		Set<ConstraintViolation<Mono<NewUserBean>>> errors =  validator.validate(bean);
    		if (!errors.isEmpty()) {
                return ServerResponse.ok()
        				.contentType(MediaType.APPLICATION_JSON_UTF8)
        				.body(Flux.fromIterable(errors), ConstraintViolation.class);
    		}
    	} catch (Exception ex) {
            return ServerResponse.ok()
    				.contentType(MediaType.APPLICATION_JSON_UTF8)
    				.body(Mono.just(new MyResult(false, ex.getMessage())), MyResult.class);
    	}
        return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(userService.saveNewUser(request.bodyToMono(NewUserBean.class)), MyResult.class);
    }
    
    //Запрос для "длительного" запроса (EventSource)
    public Mono<ServerResponse> longRequest(ServerRequest request) {
    	percentage = 0;
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(new TestThread());
		Stream<ProgressStatus> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<ProgressStatus>() {
			private boolean done = false;
			
		    @Override
		    public boolean hasNext() {
		        return !done;
		    }

		    @Override
		    public ProgressStatus next() {
		    	done = !(percentage < 100);
		        return new ProgressStatus(done, percentage);
		    }
		}, Spliterator.IMMUTABLE), false);
		
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        Flux<ProgressStatus> data = Flux.fromStream(stream);
        Flux<ProgressStatus> flux = Flux.zip(data, interval, (key, value) -> key);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(flux, ProgressStatus.class);
    	
    }
	
}