package budget.DAO;

import budget.db.DBConnection;
import budget.schema.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    // ✅ Method to get category ID (or insert if not exists)
    private int getCategoryId(String categoryName, Connection conn) throws SQLException {
        String checkQuery = "SELECT category_id FROM categories WHERE category_name = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setString(1, categoryName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("category_id"); // ✅ Return existing category_id
            }
        }

        // If category does not exist, insert it
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

    // ✅ Add expense and also insert into transactions table
    public void addExpense(Expense expense) {
        String expenseSQL = "INSERT INTO expenses (user_id, date, category_id, amount, description) VALUES (?, ?, ?, ?, ?)";
        String transactionSQL = "INSERT INTO transactions (user_id, expense_id, transaction_type, amount, date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // ✅ Start transaction

            int categoryId = getCategoryId(expense.getCategory(), conn); // Get or insert category
            int expenseId = -1;

            // Insert into expenses table
            try (PreparedStatement expenseStmt = conn.prepareStatement(expenseSQL, Statement.RETURN_GENERATED_KEYS)) {
                expenseStmt.setInt(1, expense.getUserId());
                expenseStmt.setString(2, expense.getDate());
                expenseStmt.setInt(3, categoryId);
                expenseStmt.setDouble(4, expense.getAmount());
                expenseStmt.setString(5, expense.getDescription());
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
