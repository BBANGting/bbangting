package com.khu.bbangting.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // {key, value]값 저장
    @Transactional
    public void setValues(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 만료시간 설정 -> 자동 삭제
    @Transactional
    public void setValuesWithTimeout(String key, String value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    // key 값을 이용해 value 값 추출
    public String getValues(String key){
        return redisTemplate.opsForValue().get(key);
    }

    // key 값을 이용해 데이터 추출
    @Transactional
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

}
