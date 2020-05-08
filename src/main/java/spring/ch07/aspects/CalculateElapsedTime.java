package spring.ch07.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class CalculateElapsedTime {
    private Logger logger = LoggerFactory.getLogger(CalculateElapsedTime.class);

    @Pointcut("execution(public * spring.ch07..*(..))")
    private void publicTarget() {}

    @Around("publicTarget()")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.nanoTime();
            Signature signature = joinPoint.getSignature();
            logger.info("{}.{}({}) 실행 시간 : {} ns\n",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    signature.getName(),
                    Arrays.toString(joinPoint.getArgs()),
                    (finish - start)
            );
        }
    }
}
