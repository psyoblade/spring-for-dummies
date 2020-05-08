package spring.ch07.entities;

public class NormalCalculator implements Calculator{

    @Override
    public long factorial(long num) {
        long res = 1;
        for (long l = num; l > 0; l--)
            res *= l;
        return res;
    }
}
