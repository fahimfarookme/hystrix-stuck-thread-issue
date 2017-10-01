package me.fahimfarook.spring.cloud.hystrix.dashboard;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Fahim Farook
 */
public class HystrixWebLogicDashboardConfigurationTests {
	
	private ConfigurableApplicationContext context;
	
	@Before
	public void before() {
		this.context = new SpringApplicationBuilder(HystrixWebLogicDashboardConfiguration.class)
			.properties("hystrix.dashboard.init-parameters.wl-dispatch-policy=work-manager-hystrix-dashboard")
			.web(false)
			.bannerMode(Mode.OFF)
			.run();
	}
	
	@After
	public void after() {
		if (this.context != null) {
			this.context.close();
		}
	}
	
	@Test
	public void initParametersHystrixStreamServlet() {
		final ServletRegistrationBean registration = this.context.getBean(ServletRegistrationBean.class);
		assertNotNull(registration);		
		final Map<String, String> initParameters = registration.getInitParameters();
		assertNotNull(initParameters);
		assertThat(initParameters.get("wl-dispatch-policy"), is("work-manager-hystrix-dashboard"));
	}
}
