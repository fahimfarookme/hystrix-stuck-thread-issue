package me.fahimfarook.spring.cloud.hystrix.core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.support.StandardServletEnvironment;

/**
 * @author Fahim Farook
 */
public class HystrixStreamEnvironmentPostProcessorTests {

	private final StandardServletEnvironment environment = new StandardServletEnvironment();
	
	private final HystrixStreamEnvironmentPostProcessor processor = new HystrixStreamEnvironmentPostProcessor();

	@Test
	public void applyEnvironmentPostProcessor() {
		assertFalse(this.environment.containsProperty("hystrix.stream.endpoint.enabled"));
		this.processor.postProcessEnvironment(environment, new SpringApplication());
		assertTrue(this.environment.containsProperty("hystrix.stream.endpoint.enabled"));
		assertThat(this.environment.getProperty("hystrix.stream.endpoint.enabled"), is("false"));
	}
}
