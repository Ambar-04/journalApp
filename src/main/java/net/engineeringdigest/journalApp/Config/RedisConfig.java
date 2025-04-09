package net.engineeringdigest.journalApp.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


//We need this configuration class because Redis uses a different default serializer/deserializer than what our Spring Boot application expects.
//Without this custom configuration, Spring Boot may not be able to correctly read or write data to Redis, especially when using string keys and values.
// For example, if we set a key in Redis manually via WLS(Redis Cli), Spring Boot might not recognize it due to mismatched serialization formats — and vice versa.
// By explicitly setting both the key and value serializers to StringRedisSerializer, we ensure that:
// Redis stores and retrieves data as plain strings.
@Configuration
public class RedisConfig {


    //RedisTemplate is the main class provided by Spring Data Redis for interacting with Redis. It allows you to read and write data (key-value pairs) to the Redis server.
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){ //factory is used to make connection with redis server
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory); //Sets up the Redis connection so RedisTemplate, can talk to the Redis server.

        // Redis will store and return keys and values as plain strings
        // You're storing simple types like strings or JSON strings.
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
