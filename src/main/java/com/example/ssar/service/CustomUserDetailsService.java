package com.example.ssar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ssar.dto.CustomUserDetails;
import com.example.ssar.entity.UserEntity;
import com.example.ssar.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userData = userRepository.findByUsername(username);

		if (userData != null) {

			return new CustomUserDetails(userData);
		}

		return null;
	}

}
