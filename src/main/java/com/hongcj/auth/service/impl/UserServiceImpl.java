package com.hongcj.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hongcj.auth.mapper.UserMapper;
import com.hongcj.auth.model.User;
import com.hongcj.auth.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Hongcj
 * @description
 * @date 3/23/22 5:30 PM
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
