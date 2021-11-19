package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);    // -> execution matching 하기 위한 정보 출력
    }

    @Test
    @DisplayName("최대한 명시")
    /**
     * execution([접근_제한자] [반환타입] [메소드이름]([매개변수)])
     */
    void exactMatch() {
        /**
         * 접근_제한자 : public
         * 반환타입 : String
         * 선언타입 : hello.aop.member.MemberServiceImpl
         * 메소드이름 : hello
         * 매개변수 : (String)
         * 예외 : 없음(생략)
         */
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        /**
         * pointcut.matches(메소드, 대상_클래스) -> pointcut 에 매칭 여부 T/F 반환
         */
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("최대한 생략")
    void allMatch() {
        /**
         * 접근_제한자 : 생략
         * 반환타입 : *
         * 선언타입 : 생략
         * 메소드이름 : *
         * 매개변수 : (..)
         * 예외 : 없음(생략)
         */
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch() {
        /**
         * 접근_제한자 : 생략
         * 반환타입 : *
         * 선언타입 : 생략
         * 메소드이름 : hello
         * 매개변수 : (..)
         * 예외 : 없음(생략)
         */
        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchPatternAfter() {
        /**
         * 접근_제한자 : 생략
         * 반환타입 : *
         * 선언타입 : 생략
         * 메소드이름 : he*
         * 매개변수 : (..)
         * 예외 : 없음(생략)
         */
        pointcut.setExpression("execution(* he*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchPatternPre() {
        /**
         * 접근_제한자 : 생략
         * 반환타입 : *
         * 선언타입 : 생략
         * 메소드이름 : *llo
         * 매개변수 : (..)
         * 예외 : 없음(생략)
         */
        pointcut.setExpression("execution(* *llo(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchPatternBoth() {
        /**
         * 접근_제한자 : 생략
         * 반환타입 : *
         * 선언타입 : 생략
         * 메소드이름 : *l*
         * 매개변수 : (..)
         * 예외 : 없음(생략)
         */
        pointcut.setExpression("execution(* *l*(..))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch() {
        pointcut.setExpression("execution(public String hello.aop.member.*.*(String))");
        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * hello.aop.member.*(1).*(2)
     * (1) : 타입
     * (2) : 메소드 이름
     *
     * 패키지에서
     * .  : 정확하게 해당 위치릐 패키지
     * .. : 해당 패키지와 그 하위 패키지도 포함
     */
}
