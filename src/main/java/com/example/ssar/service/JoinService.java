package com.example.ssar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ssar.dto.JoinDTO;
import com.example.ssar.entity.UserEntity;
import com.example.ssar.repository.UserRepository;

@Service
public class JoinService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void joinProcess(JoinDTO joinDTO) {

		 //db에 이미 동일한 username을 가진 회원이 존재하는지?
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return;
        }


		UserEntity data = new UserEntity();

		data.setUsername(joinDTO.getUsername());
		data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
		
		if(joinDTO.getUsername().toLowerCase().contains("admin")) {
			data.setRole("ROLE_ADMIN");
		}else if(joinDTO.getUsername().toLowerCase().contains("manager")) {
			data.setRole("ROLE_MANAGER");
		}else {
			data.setRole("ROLE_USER");
		}
		userRepository.save(data);
	}

}
