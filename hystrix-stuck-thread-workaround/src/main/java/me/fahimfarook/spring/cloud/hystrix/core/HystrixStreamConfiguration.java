package me.fahimfarook.spring.cloud.hystrix.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.endpoint.ServletWrappingEndpoint;
import org.springframework.cloud.netflix.hystrix.HystrixCircuitBreakerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.ServletWrappingController;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * Solution for <a href=
 * 'https://github.com/spring-cloud/spring-cloud-netflix/issues/2301'>this
 * issue.</a>
 * 
 * <p>
 * Spring cloud has embedded HystrixMetricsStreamServlet on an Endpoint (i.e.
 * ServletWrappingEndpoint >> MvcEndpoint). Spring calls Endpoint classes'
 * handle(req, res) methods in which HystrixMetricsStreamServlet.service() is
 * programmatically invoked - i.e. never registers HystrixMetricsStreamServlet
 * as a servlet with the container. Therefore I have explicitly registered it as
 * a servlet.
 * 
 * <p>
 * TODO throw IllegalStateException if the hystrix work-manager is not found.
 * 
 * @author Fahim Farook
 * @see {@link ServletWrappingEndpoint} and {@link ServletWrappingController}.
 * 
 */
@Configuration
@ConditionalOnProperty(value = "hystrix.stream.endpoint.enabled", havingValue = "false")
@ConditionalOnWebApplication
@ConditionalOnClass({ HystrixCircuitBreakerConfiguration.class })
@EnableConfigurationProperties({HystrixStreamProperties.class})
public class HystrixStreamConfiguration {
		
	private static final Logger logger = LoggerFactory.getLogger(HystrixStreamConfiguration.class);
	
	@Autowired
	private HystrixStreamProperties properties;

	@Bean
	public ServletRegistrationBean hystrixStreamServlet() {
		logger.info("Registering HystrixMetricsStreamServlet with the container.");
		final HystrixMetricsStreamServlet hystrixStreamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean bean = new ServletRegistrationBean(hystrixStreamServlet, "/hystrix.stream");
		bean.setInitParameters(this.properties.getInitParameters());
		return bean;
	}
}