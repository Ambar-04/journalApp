package net.engineeringdigest.journalApp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    //redisTemplate.opsForValue() – Used to perform get/set operations on simple key-value pairs in Redis.
    //
    //mapper.writeValueAsString(obj) – Converts a Java object into a JSON string for storage.
    //
    //mapper.readValue(json, Class) – Converts a JSON string back into a Java object.

    @Autowired
    private RedisTemplate redisTemplate;
    // RedisTemplate is the main class provided by Spring Data Redis for interacting with Redis.
    // It allows you to read and write data (key-value pairs) to the Redis server.


    public void set(String key, Object obj, Long ttl){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(obj); //Since our RedisConfig is set to store values as strings (using StringRedisSerializer)
            // We need to turn any object into a string before saving it
            // Redis only understands strings (in our setup), so before saving a Java object, we turn it into a JSON string
            redisTemplate.opsForValue().set(key, jsonValue , ttl, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("Exception"+e);
        }
    }

    public <T> T get(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            //Here the "value" associated with the "key" is getting stored, not the "key"
            //The value is returned as an Object (because RedisTemplate is generic).
            //But in our setup, we had to store a JSON string, as in RedisConfig the "value" is also serialized/deserialized as string,
            //Also in above set method we are storing JSON type string(jsonValue) in "value"

            //But,RedisTemplate doesn't automatically know what type you're expecting
            //So, even though the stored value is a JSON string, it returns it as a generic Object.

            ObjectMapper mapper = new ObjectMapper();
//            String string = o.toString();
            return mapper.readValue(o.toString(), entityClass);
            //We call toString() on Redis value as it's returned as an Object, and we need it as a JSON string to deserialize it and send it in type of entityClass
        }catch (Exception e){
            log.error("Exception"+e);
            return null;
        }
    }
}
