package com.example.olive_Cinema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // RedisStandaloneConfiguration에 호스트와 포트를 명시적으로 설정
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("clustercfg.hjh-redis.fpxtkj.apn2.cache.amazonaws.com");
        config.setPort(6379);
        config.setPassword(""); // 비밀번호가 없으면 빈 문자열, 필요한 경우 설정

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        // 직렬화 설정 (문자열 기반)
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
