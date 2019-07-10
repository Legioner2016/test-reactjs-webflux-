package com.intech.test.react.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс результата запроса к серверу.
 * Успешно и текст ошибки
 *  
 * @author a.palkin
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyResult {

	private boolean success = false;
	private String error = null;
	
	
	
}
