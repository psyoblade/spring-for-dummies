package spring.ch07;

import org.junit.Test;
import spring.ch07.entities.Calculator;
import spring.ch07.entities.NormalCalculator;
import spring.ch07.entities.RecursionCalculator;

import static org.junit.Assert.*;

public class Ch07ApplicationTest {

    @Test
    public void 단순반복_팩토리얼_계산() {
        Calculator calculator = new NormalCalculator();
        assertEquals(24, calculator.factorial(4));
    }

    @Test
    public void 재귀함수_팩토리얼_계산() {
        Calculator calculator = new RecursionCalculator();
        assertEquals(24, calculator.factorial(4));
    }

}