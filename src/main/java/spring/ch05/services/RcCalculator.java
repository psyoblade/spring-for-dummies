package spring.ch05.services;

import org.springframework.stereotype.Service;
import spring.ch05.annotations.CustomBean;

@Service
public class RcCalculator {

    public int multiplyOf(int first, int second) {
        return first * second;
    }
}
