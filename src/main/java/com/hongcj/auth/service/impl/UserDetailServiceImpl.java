package com.hongcj.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongcj.auth.model.User;
import com.hongcj.auth.service.UserService;
import com.hongcj.auth.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Hongcj
 * @description
 * @date 3/21/22 4:40 PM
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//		return new UserDetailsImpl(new User("admin",passwordEncoder.encode("123456")));
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.ge(User::getUsername,username);
		User user = userService.getOne(queryWrapper);
		if(user == null){
			throw new RuntimeException("用户不存在");
		}
		return new UserDetailsImpl(user);
	}

}
