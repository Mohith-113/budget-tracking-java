package budget.DAO;

import budget.db.DBConnection;
import budget.schema.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

	private int getCategoryId(String categoryName, Connection conn) throws SQLException {
        String checkQuery = "SELECT category_id FROM categories WHERE category_name = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, categoryName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id"); // ✅ Return existing category_id
            }
        }
        
        String insertQuery = "INSERT INTO categories (category_name) VALUES (?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, categoryName);
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // ✅ Return newly created category_id
            }
        }
        throw new SQLException("Failed to insert category: " + categoryName);
    }
	
	public void addBudget(Budget budget) throws SQLException {
		String BudgetSQL = "INSERT INTO budgets (user_id, category_id, month, year, monthly_limit) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnection.getConnection()) {
			int categoryID = getCategoryId(budget.getCategory(), conn);
			try (PreparedStatement budgetStmt = conn.prepareStatement(BudgetSQL)) {
				budgetStmt.setInt(1, budget.getUserId());
				budgetStmt.setInt(2, categoryID);
				budgetStmt.setInt(3, budget.getMonth());
				budgetStmt.setInt(4, budget.getYear());
				budgetStmt.setDouble(5, budget.getAmount());
				
				budgetStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Budget> getBudget(int userId) {
		
		List<Budget> budget = new ArrayList<>();
		
		String sql = "SELECT b.budget_id, b.user_id, b.month, b.year, b.monthly_limit, c.category_name FROM budgets b JOIN categories c ON b.category_id = c.category_id WHERE b.user_id = ? ORDER BY b.year DESC, b.month DESC;";
				
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
				stmt.setInt(1,  userId);
				
				try (ResultSet rs = stmt.executeQuery()){
					while (rs.next()) {
						Budget budget1 = new Budget();
						budget1.setBudget_id(rs.getInt("budget_id"));
						budget1.setUserId(rs.getInt("user_id"));
						budget1.setMonth(rs.getInt("month"));
						budget1.setYear(rs.getInt("year"));
						budget1.setAmount(rs.getDouble("monthly_limit"));
						budget1.setCategory(rs.getString("category_name"));
						budget.add(budget1);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return budget;
	}
}
