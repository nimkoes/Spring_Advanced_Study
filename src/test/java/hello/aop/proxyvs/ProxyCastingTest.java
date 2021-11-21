package hello.aop.proxyvs;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시 사용

        // 프록시를 인터페이스로 캐스팅 -> 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실채, ClassCastException 예외 발생
        // java.lang.ClassCastException: class com.sun.proxy.$Proxy9 cannot be cast to class hello.aop.member.MemberServiceImpl (com.sun.proxy.$Proxy9 and hello.aop.member.MemberServiceImpl are in unnamed module of loader 'app')
        // JDK 동적 프록시는 인터페이스 기반으로 프록시를 생성하기 때문에 구체 클래스에 대해 알지 못함
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // CGLib 프록시 사용

        // 프록시를 인터페이스로 캐스팅 -> 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        log.info("proxy class={}", memberServiceProxy.getClass());

        // CGLib 프록시를 구현 클래스로 캐스팅 -> 성공
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }

}
