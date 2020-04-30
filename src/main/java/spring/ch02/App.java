package spring.ch02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 스프링 프레임워크의 기초를 이해하기 위한 챕터 ApplicationContext 내부에서 실제로 일어날 법한 일을 코드로 작성합니다
 */
public class App {
    private Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(AppContext.class);
        Greeter greet = app.getBean(Greeter.class);
        greet.setFormat("%s, 안녕하세요");
        String message = greet.greet("박수혁");
        System.out.println(message);
    }
}

