package me.fahimfarook.spring.cloud.hystrix.dashboard;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Import;

/**
 * Solution for <a href=
 * 'https://github.com/spring-cloud/spring-cloud-netflix/issues/2301'>this
 * issue.</a>
 * 
 * <p>
 * WebLogic users facing stuck-thread issue due to ProxyStremServlet long
 * polling can use this enable annotation instead of
 * {@link EnableHystrixDashboard}.
 * 
 * <p>
 * HystrixWebLogicDashboardConfiguration.class can be configured as a
 * EnableAutoConfiguration in spring.factories. When tested Spring did not
 * redefine beans when @EnableAutoConfiguration is seen. However doing so will
 * enables hystrix-dashboard for the application by default.
 * 
 * @author Fahim Farook
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HystrixWebLogicDashboardConfiguration.class)
public @interface EnableWebLogicHystrixDashboard {

}
