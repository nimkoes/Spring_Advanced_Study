package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    /**
     * Aspect 적용 순서는 class 단위로만 가능
     *
     * @Order 의 숫자가 낮을수록 먼저 적용
     * 별도의 public class 로 파일 단위 구분을 해도 되지만
     * inner class 를 정의해서 순서를 보장받는 방법을 사용
     */
    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature()); // join point 시그니처
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {
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

}
