package me.fahimfarook.spring.cloud.hystrix.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

/**
 * Properties to manage core Hystrix stream behavior.
 * 
 * @author Fahim Farook
 */
@ConfigurationProperties("hystrix.stream")
public class HystrixStreamProperties {

	/**
	 * Initialization parameters for {@link ServletRegistrationBean}.
	 * ServletRegistrationBean itself is not dependent on any initialization
	 * parameters, but could be used for adding web container specific
	 * configurations. i.e. wl-dispatch-policy for WebLogic.
	 */
	private Map<String, String> initParameters = new HashMap<>();

	public Map<String, String> getInitParameters() {
		return this.initParameters;
	}
}
