package spring.ch02;

class Greeter {
    private String message;
    private String format;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String greet(String name) {
        return String.format(this.format, name);
    }
}

