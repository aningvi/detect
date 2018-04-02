package edu.zstu.web;
/**
 * @Author: Aning
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = {"edu.zstu"})

public class DetectWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectWebApplication.class, args);
	}
}
