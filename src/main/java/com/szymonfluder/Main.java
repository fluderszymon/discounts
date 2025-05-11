package com.szymonfluder;


import com.szymonfluder.util.CalculatorInterface;
import com.szymonfluder.util.impl.Calculator;

public class Main {

    public static void main(String[] args) {
        CalculatorInterface calc = new Calculator(args[0], args[1]);
        calc.execute();
    }
}