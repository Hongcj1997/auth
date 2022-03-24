package com.hongcj.auth.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author Hongcj
 * @description
 * @date 3/21/22 4:59 PM
 */
@Data
@TableName("sys_user")
public class User {
	@TableField(value = "username")
	String username;

	@TableField(value = "password")
	String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
