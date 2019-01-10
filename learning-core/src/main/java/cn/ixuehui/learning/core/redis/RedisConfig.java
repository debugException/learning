package cn.ixuehui.learning.core.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author szq
 * @Package cn.ixuehui.core.redis
 * @Description: Redis配置.
 *  使用@EnableCaching开启声明式缓存支持. 之后就可以使用 @Cacheable/@CachePut/@CacheEvict 注解缓存数据.
 * @date 2018/12/2714:57
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    /**
     * 覆盖默认配置 RedisTemplate，使用 String 类型作为key，设置key/value的序列化规则
     */
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //使用Jackson2JsonRedisSerialize替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = jackson2ObjectMapperBuilder.createXmlMapper(false).build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    	RedisSerializer<String> redisSerializer = new StringRedisSerializer();
    	Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
    	
    	//解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        
    	RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
    			.defaultCacheConfig()
    			.entryTtl(Duration.ofMinutes(3))//设置缓存有效期3分钟
    			.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
    			.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
    			.disableCachingNullValues();
    	
    	return RedisCacheManager
    			.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
    			.cacheDefaults(redisCacheConfiguration)
    			.build();
    	
    }

}
