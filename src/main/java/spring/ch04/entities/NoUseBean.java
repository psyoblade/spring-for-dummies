package spring.ch04.entities;

public class NoUseBean {

    private String name;
    private String cause;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "NoUseBean{" +
                "name='" + name + '\'' +
                ", cause='" + cause + '\'' +
                '}';
    }
}
