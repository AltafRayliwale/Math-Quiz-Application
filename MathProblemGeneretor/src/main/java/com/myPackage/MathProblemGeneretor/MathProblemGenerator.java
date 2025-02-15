package com.myPackage.MathProblemGeneretor;

import java.util.Random;

public class MathProblemGenerator {
    private Random random = new Random();

    // Generate a random math problem
    public String generateProblem(String operation) {
        int num1 = random.nextInt(10) + 1; // Generate random numbers between 1 and 10
        int num2 = random.nextInt(10) + 1;
        
        switch (operation) {
            case "addition":
                return num1 + " + " + num2;
            case "subtraction":
                return num1 + " - " + num2;
            case "multiplication":
                return num1 + " * " + num2;
            default:
                return num1 + " + " + num2;
        }
    }

    // Get the answer to the problem
    public int getAnswer(String problem, String operation) {
        String[] parts = problem.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[2]);
        
        switch (operation) {
            case "addition":
                return num1 + num2;
            case "subtraction":
                return num1 - num2;
            case "multiplication":
                return num1 * num2;
            default:
                return num1 + num2;
        }
    }
}
