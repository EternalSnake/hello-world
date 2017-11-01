package com.yunupay.config;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * 
 * @Package com.yunupay.config 	
 * @ClassName: ReidsConfiguration 
 * @Description: redis配置
 * @author qijiaxv
 * @time   2017年10月30日 下午1:36:14
 * @version V
 */
@Configuration
@EnableCaching
public class ReidsConfiguration {
	
	@Value("${spring.redis.host}")
	private String hostName;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.database}")
	private int databaseIndex;
	
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdl;
	
	@Value("${spring.redis.pool.min-idle}")
	private int minIdl;
	
	@Value("${spring.redis.pool.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${spring.redis.pool.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${spring.redis.pool.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${spring.redis.pool.numTestsPerEvictionRun}")
	private int numTestsPerEvictionRun;
	
	@Value("${spring.redis.pool.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	
	@Value("${spring.redis.pool.max-wait}")
	private int maxWaitMillis;
	
	@Value("${spring.redis.pool.max-total}")
	private int maxTotal;
	
	@Value("${spring.redis.cache.defaultExpireTime}")
	private int defaultExpireTime;
	
	@Autowired(required = true)
	private JedisConnectionFactory jedisConnectionFactory;
	

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		//jedis连接池配置
		JedisPoolConfig poolConfig=new JedisPoolConfig();
	    poolConfig.setMaxIdle(maxIdl);
	    poolConfig.setMinIdle(minIdl);
	    poolConfig.setTestOnBorrow(testOnBorrow);
	    poolConfig.setTestOnReturn(testOnReturn);
	    poolConfig.setTestWhileIdle(testWhileIdle);
	    poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
	    poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	    poolConfig.setMaxWaitMillis(maxWaitMillis);
	    poolConfig.setMaxTotal(maxTotal);
	    //jedis连接工厂配置
	    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
		jedisConnectionFactory.setHostName(hostName);
		jedisConnectionFactory.setPort(port);
		jedisConnectionFactory.setDatabase(databaseIndex);
		jedisConnectionFactory.setTimeout(timeout);
		return jedisConnectionFactory;
	}
	
	

	@Bean
	public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate){
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		//设置key-value超时时间(秒)
		redisCacheManager.setDefaultExpiration(defaultExpireTime);
		return redisCacheManager;
	}
	
	
	@Bean
	@ConditionalOnBean(name = "jedisConnectionFactory")
    public RedisTemplate<String, Object> redisTemplateObject() throws Exception {
        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
        redisTemplateObject.setConnectionFactory(jedisConnectionFactory);
        setSerializer(redisTemplateObject);
        redisTemplateObject.afterPropertiesSet();
        return redisTemplateObject;
    }
	

	private void setSerializer(RedisTemplate<String, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

    }
	
	
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
			}
		};
    }
	

	
}
