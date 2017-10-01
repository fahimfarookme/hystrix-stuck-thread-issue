package me.fahimfarook.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import me.fahimfarook.spring.cloud.hystrix.dashboard.EnableWebLogicHystrixDashboard;

@EnableCircuitBreaker
@EnableHystrix
@EnableWebLogicHystrixDashboard
@SpringBootApplication
public class HystrixStuckThreadClientApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(HystrixStuckThreadClientApplication.class, args);
	}
}

interface Api {

	@RequestMapping("/success")
	String success();

	@RequestMapping("/failure")
	String failure();

	String fallback();
}

@RestController
class Resource implements Api {

	@HystrixCommand(fallbackMethod = "fallback")
	@Override
	public String success() {
		return "Alhamdulillah!!";
	}

	@HystrixCommand(fallbackMethod = "fallback")
	@Override
	public String failure() {
		throw new IllegalStateException("Failure scenario!");
	}

	@Override
	public String fallback() {
		return "Fallbak";
	}
}
