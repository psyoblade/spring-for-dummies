package spring.ch06;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch06.config.Ch06AppContext;
import spring.ch06.entities.ExternalLibrary;
import spring.ch06.entities.Greeting;

import static org.junit.Assert.*;

public class ApplicationTest {

    private AnnotationConfigApplicationContext ctx;
    private Greeting greeting;
    private ExternalLibrary externalLibrary;

    @Before
    public void initialize() {
        ctx = new AnnotationConfigApplicationContext(Ch06AppContext.class);
        greeting = ctx.getBean(Greeting.class);
        externalLibrary = ctx.getBean(ExternalLibrary.class);
    }

    @After
    public void destroy() {
        ctx.close();
    }

    @Test
    public void 인사하기() {
        assertEquals(0, greeting.getTimes());
    }

    @Test
    public void 외부라이브러리_초기화_및_종료() {
        assertEquals(0, externalLibrary.getId());
    }

}