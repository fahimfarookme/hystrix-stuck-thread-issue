package me.fahimfarook.spring.cloud.hystrix.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Fahim Farook
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HystrixStreamConfigurationTests.Application.class,
				webEnvironment = WebEnvironment.RANDOM_PORT,
				value = {
					"hystrix.stream.endpoint.enabled=false",
				    "hystrix.stream.init-parameters.wl-dispatch-policy=work-manager-hystrix-stream"
				})
public class HystrixStreamConfigurationTests {

	@Autowired
	private ConfigurableApplicationContext context;

	@SpringBootApplication
	protected static class Application {
		public static void main(String[] args) {
			new SpringApplicationBuilder().run();
		}
	}

	@Test
	public void initParametersHystrixStreamServlet() {
		final ServletRegistrationBean registration = (ServletRegistrationBean) 
				this.context.getBean("hystrixStreamServlet");
		assertNotNull(registration);
		final Map<String, String> initParameters = registration.getInitParameters();
		assertNotNull(initParameters);
		assertThat(initParameters.get("wl-dispatch-policy"), is("work-manager-hystrix-stream"));
	}
}
