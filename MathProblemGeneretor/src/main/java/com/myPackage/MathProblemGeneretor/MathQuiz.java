package com.myPackage.MathProblemGeneretor;
import java.util.Random;

public class MathQuiz {
    private Random random = new Random();
    private String currentQuestion;
    private int correctAnswer;
    private int questionCount = 0; 

    public String getNextQuestion() {
        if (questionCount < 5) {
            String operation = getRandomOperation();
            currentQuestion = generateProblem(operation);
            correctAnswer = getAnswer(currentQuestion, operation);
            questionCount++;
            return currentQuestion;
        } else {
            return null; 
        }
    }

    public boolean checkAnswer(int userAnswer) {
        return userAnswer == correctAnswer;
    }

    private String getRandomOperation() {
        String[] operations = {"addition", "subtraction", "multiplication"};
        return operations[(int) (Math.random() * operations.length)];
    }

    private String generateProblem(String operation) {
        int num1 = random.nextInt(10) + 1;
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

    private int getAnswer(String problem, String operation) {
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
