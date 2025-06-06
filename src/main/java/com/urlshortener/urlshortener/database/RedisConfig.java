package com.urlshortener.urlshortener.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

  @Bean
  public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
    template.setEnableTransactionSupport(true);
    return template;
  }

  @Bean
  public HashOperations<String, String, String> hashOperations(StringRedisTemplate redisTemplate) {
    return redisTemplate.opsForHash();
  }
}