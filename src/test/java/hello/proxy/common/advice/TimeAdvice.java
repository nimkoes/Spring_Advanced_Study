package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeAdvice 실행");
        long startTime = System.currentTimeMillis();

        /**
         * 앞선 예제의 target 을 실행하는 것을 대체
         * target 을 찾아서 알아서 실행 해준다.
         */
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeAdvice 종료 resultTime={}ms", resultTime);

        return result;
    }
}
