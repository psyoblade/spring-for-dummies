package spring.ch06.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Greeting implements InitializingBean, DisposableBean {
    private Logger logger = LoggerFactory.getLogger(Greeting.class);
    private String sayHello;
    private int times;

    public String getSayHello() {
        return sayHello;
    }

    public void setSayHello(String sayHello) {
        this.sayHello = sayHello;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public void destroy() throws Exception {
        logger.info("destroy bean " + this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("initializing bean " + this);
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "sayHello='" + sayHello + '\'' +
                ", times=" + times +
                '}';
    }
}
