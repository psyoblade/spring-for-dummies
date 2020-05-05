package spring.ch05.services;

import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class Calculator {

    public int sumOf(int from, int to) {
        return IntStream.range(from, to + 1).reduce(0, Integer::sum);
    }
}
