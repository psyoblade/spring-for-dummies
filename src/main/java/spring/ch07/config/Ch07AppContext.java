package spring.ch07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.ch07.aspects.CalculateElapsedTime;
import spring.ch07.entities.Calculator;
import spring.ch07.entities.NormalCalculator;
import spring.ch07.entities.RecursionCalculator;

@Configuration
@EnableAspectJAutoProxy //(proxyTargetClass=true)
public class Ch07AppContext {

    @Bean
    public Calculator normalCalculator() {
        return new NormalCalculator();
    }

    @Bean
    public Calculator recursionCalculator() {
        return new RecursionCalculator();
    }
}
