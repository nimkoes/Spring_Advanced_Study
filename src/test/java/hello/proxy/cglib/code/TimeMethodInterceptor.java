package hello.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    // proxy 가 호출 할 대상
    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("cglib >> TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        /**
         * 실제 대상 동적 호출
         * method 를 사용해도 되지만, CGLIB 는 성능상 MethodProxy proxy 를 사용하는 것을 권장
         */
        Object result = methodProxy.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("cglib >> TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
