package com.hongcj.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Hongcj
 * @description
 * @date 3/21/22 4:58 PM
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.requestMatchers()
//				.antMatchers("/api/**")
//				.and()
//				.authorizeRequests()
//				.antMatchers("/api/**")
//				.authenticated();

		http    // 设置session的创建策略（根据需要创建即可）
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.authorizeRequests()
				.antMatchers("/api/**").authenticated() // auto为前缀的请求需要认证
				.antMatchers("/demo/**").authenticated() // demo为前缀的请求需要认证
				.antMatchers("/anon/**").permitAll() // anon为前缀的请求需要认证
//				.anyRequest().permitAll() // 所有请求都放开
		;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("resourcesId").stateless(true);
	}
}
