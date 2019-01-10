package cn.ixuehui.learning.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.ixuehui.learning.dao.entity.User;
import cn.ixuehui.learning.dao.mapper.UserMapper;
import cn.ixuehui.learning.security.entity.JwtUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 根据用户名查询用户基本信息
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		queryWrapper.eq("status", 1);
		User user = userMapper.selectOne(queryWrapper);
		return user == null ? null : new JwtUser(user);
	}

}
