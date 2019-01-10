package cn.ixuehui.learning.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.ixuehui.learning.dao.entity.User;


public interface IUserService extends IService<User> {

	List<User> getUserList();
}
