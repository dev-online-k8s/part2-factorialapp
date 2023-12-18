package com.example.factorialapp;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FactorialService {

    private StringRedisTemplate redisTemplate;

    public FactorialService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public BigDecimal calculate(int n) {
        if (n<=1) {
            long elapsedTime;
            long startTime = System.currentTimeMillis();
            do {
                elapsedTime = System.currentTimeMillis() - startTime;
            } while (elapsedTime < 500);

            return BigDecimal.ONE;
        }

        return new BigDecimal(n).multiply(calculate(n-1));
    }

    @Scheduled(fixedDelay = 1000L)
    public void calculateTask() {
        if (redisTemplate.hasKey("factorial:task-queue")) {
            String task = redisTemplate.opsForSet().pop("factorial:task-queue");
            if (task != null) {
                BigDecimal result = calculate(Integer.parseInt(task));
                redisTemplate.opsForHash()
                        .put(
                                "factorial:result-set",
                                task,
                                result.toPlainString()
                        );
            }

        }
    }
}
