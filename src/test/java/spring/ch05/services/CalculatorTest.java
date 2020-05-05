package spring.ch05.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch05.configs.Ch5Context;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private static AnnotationConfigApplicationContext context;
    private Calculator calculator;
    private RcCalculator rcCalculator;

    @BeforeClass
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(Ch5Context.class);
    }

    @Before
    public void init() {
        calculator = context.getBean("calculator", Calculator.class);
        rcCalculator = context.getBean(RcCalculator.class);
    }

    @Test
    public void 중복되는_빈이름() {
        int actual = calculator.sumOf(1, 10);
        System.out.println(actual);
    }

    @Test
    public void 하나부터_열까지_더하기() {
        int actual = calculator.sumOf(1, 10);
        assertEquals(55,  actual);
    }

    @Test
    public void 두수의_곱셈() {
        int actual = rcCalculator.multiplyOf(1, 10);
        assertEquals(10, actual);
    }

}
