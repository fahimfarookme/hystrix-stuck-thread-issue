package me.fahimfarook.spring.cloud.hystrix.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.HystrixDashboardConfiguration;
import org.springframework.cloud.netflix.hystrix.dashboard.HystrixDashboardProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Not extending from {@link HystrixDashboardConfiguration} because will cause
 * spring to override all the beans in HystrixDashboardConfiguration. Therefore
 * just <code>@Import(HystrixDashboardConfiguration.class)</code> and redefine
 * the proxyStreamServlet bean.
 * 
 * <p>
 * TODO throw IllegalStateException if the hystrix work-manager is not found.
 * 
 * @author Fahim Farook
 */
@Configuration
@Import(HystrixDashboardConfiguration.class)
@EnableConfigurationProperties({ HystrixWebLogicDashboardProperties.class, HystrixDashboardProperties.class })
public class HystrixWebLogicDashboardConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(HystrixWebLogicDashboardConfiguration.class);

	@Autowired
	private HystrixWebLogicDashboardProperties weblogicProperties;

	@Autowired
	private HystrixDashboardProperties standardProperties;

	@Bean
	public ServletRegistrationBean proxyStreamServlet() {
		logger.info("Registering ProxyStreamServlet with the container.");
		final HystrixDashboardConfiguration.ProxyStreamServlet proxyStreamServlet = 
				new HystrixDashboardConfiguration.ProxyStreamServlet();
		proxyStreamServlet
				.setEnableIgnoreConnectionCloseHeader(this.standardProperties.isEnableIgnoreConnectionCloseHeader());
		final ServletRegistrationBean registration = new ServletRegistrationBean(proxyStreamServlet, "/proxy.stream");
		registration.setInitParameters(this.weblogicProperties.getInitParameters());
		return registration;
	}
}
