package com.intech.test.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Тестовое приложение SpringBoot + ReactJs
 * TODO:  - валидация (руками выполняется, автоматическая - проблема)
 * 		- @Transactional не работает (нужно какое-то решение в обход)
 * 
 * @author a.palkin
 *
 */
@SpringBootApplication
public class TestReactProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestReactProjectApplication.class, args);
	}

}
