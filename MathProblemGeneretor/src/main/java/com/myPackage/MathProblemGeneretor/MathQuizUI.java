package com.myPackage.MathProblemGeneretor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MathQuizUI {
    private JFrame frame;
    private JTextArea questionArea;
    private JButton startQuizButton, submitAnswerButton, viewResultsButton;
    private MathQuiz mathQuiz;
    private JTextField answerField; 
    private int currentQuestion = 0; 
    private int score = 0; 

    public MathQuizUI() {
        mathQuiz = new MathQuiz();
        DatabaseManager.init();

        frame = new JFrame("Math Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); 

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(235, 235, 235)); 

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("Arial", Font.BOLD, 20));
        questionArea.setBackground(new Color(255, 255, 255)); 
        questionArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        JScrollPane questionScrollPane = new JScrollPane(questionArea);
        questionScrollPane.setPreferredSize(new Dimension(550, 150));
        contentPanel.add(questionScrollPane, BorderLayout.NORTH);

        answerField = new JTextField(10);
        answerField.setFont(new Font("Arial", Font.PLAIN, 18));
        answerField.setBackground(new Color(255, 255, 255)); 
        answerField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        answerPanel.setBackground(new Color(235, 235, 235)); 
        answerPanel.add(new JLabel("Your Answer:"));
        answerPanel.add(answerField);
        contentPanel.add(answerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 10)); 
        buttonPanel.setBackground(new Color(235, 235, 235));

        startQuizButton = new JButton("Start Quiz");
        startQuizButton.setFont(new Font("Arial", Font.BOLD, 16));
        startQuizButton.setBackground(new Color(0, 123, 255)); 
        startQuizButton.setForeground(Color.WHITE); 
        startQuizButton.setFocusPainted(false);
        startQuizButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        startQuizButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startQuizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuiz();
            }
        });

        submitAnswerButton = new JButton("Submit Answer");
        submitAnswerButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitAnswerButton.setBackground(new Color(40, 167, 69)); 
        submitAnswerButton.setForeground(Color.WHITE); 
        submitAnswerButton.setFocusPainted(false);
        submitAnswerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        submitAnswerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitAnswerButton.setEnabled(false); 
        submitAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });

        viewResultsButton = new JButton("View Past Results");
        viewResultsButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewResultsButton.setBackground(new Color(255, 193, 7)); 
        viewResultsButton.setForeground(Color.WHITE); // White text
        viewResultsButton.setFocusPainted(false);
        viewResultsButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        viewResultsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	List<String> results = DatabaseManager.getResults();

                StringBuilder sb = new StringBuilder();
                sb.append("Past Results:\n");
                if (results.isEmpty()) {
                    sb.append("No results found.");
                } else {
                    for (String result : results) {
                        sb.append(result + "\n");
                    }
                }
                questionArea.setText(sb.toString());
            }
        });

        buttonPanel.add(startQuizButton);
        buttonPanel.add(submitAnswerButton);
        buttonPanel.add(viewResultsButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void startQuiz() {
        score = 0;
        currentQuestion = 0;

        questionArea.setText("Quiz Started!\n");
        submitAnswerButton.setEnabled(true);

        showNextQuestion();
    }

    private void showNextQuestion() {
        if (currentQuestion < 5) {
            String question = mathQuiz.getNextQuestion();
            questionArea.setText("Question " + (currentQuestion + 1) + ": " + question);
        } else {
            String resultMessage = "Quiz Completed!\nScore: " + score + "/5\n";
            
            if (score == 5) {
                resultMessage += "Excellent! You got all the answers correct!";
            } else if (score >= 3) {
                resultMessage += "Good job! You got " + score + " out of 5 correct.";
            } else {
                resultMessage += "Better luck next time! You got " + score + " out of 5 correct.";
            }

            questionArea.setText(resultMessage);
            submitAnswerButton.setEnabled(false);

            // Save the result to the database
            DatabaseManager.saveResult(score, 5);
        }
    }


    private void submitAnswer() {
        
        String answerText = answerField.getText().trim();
        if (!answerText.isEmpty()) {
            try {
                int userAnswer = Integer.parseInt(answerText);
                if (mathQuiz.checkAnswer(userAnswer)) {
                    score++;
                }
            } catch (NumberFormatException e) {
                questionArea.append("\nInvalid input! Please enter a valid number.");
                return;
            }

            currentQuestion++;
            answerField.setText(""); 
            showNextQuestion(); 
        } else {
            questionArea.append("\nPlease enter an answer before submitting.");
        }
    }

    public static void main(String[] args) {
        new MathQuizUI();
    }
}
