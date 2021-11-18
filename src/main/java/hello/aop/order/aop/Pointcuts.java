package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // hello.aop.order 패키지와 하위 모든 패키지
    @Pointcut("execution(* hello.aop.order..*(..))")    // pointcut expression
    public void allOrder() {
    }

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    // 두 개의 pointcut 을 조합한 새로운 pointcut
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}

}
