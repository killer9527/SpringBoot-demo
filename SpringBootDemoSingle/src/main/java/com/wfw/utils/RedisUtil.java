package com.wfw.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by killer9527 on 2018/4/3.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private Environment env;

    public <T> T get(String key, Class<T> clz) {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null)
            return null;
        return JSONObject.parseObject(value, clz);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value, long timeout) {
        String json = JSONObject.toJSONString(value);
        redisTemplate.opsForValue().set(key, json, timeout, TimeUnit.MILLISECONDS);
    }

    public void set(String key, Object value){
        int timeout = env.getProperty("spring.redis.timeout", Integer.class);
        String json = JSONObject.toJSONString(value);
        redisTemplate.opsForValue().set(key, json, timeout, TimeUnit.MILLISECONDS);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
