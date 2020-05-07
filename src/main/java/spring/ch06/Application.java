package spring.ch06;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch06.config.Ch06AppContext;
import spring.ch06.entities.Greeting;

public class Application {

    private AnnotationConfigApplicationContext ctx;
    private Greeting greeting;

    public Application() {
        ctx = new AnnotationConfigApplicationContext(Ch06AppContext.class);
        greeting = ctx.getBean(Greeting.class);
    }

    public void run() {
        System.out.println(greeting.getSayHello());
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
