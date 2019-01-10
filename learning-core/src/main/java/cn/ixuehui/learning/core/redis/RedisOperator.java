package cn.ixuehui.learning.core.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RedisOperator {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Value("${redis.root}")
	private String category; //redis缓存根操作路径
	
	/**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE=60 * 3;
    
    /**
     * 获取Key的全路径
     * @param key
     * @return
     */
    private String getFullKey(String key) {
    	return this.category + ":" + key;
    }
   
    /**
     * 指定缓存失效时间
     * @param key 键不能为null
     * @param time 时间（秒）
     * @return
     */
    public boolean expire(String key, long time) {
    	try {
			if (time>0) {
				redisTemplate.expire(getFullKey(key), time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    } 
    
    /**
     * 根据key获取过期时间
     * @param key  时间(秒) 返回0代表为永久有效
     * @return
     */
    public long getExpire(String key) {
    	return redisTemplate.getExpire(getFullKey(key), TimeUnit.SECONDS);
    }
    
    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
    	try {
			return redisTemplate.hasKey(getFullKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * 删除缓存 
     * @param key 可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
	public void del(String... key) {
    	if (key!=null && key.length>0) {
			if (key.length == 1) {
				redisTemplate.delete(getFullKey(key[0]));
			}else {
				List<String> list = CollectionUtils.arrayToList(key);
				list.stream().forEach(t->getFullKey((String) t));
				redisTemplate.delete(list);
			}
		}
    }
    
    //==============String==============
    /**
     * 普通缓存获取
     * @param key
     * @return
     */
    public Object get(String key) {
    	return key == null ? null : redisTemplate.opsForValue().get(getFullKey(key));
    }
    
    /**
     * 普通缓存放入，不设置有效期
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
    	try {
			redisTemplate.opsForValue().set(getFullKey(key), value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * 普通缓存放入，默认有效期三分钟
     * @param key
     * @param value
     * @param time 时间（秒）
     * @return
     */
    public boolean set(String key, Object value, long time) {
    	try {
			if (time>0) {
				redisTemplate.opsForValue().set(getFullKey(key), value, time, TimeUnit.SECONDS);
			}else {
				set(key, value, DEFAULT_EXPIRE);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * 递增
     * @param key
     * @param delta 要增加几（大于0）
     * @return
     */
    public long incr(String key, long delta) {
    	if (delta<0) {
			throw new RuntimeException("递增因子必须大于0");
		}
    	return redisTemplate.opsForValue().increment(getFullKey(key), delta);
    }
    
    /**
     * 递减
     * @param key
     * @param delta 要增加几（大于0）
     * @return
     */
    public long decr(String key, long delta) {
    	if (delta<0) {
			throw new RuntimeException("递减因子必须大于0");
		}
    	return redisTemplate.opsForValue().increment(getFullKey(key), -delta);
    }
    
    //===============Map===================
    /**
     * HashGet
     * @param key 键不能为null
     * @param item 项不能为null
     * @return
     */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(getFullKey(key), item);
	}
	
	/**
	 * 获取hashKey对应的所有键值
	 * @param key
	 * @return
	 */
	public Map<Object, Object> hmget(String key){
		return redisTemplate.opsForHash().entries(getFullKey(key));
	}
	
	/**
	 * HashSet 
	 * @param key
	 * @param map 对应多个键值
	 * @return
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(getFullKey(key), map);
			expire(key, DEFAULT_EXPIRE);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * HashSet 
	 * @param key
	 * @param map 对应多个键值
	 * @param time 时间（秒）
	 * @return
	 */
	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(getFullKey(key), map);
			if (time>0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 向一张hash表中放入数据，如果不存在则创建
	 * @param key
	 * @param item
	 * @param value
	 * @return
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(getFullKey(key), item, value);
			expire(key, DEFAULT_EXPIRE);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 向一张hash表中放入数据，如果不存在则创建
	 * @param key
	 * @param item
	 * @param value
	 * @param  time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间 
	 * @return
	 */
	public boolean hset(String key, String item, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(getFullKey(key), item, value);
			if (time>0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除hash表中的值
	 * @param key
	 * @param item
	 */
	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(getFullKey(key), item);
	}
	
	/**
	 * 判断hash表中是否有该项的值
	 * @param key
	 * @param item
	 * @return
	 */
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(getFullKey(key), item);
	}
	
	/**
	 * hash递增 如果不存在就会创建一个，并把新增后的值返回
	 * @param key
	 * @param item
	 * @param delta
	 * @return
	 */
	public double hincr(String key, String item, double delta) {
		return redisTemplate.opsForHash().increment(getFullKey(key), item, delta);
	}
	
	/**
	 * hash递减
	 * @param key
	 * @param item
	 * @param delta
	 * @return
	 */
	public double hdecr(String key, String item, double delta) {
		return redisTemplate.opsForHash().increment(getFullKey(key), item, -delta);
	}
	
	//===========set==============
	/**
	 * 根据key获取Set中的所有值
	 * @param key
	 * @return
	 */
	public Set<Object> sGet(String key){
		try {
			return redisTemplate.opsForSet().members(getFullKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据value从一个set中查询是否存在
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(getFullKey(key), value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将数据放入set缓存
	 * @param key
	 * @param values
	 * @return
	 */
	public long sSet(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(getFullKey(key), values);
			expire(key, DEFAULT_EXPIRE);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 将数据放入set缓存
	 * @param key
	 * @param values
	 * @param time 
	 * @return
	 */
	public long sSet(String key, Long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(getFullKey(key), values);
			if (time>0) {
				expire(key, time);
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取set缓存的长度
	 * @param key
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(getFullKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 从set移除值为value的
	 * @param key
	 * @param values
	 * @return
	 */
	public long setRemove(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().remove(getFullKey(key), values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	//============list=============
	/**
	 * 获取list缓存的内容
	 * @param key
	 * @param start 开始
	 * @param end 结束 0到-1表示所有值
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end){
		try {
			return redisTemplate.opsForList().range(getFullKey(key), start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取list缓存的长度
	 * @param key
	 * @return
	 */
	public long lGetListSize(String key){
		try {
			return redisTemplate.opsForList().size(getFullKey(key));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 通过索引获取list中的值
	 * @param key
	 * @param index index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lGetIndex(String key, long index){
		try {
			return redisTemplate.opsForList().index(getFullKey(key), index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将list放入到缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lSet(String key, Object value){
		try {
			redisTemplate.opsForList().rightPush(getFullKey(key), value);
			expire(key, DEFAULT_EXPIRE);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将list放入到缓存
	 * @param key
	 * @param value
	 * @param time 时间（秒）
	 * @return
	 */
	public boolean lSet(String key, Object value, long time){
		try {
			redisTemplate.opsForList().rightPush(getFullKey(key), value);
			if (time>0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将list放入到缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean lSet(String key, List<Object> value){
		try {
			redisTemplate.opsForList().rightPushAll(getFullKey(key), value);
			expire(key, DEFAULT_EXPIRE);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将list放入到缓存
	 * @param key
	 * @param value
	 * @param time 时间（秒）
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time){
		try {
			redisTemplate.opsForList().rightPushAll(getFullKey(key), value);
			if (time>0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 根据索引修改list中的某条数据
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index, Object value){
		try {
			redisTemplate.opsForList().set(getFullKey(key), index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 从list中移除N个值为value的元素
	 * @param key
	 * @param count 移除多少个
	 * @param value 值
	 * @return
	 */
	public long lRemove(String key, long count, Object value){
		try {
			return redisTemplate.opsForList().remove(getFullKey(key), count, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	
	
}
