package org.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter
{
	@Bean
	public AuthenticationInterceptor authenticationInterceptor()
	{
		return new AuthenticationInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(authenticationInterceptor());
	}
}
