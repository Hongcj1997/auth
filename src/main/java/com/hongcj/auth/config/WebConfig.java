package com.hongcj.auth.config;

import com.hongcj.auth.service.UserService;
import com.hongcj.auth.service.impl.UserDetailServiceImpl;
import com.hongcj.auth.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Hongcj
 * @description
 * @date 3/21/22 3:42 PM
 */
@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 授权中心管理器
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
//		auth.inMemoryAuthentication()
//				.passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
//				.password(new BCryptPasswordEncoder().encode("123456"))
//				.roles("USER");
	}

	/* 配置资源拦截规则 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// 解决跨域
		http.csrf().disable();
		http.requestMatchers().antMatchers("/oauth/**")
				.and()
				.authorizeRequests()
				.antMatchers("/oauth/**").authenticated();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
