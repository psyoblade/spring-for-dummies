package spring.ch07.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class CacheResult {
    private Logger logger = LoggerFactory.getLogger(CacheResult.class);

    private Map<Long, Object> cache = new HashMap<>();

    @Pointcut("execution(public * spring.ch07.entities..*(..))")
    public void publicTarget() {}

    @Around("CalculateElapsedTime.publicTarget()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Long num = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(num)) {
            logger.info("캐시에서 결과를 반환했습니다. {}:{}", num, cache.get(num));
            return cache.get(num);
        }
        Object result = joinPoint.proceed();
        cache.put(num, result);
        logger.info("캐시에 결과를 추가 했습니다. {}:{}", num, (Long)result);
        return result;
    }
}
