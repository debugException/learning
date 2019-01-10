package cn.ixuehui.learning.core.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.ixuehui.learning.core.mongodb.MongodbOperator;
import cn.ixuehui.learning.core.mongodb.VisitLog;

/**
 * 异步任务列表
 * @author szq
 * Date: 2019年1月10日 下午1:48:18
 */
@Component
public class AsyncTask {

	/**保存日志到MongoDB数据库中*/
	@Async
	public void saveLog(VisitLog visitLog) {
		MongodbOperator.save(visitLog);

	}
}
