package com.swaroop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.swaroop.entity.User;
import com.swaroop.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Cacheable(value = "users", key = "'allUsers'")
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

	@Cacheable(value = "users", key = "#id")
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	@CachePut(value = "users", key = "#result.id")
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@CacheEvict(value = "users", key = "#id")
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
