package spring.ch07.entities;

public class RecursionCalculator implements Calculator {

    @Override
    public long factorial(long num) {
        return num == 1 ? 1 : num * factorial(num - 1);
    }
}
