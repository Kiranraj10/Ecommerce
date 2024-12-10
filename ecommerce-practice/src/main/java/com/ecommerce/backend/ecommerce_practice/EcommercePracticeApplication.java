package com.ecommerce.backend.ecommerce_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommercePracticeApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(EcommercePracticeApplication.class, args);
		Test t= context.getBean(Test.class);
		System.out.println(t.sayHello());
	}
	@Bean
	public Test test(){
		return new Test();
	}


}
