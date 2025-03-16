package budget.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import budget.db.DBConnection;

public class AnalyticsDAO {
    public Map<String, Object> getAnalytics(int userId) throws SQLException {
        Map<String, Object> analyticsData = new HashMap<>();
        Connection conn = DBConnection.getConnection();

        // Expenses by Category
        String expenseQuery = "SELECT c.category_name, SUM(e.amount) AS total " +
                              "FROM expenses e " +
                              "JOIN categories c ON e.category_id = c.category_id " +
                              "WHERE e.user_id = ? " +
                              "GROUP BY c.category_name";
        try (PreparedStatement stmt = conn.prepareStatement(expenseQuery)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            Map<String, Double> expenses = new HashMap<>();
            while (rs.next()) {
                expenses.put(rs.getString("category_name"), rs.getDouble("total"));
            }
            analyticsData.put("expenses", expenses);
        }

        // Budget Usage
        String budgetQuery = "SELECT c.category_name, b.monthly_limit " +
                              "FROM budgets b " +
                              "JOIN categories c ON b.category_id = c.category_id " +
                              "WHERE b.user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(budgetQuery)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            Map<String, Double> budgets = new HashMap<>();
            while (rs.next()) {
                budgets.put(rs.getString("category_name"), rs.getDouble("monthly_limit"));
            }
            analyticsData.put("budgets", budgets);
        }

        // Goal Progress
        String goalQuery = "SELECT g.goal_name, g.target_amount, g.current_savings " +
                           "FROM financial_goals g " +
                           "WHERE g.user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(goalQuery)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            Map<String, Map<String, Double>> goals = new HashMap<>();
            while (rs.next()) {
                Map<String, Double> goalData = new HashMap<>();
                goalData.put("target", rs.getDouble("target_amount"));
                goalData.put("saved", rs.getDouble("current_savings"));
                goals.put(rs.getString("goal_name"), goalData);
            }
            analyticsData.put("goals", goals);
        }

        conn.close();
        return analyticsData;
    }
}