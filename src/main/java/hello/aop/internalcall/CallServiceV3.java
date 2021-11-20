package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV3 {

    /**
     * ObjectProvider 는 객체를 spring container 에서 조회하는 것을
     * bean 생성 시점이 아닌 실제 사용 시점으로 지연할 수 있다.
     * <p>
     * ObjectProvider 는 이 기능에 특화된 것이므로
     * CallServiceV2 에서 applicationContext 를 주입받아 직접 객체를 꺼내는 것보다 좋은 방법이다.
     */
    private final ObjectProvider<CallServiceV3> callServiceProvider;

    public CallServiceV3(ObjectProvider<CallServiceV3> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
        CallServiceV3 callServiceV3 = callServiceProvider.getObject();
        callServiceV3.internal(); // 외부 메소드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
