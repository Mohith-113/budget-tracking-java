package budget.DAO;

import budget.db.DBConnection;
import budget.schema.Expense;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
	
	
	public double getTotalSpentByUser(int userId, int year, int month) {
	    String sql = "SELECT SUM(amount) FROM expenses WHERE user_id = ? AND year = ? AND month = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        stmt.setInt(2, year);
	        stmt.setInt(3, month);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) return rs.getDouble(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	public double getTotalSpentByCategory(int userId, int categoryId, int year, int month) {
	    String sql = "SELECT SUM(amount) FROM expenses WHERE user_id = ? AND category_id = ? AND year = ? AND month = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        stmt.setInt(2, categoryId);
	        stmt.setInt(3, year);
	        stmt.setInt(4, month);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) return rs.getDouble(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

	public double getMonthlyLimit(int userId, int year, int month) {
	    String sql = "SELECT SUM(monthly_limit) FROM budgets WHERE user_id = ? AND year = ? AND month = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        stmt.setInt(2, year);
	        stmt.setInt(3, month);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) return rs.getDouble(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return Double.MAX_VALUE;
	}

	public double getCategoryLimit(int userId, int categoryId, int year, int month) {
		String sql = "SELECT SUM(monthly_limit) FROM budgets WHERE user_id = ? AND year = ? AND month = ? AND category_id = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        stmt.setInt(2, year);
	        stmt.setInt(3, month);
	        stmt.setInt(4, categoryId);	       
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) return rs.getDouble(1);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return Double.MAX_VALUE;
	}


    // ✅ Method to get category ID (or insert if not exists)
	public int getCategoryId(String categoryName) throws SQLException {
	    
	    System.out.println("[DEBUG] Method getCategoryId called with categoryName: " + categoryName);
	    
	    if (categoryName == null || categoryName.trim().isEmpty()) {
	        System.out.println("[DEBUG] Invalid category name: null or empty");
	        throw new SQLException("Category name cannot be null or empty");
	    }
	    
	    String checkQuery = "SELECT category_id FROM categories WHERE category_name = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
	        
	        System.out.println("[DEBUG] Executing query to check existing category: " + checkQuery);
	        checkStmt.setString(1, categoryName);
	        ResultSet rs = checkStmt.executeQuery();
	        
	        if (rs.next()) {
	            int existingCategoryId = rs.getInt("category_id");
	            System.out.println("[DEBUG] Category found, returning existing category_id: " + existingCategoryId);
	            return existingCategoryId; // ✅ Return existing category_id
	        }
	    }
	    
	    // If category does not exist, insert it
	    String insertQuery = "INSERT INTO categories (category_name) VALUES (?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
	        
	        System.out.println("[DEBUG] Executing insert query: " + insertQuery);
	        insertStmt.setString(1, categoryName);
	        int rowsAffected = insertStmt.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int newCategoryId = generatedKeys.getInt(1);
	                System.out.println("[DEBUG] Category inserted, returning new category_id: " + newCategoryId);
	                return newCategoryId; // ✅ Return newly created category_id
	            }
	        }
	    }
	    
	    System.out.println("[DEBUG] Failed to insert category: " + categoryName);
	    throw new SQLException("Failed to insert category: " + categoryName);
	}

    // ✅ Add expense and also insert into transactions table
    public void addExpense(Expense expense) {
        String expenseSQL = "INSERT INTO expenses (user_id, date, category_id, amount, description, month, year) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String transactionSQL = "INSERT INTO transactions (user_id, expense_id, transaction_type, amount, date, month, year) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // ✅ Start transaction

            int expenseId =-1;
            
            LocalDate expenseDate = LocalDate.parse(expense.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int month1 = expenseDate.getMonthValue();
            int year1 = expenseDate.getYear();

            // Insert into expenses table
            try (PreparedStatement expenseStmt = conn.prepareStatement(expenseSQL, Statement.RETURN_GENERATED_KEYS)) {
                expenseStmt.setInt(1, expense.getUserId());
                expenseStmt.setString(2, expense.getDate());
                expenseStmt.setInt(3, expense.getCategoryId());
                expenseStmt.setDouble(4, expense.getAmount());
                expenseStmt.setString(5, expense.getDescription());
                expenseStmt.setInt(6, expense.getMonth());
                expenseStmt.setInt(7, expense.getYear());
                expenseStmt.executeUpdate();

                ResultSet rs = expenseStmt.getGeneratedKeys();
                if (rs.next()) {
                    expenseId = rs.getInt(1); // ✅ Get generated expense_id
                }
            }

            if (expenseId > 0) {
                // Insert into transactions table
                try (PreparedStatement transactionStmt = conn.prepareStatement(transactionSQL)) {
                    transactionStmt.setInt(1, expense.getUserId());
                    transactionStmt.setInt(2, expenseId);
                    transactionStmt.setString(3, "EXPENSE"); // ✅ Transaction type
                    transactionStmt.setDouble(4, expense.getAmount());
                    transactionStmt.setString(5, expense.getDate());
                    transactionStmt.setInt(6, expense.getMonth());
                    transactionStmt.setInt(7, expense.getYear());
                    transactionStmt.executeUpdate();
                }
            }

            conn.commit(); // ✅ Commit both inserts
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Retrieve expenses for a user (fetch category_name using category_id)
    public List<Expense> getExpensesByUser(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT e.expense_id, e.user_id, e.date, c.category_name, e.amount, e.description " +
                "FROM expenses e " +
                "JOIN categories c ON e.category_id = c.category_id " +
                "WHERE e.user_id = ? ORDER BY e.date DESC;";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Expense expense = new Expense();
                    expense.setId(rs.getInt("expense_id"));
                    expense.setUserId(rs.getInt("user_id"));
                    expense.setDate(rs.getString("date"));
                    expense.setCategory(rs.getString("category_name")); // ✅ Fetch category_name
                    expense.setAmount(rs.getDouble("amount"));
                    expense.setDescription(rs.getString("description"));
                    expenses.add(expense);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }
}
