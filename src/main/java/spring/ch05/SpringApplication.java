package spring.ch05;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch05.configs.Ch5Context;
import spring.ch05.services.Calculator;

public class SpringApplication {

    private Calculator calculator;

    public SpringApplication() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Ch5Context.class);
        Calculator calculator = context.getBean(Calculator.class);
    }

    public void run() {
        calculator.sumOf(1, 10);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication();
        application.run();
    }

}
