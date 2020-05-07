package spring.ch06.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalLibrary {

    private Logger logger = LoggerFactory.getLogger(ExternalLibrary.class);
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void initializeExternalBean() {
        logger.info("initializing external bean");
    }

    public void destroyExternalBean() {
        logger.info("destroy external bean");
    }

    @Override
    public String toString() {
        return "ExternalLibrary{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
