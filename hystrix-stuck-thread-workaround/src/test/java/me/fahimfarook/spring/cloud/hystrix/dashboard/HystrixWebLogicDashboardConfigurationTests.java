/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * Copied some from https://github.com/fahimfarookme/spring-cloud-netflix/commit/94efa6f4dcc7d40150afe21851085998ecb76df5
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
