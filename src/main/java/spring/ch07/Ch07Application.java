package spring.ch07;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch07.config.Ch07AppContext;
import spring.ch07.entities.NormalCalculator;
import spring.ch07.entities.RecursionCalculator;

public class Ch07Application {
    private Logger logger = LoggerFactory.getLogger(Ch07Application.class);

    private AnnotationConfigApplicationContext ctx;
    private NormalCalculator normalCalculator;
    private RecursionCalculator recursionCalculator;

    public Ch07Application() {
        ctx = new AnnotationConfigApplicationContext(Ch07AppContext.class);
        normalCalculator = ctx.getBean(NormalCalculator.class);
        recursionCalculator = ctx.getBean(RecursionCalculator.class);
    }

    public void run() {
        long num = 10;
        long normal = normalCalculator.factorial(num);
        long recursion = recursionCalculator.factorial(num);
        logger.info("normal:{}, recursion:{}", normal, recursion);
        assert(normal == recursion);
    }

    public static void main(String[] args) {
        Ch07Application ch07 = new Ch07Application();
        ch07.run();
    }
}
