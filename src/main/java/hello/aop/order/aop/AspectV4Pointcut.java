package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV4Pointcut {

    @Around("hello.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
        return joinPoint.proceed();
    }

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        try {
            log.info("[트랜잭션 시작] {}", signature);
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", signature);
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", signature);
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", signature);
        }
    }
}
