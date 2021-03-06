package com.oss;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.oss.customer.repository.Customer;
import com.oss.customer.repository.CustomerRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... strings) throws Exception {

		// save a couple of customers
		Date today = new Date();
		repository.deleteAll();
		repository.save(new Customer("Jack Bauer", "Jack@builder.com", today));
		repository.save(new Customer("Chloe O'Brian", "Chloe@builder.com", today));
		repository.save(new Customer("Kim Bauer", "Kim@builder.com", today));
		repository.save(new Customer("David Palmer", "David@builder.com", today));
		repository.save(new Customer("Michelle Dessler", "Michelle@builder.com", today));
		repository.save(new Customer("Tony Qi", "qijunbo@builder.com", today));
		repository.save(new Customer("Jone Fathe", "jone@builder.com", today));
		// fetch all customers
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
		for (Customer customer : repository.findAll()) {
			log.info(customer.toString());
		}

	}

}
