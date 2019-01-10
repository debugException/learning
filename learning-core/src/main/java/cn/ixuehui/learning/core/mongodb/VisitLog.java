package cn.ixuehui.learning.core.mongodb;

import java.util.Date;

import lombok.Data;

/**
 * <p>
 * 接口访问日志
 * </p>
 *
 * @author szq
 * @since 2019-01-09
 */
@Data
public class VisitLog{
	
    private String xh;

    private String loginToken;

    private Integer userId;

    private String ip;

    private String module;

    private String description;

    private String className;

    private String methodName;

    private String argJson;

    private Date startDate;

    private Date endDate;

    private Integer excedate;

    private String result;

}
