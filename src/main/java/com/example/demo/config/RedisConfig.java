package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    /* @Value("${spring.redis.username}")
    private String username;
    @Value("${spring.redis.password}")
    private String password; */
    
    // Set connection
	@Bean
	JedisConnectionFactory jedisConnectionFactory(){
     /* RedisStandaloneConfiguration class used for setting up RedisConnection via 
        RedisConnectionFactory using connecting to a single node Redis installation */
        RedisStandaloneConfiguration configuration= new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
     /* configuration.setUsername(username);
        configuration.setPassword(password); */
        JedisConnectionFactory jedisConnectionFactory= new JedisConnectionFactory(configuration);
		return jedisConnectionFactory;
	}

 /* This is the default configuration and it will use java Serializer 
    (You model class must implement Serializable) */

	/*@Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String,Object> redisTemplate= new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    } */

/* This is configuration for Redis to store values as JSON. We use Jackson2JsonRedisSerializer
    (Model class don't need to implement Serializable , You can remove it ) */
   @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());

        redisTemplate.setKeySerializer(stringRedisSerializer); // The serialization type of the key

        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
       
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer); // The serialization type of value
        redisTemplate.setHashKeySerializer(stringRedisSerializer);   // the serialization type of hash key
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
