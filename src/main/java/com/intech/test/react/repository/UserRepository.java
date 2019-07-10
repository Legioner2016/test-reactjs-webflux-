package com.intech.test.react.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.intech.test.react.models.User;

/**
 * Spring Data. Только один дополнительный метод - найти по логин-у
 * 
 * @author a.palkin
 *
 */
public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByLoginIgnoreCase(String login);

}
