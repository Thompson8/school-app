package eu.thompson8.school.app.config.servlet;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class DispatcherServletConfig {

	private static final String DISPATCHER_SERVLET_NAME = "SchoolAppDispatcherServlet";

	private static final String DISPATCHER_SERVLET_PATH = "/";

	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet servlet = new DispatcherServlet();
		servlet.setThrowExceptionIfNoHandlerFound(true);
		return servlet;
	}

	@Bean
	public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
		DispatcherServletRegistrationBean servletRegistration = new DispatcherServletRegistrationBean(dispatcherServlet,
				DISPATCHER_SERVLET_PATH);
		servletRegistration.setName(DISPATCHER_SERVLET_NAME);
		return servletRegistration;
	}

}