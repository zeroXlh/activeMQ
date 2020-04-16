package com.zero.activeMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableJms
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
