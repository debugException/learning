package cn.ixuehui.learning.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ixuehui.learning.core.base.Result;
import cn.ixuehui.learning.core.base.ResultData;

@RestController
@RequestMapping("/user")
public class UserController {

	@RequestMapping("/list")
	public Result list() {
		return ResultData.success();
		
	}
}
