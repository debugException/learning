package cn.ixuehui.learning.dao.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.ixuehui.learning.dao.entity.User;


@Mapper
public interface UserMapper extends BaseMapper<User> {

	
	List<User> getUserList();
}
