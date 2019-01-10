package cn.ixuehui.learning.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.ixuehui.learning.dao.entity.User;
import cn.ixuehui.learning.dao.mapper.UserMapper;
import cn.ixuehui.learning.service.IUserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired(required=true)
	private UserMapper userMapper;
	
	@Override
	public List<User> getUserList() {
		return userMapper.getUserList();
	}

}
