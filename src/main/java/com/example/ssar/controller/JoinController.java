package com.example.ssar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ssar.dto.JoinDTO;
import com.example.ssar.service.JoinService;

@Controller
public class JoinController {

	@Autowired
	private JoinService joinService;

	@GetMapping("/join")
	public String joinP() {

		return "join";
	}

	@PostMapping("/joinProc")
	public String joinProcess(JoinDTO joinDTO) {

		System.out.println(joinDTO.getUsername());

		joinService.joinProcess(joinDTO);

		return "redirect:/login";
	}

}
