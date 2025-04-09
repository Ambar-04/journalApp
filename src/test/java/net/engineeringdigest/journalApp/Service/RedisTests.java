package net.engineeringdigest.journalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testSendMail(){
//        redisTemplate.opsForValue().set("email","ambar@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        Object age = redisTemplate.opsForValue().get("age"); // set in wls
        int a = 1;
    }
}
