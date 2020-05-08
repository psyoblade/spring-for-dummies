package spring.ch07;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch07.config.Ch07AppContext;
import spring.ch07.entities.Calculator;

public class Ch07Application {

    private AnnotationConfigApplicationContext ctx;
    private Calculator normalCalculator;
    private Calculator recursionCalculator;

    public Ch07Application() {
        ctx = new AnnotationConfigApplicationContext(Ch07AppContext.class);
        normalCalculator = ctx.getBean("normalCalculator", Calculator.class);
        recursionCalculator = ctx.getBean("recursionCalculator", Calculator.class);
    }

    public void run() {
        System.out.println(normalCalculator.factorial(10));
        System.out.println(recursionCalculator.factorial(10));
    }

    public static void main(String[] args) {
        Ch07Application ch07 = new Ch07Application();
        ch07.run();
    }
}
