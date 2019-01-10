package cn.ixuehui.learning.dao.model;

import cn.ixuehui.learning.core.base.Record;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO extends Record<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String username;
	
	private String password;
	
}
