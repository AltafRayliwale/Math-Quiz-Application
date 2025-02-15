package com.myPackage.MathProblemGeneretor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/math_quiz_db";
    private static final String DB_USERNAME = "root";  
    private static final String DB_PASSWORD = "Root";  

    public static void init() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS results (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "score INT, " +
                    "totalQuestions INT, " +
                    "quiz_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveResult(int score, int totalQuestions) {
        String insertSQL = "INSERT INTO results(score, totalQuestions) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, score);
            pstmt.setInt(2, totalQuestions);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getResults() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT * FROM results ORDER BY id DESC"; // Get the latest results
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int score = rs.getInt("score");
                int totalQuestions = rs.getInt("totalQuestions");
                results.add("Score: " + score + "/" + totalQuestions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results; 
    }

}
