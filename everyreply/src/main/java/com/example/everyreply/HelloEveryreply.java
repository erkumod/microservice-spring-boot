package com.example.everyreply;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloEveryreply {
	@RequestMapping("/getName")
	String HelloMethod() {
		return "Hello Raju Pandey EveryReply";
	}

}
