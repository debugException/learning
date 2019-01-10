package cn.ixuehui.learning.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.ixuehui.learning.core.annotation.NoAuth;
import cn.ixuehui.learning.core.annotation.NoAuth.AuthType;
import cn.ixuehui.learning.core.annotation.OperationLog;
import cn.ixuehui.learning.core.base.BaseController;
import cn.ixuehui.learning.core.base.Record;
import cn.ixuehui.learning.core.base.Result;
import cn.ixuehui.learning.core.base.ResultData;
import cn.ixuehui.learning.core.constants.Constants;
import cn.ixuehui.learning.core.redis.RedisOperator;
import cn.ixuehui.learning.core.util.CommonUtil;
import cn.ixuehui.learning.dao.entity.User;
import cn.ixuehui.learning.service.IUserService;

@RestController
@RequestMapping("/user")
@CacheConfig(cacheNames="userCache")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService; 
	
	@Autowired
	private RedisOperator redisOperator;
	
	@RequestMapping("/list")
	@Cacheable(value="userList")
	@OperationLog(key="getUserList()", description="查询用户列表")
	public Result getUserList() {
		Page<User> page = new Page<User>(1, 10);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		IPage<User> result = userService.page(page, queryWrapper);
		
		
		return ResultData.success(result);
	}
	
	@RequestMapping("/test")
	@NoAuth(authType=AuthType.visitor)
	@OperationLog(key="test()", description="测试")
	public Result test(int id) {
		return ResultData.success(userService.getById(id));
	}
	
	@RequestMapping("/login")
	@NoAuth
	@OperationLog(key="login()", description="登录操作")
	public Result login(String username, String password) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("username", username);
		queryWrapper.eq("password", DigestUtils.md5Hex(password));
		User user = userService.getOne(queryWrapper);
		String loginToken = CommonUtil.loginToken(user.getId());
		String redisKey = Constants.REDIS_KEY + user.getId();
		redisOperator.set(redisKey, loginToken);
		Record<String, Object> record = new Record<>();
		record.put("id", user.getId());
		record.put("username", user.getUsername());
		record.put("logintoken", loginToken);
		return ResultData.success(record);
	}

}
