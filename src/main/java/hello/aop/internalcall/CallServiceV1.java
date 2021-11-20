package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /**
     * 생성자 주입을 받을 경우 순환 참조 (circular reference) 문제 발생
     * 자기 자신을 주입 받기 위해 생성자 주입을 사용하면
     * 생성 하지도 않았는데 생성 된 객체를 사용할 수 없음
     *
     * 이에 대한 대한으로 setter 주입 사용
     *
     * 이 때 주입받는 객체는 proxy 객체
     */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter={}", callServiceV1.getClass());  // proxy 객체 주입
        // callServiceV1 setter=class hello.aop.internalcall.CallServiceV1$$EnhancerBySpringCGLIB$$25536e0e
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메소드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
