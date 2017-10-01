package me.fahimfarook.spring.cloud.hystrix.core;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerConfiguration;
import org.springframework.cloud.netflix.hystrix.HystrixStreamEndpoint;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * The purpose of this class is to set
 * <code>hystrix.stream.endpoint.enabled</code> to false in
 * <code>HystrixCircuitBreakerConfiguration.java#L74</code>. This could have
 * been done in {@link HystrixStreamConfiguration}, but we cannot guarantee that
 * {@link HystrixStreamConfiguration} is constructed before
 * {@link HystrixCircuitBreakerConfiguration} - so that
 * <code>hystrix.stream.endpoint.enabled</code> might not have been set when
 * spring-cloud creates {@link HystrixStreamEndpoint}.
 * 
 * <p>
 * This property is not set in any spring cloud default properties - therefore
 * no need to override "defaultPropertySource".
 * 
 * @author Fahim Farook
 *
 */
public class HystrixStreamEnvironmentPostProcessor implements EnvironmentPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(HystrixStreamEnvironmentPostProcessor.class);
	
	@Override
	public void postProcessEnvironment(final ConfigurableEnvironment environment, final SpringApplication application) {
		logger.info("Overriding environmnet for WebLogic - disabling hystrix.stream.endpoint.enabled");
		final PropertySource<Map<String, Object>> source = 
		new MapPropertySource("hystrixProperties", Collections.singletonMap("hystrix.stream.endpoint.enabled", "false"));
		environment.getPropertySources().addLast(source);
	}

}