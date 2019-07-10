package com.intech.test.react.service;

import com.intech.test.react.bean.MyResult;
import com.intech.test.react.bean.NewUserBean;

import reactor.core.publisher.Mono;

/**
 * Сервис управления пользователями 
 * 
 * @author a.palkin
 *
 */
public interface UserService {

	//Сохранить пользователя (но сначала проверить, что нет уже с таким же логином,  в однорй транзакции)
	Mono<MyResult> saveNewUser(Mono<NewUserBean> bean);
	
}
