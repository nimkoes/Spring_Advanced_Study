package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    /**
     * 다른 pointcut annotation 은 JoinPoint 클래스 타입 변수를 첫 번째 매개변수로 필요할 경우 사용할 수 있다. (생략 가능)
     * 하지만 @Around 는 ProceedingJoinPoint 클래스 타입 변수를 전달 받아
     * proceed() 로 target 에 해당하는 method 를 호출해줘야 한다.
     */
    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        Signature signature = joinPoint.getSignature();

        try {
            // @Before
            log.info("[트랜잭션 시작] {}", signature);
            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}", signature);
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", signature);
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}", signature);
        }
    }

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[doBefore] {}", joinPoint.getSignature());
    }

    // method 에 JoinPoint 를 굳이 넘겨주지 않아도 적용 됨
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore2() {
        log.info("[doBefore2] hello");
    }


    /**
     * return 되는 값의 이름을 명시해 줘야 함
     * annotation 의 returning 에 적어준 반환 값의 이름을 명시하면
     * (현재 기준) doReturn method 에 Object 타입으로 같은 이름을 사용해서
     * advice 를 적용 할 실체 target method 의 반환 값을 받아올 수 있다.
     * <p>
     * target method 로 정의할 수 있는 것은
     * proxy 를 사용하는 Spring 의 AOP 는 method 레벨에만 적용 가능하기 때문이다.
     *
     * @Around 와는 달리 target method 의 실행 결과를 변조하는 등의 작업은 불가능
     */
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "e")
    public void doThrowing(JoinPoint joinPoint, Exception e) {
        log.info("[exception] {} message={}", joinPoint.getSignature(), e);
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }

}
