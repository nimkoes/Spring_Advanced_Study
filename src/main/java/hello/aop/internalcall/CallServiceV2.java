package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    /**
     * applicationContext 를 주입 받아 bean 사용 시점에 지연 조회 하여 사용하는 방법
     * applicationContext 자체를 이것 하나만을 위해 주입 받는 것을 좋은 생각은 아님
     * 또 다른 지연 조회 방식으로 ObjectProvider 사용 -> CallServiceV3.class 참고
     */
    private final ApplicationContext applicationContext;

    public CallServiceV2(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void external() {
        log.info("call external");
        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        callServiceV2.internal(); // 외부 메소드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
