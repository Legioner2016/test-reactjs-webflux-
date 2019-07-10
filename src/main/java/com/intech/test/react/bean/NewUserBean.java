package com.intech.test.react.bean;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Бин данных нового пользователя
 * 
 * @author a.palkin
 *
 */
@Data
@NoArgsConstructor
public class NewUserBean {

	@NotEmpty
	private String login;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;

}
