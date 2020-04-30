package spring.ch02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {

    @Bean
    public Greeter greeter() {
        return new Greeter();
    }

    @Bean
    public SayHello helloOnce() {
        return new SayHello();
    }

    @Bean
    public SayHello helloTwice() {
        return new SayHello();
    }
}
