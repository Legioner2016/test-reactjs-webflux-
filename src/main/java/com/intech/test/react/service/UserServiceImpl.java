package com.intech.test.react.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intech.test.react.bean.MyResult;
import com.intech.test.react.bean.NewUserBean;
import com.intech.test.react.models.User;
import com.intech.test.react.models.UserRole;
import com.intech.test.react.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository		userRepository;
	
	@Transactional(readOnly = true)
	@Override
	public Mono<MyResult> saveNewUser(Mono<NewUserBean> bean) {
		return bean.map(b -> {
			boolean not_exists = !userRepository.findByLoginIgnoreCase(b.getLogin().trim()).isPresent();
			if (not_exists) {
				User user = new User();
				user.setLogin(b.getLogin());
				user.setName(b.getName());
				user.setSurname(b.getSurname());
				user.setRole(UserRole.USER);
				userRepository.save(user);
				return new MyResult(true, null);
			}
			else {
				return new MyResult(false, "User already exists");				
			}
		});
	}


}
