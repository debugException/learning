package cn.ixuehui.learning.core.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 线程池
 * @author szq
 * Date: 2019年1月7日 上午10:23:02
 */

public class ThreadPoolUtil {

	/**
	 * 线程池工厂
	 */
	private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("xheducation_api").build();
	
	/**
	 * corePoolSize: 线程池维护线程的最少线程数,也是核心线程数,包括空闲线程
	 * maximumPoolSize: 线程池维护线程的最大线程数
	 * keepAliveTime: 线程池维护线程所允许的空闲时间
	 * unit: 程池维护线程所允许的空闲时间的单位
	 * workQueue: 线程池所使用的缓冲队列
	 * handler: 线程池对拒绝任务的处理策略
	 * 当一个任务通过execute(Runnable)方法欲添加到线程池时：
		1、 如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
		2、 如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
		3、 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maximumPoolSize，建新的线程来处理被添加的任务。
		4、 如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maximumPoolSize，那么通过 handler所指定的策略来处理此任务。也就是：处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、最大线程 maximumPoolSize，如果三者都满了，使用handler处理被拒绝的任务。
		5、 当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
	 */
	public static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			2, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),  namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
}
