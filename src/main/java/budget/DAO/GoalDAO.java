package budget.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import budget.db.DBConnection;
import budget.schema.Goal;

public class GoalDAO {
	public void addGoal(Goal goal) throws SQLException {
		String GoalSQL = "INSERT INTO financial_goals (user_id, goal_name, target_amount, current_savings, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
		String ContributeSQL = "INSERT INTO goal_contributions (user_id, goal_id, amount) VALUES (?, ?, ?)";
		String TransactionSQL = "INSERT INTO transactions (user_id, goal_id, transaction_type, amount, date, month, year) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		
		
		try(Connection conn = DBConnection.getConnection()){
			conn.setAutoCommit(false);
			
			int goalId = -1;
			
			
			try(PreparedStatement goalStmt = conn.prepareStatement(GoalSQL, Statement.RETURN_GENERATED_KEYS)) {
				goalStmt.setInt(1, goal.getUserId());
				goalStmt.setString(2, goal.getGoal_name());
				goalStmt.setDouble(3, goal.getTarget_amount());
				goalStmt.setDouble(4, goal.getSaving_amount());
				goalStmt.setString(5, goal.getStart_date());
				goalStmt.setString(6, goal.getEnd_date());
				goalStmt.setString(7, goal.getStatus());
				
				goalStmt.executeUpdate();
				
				ResultSet rs = goalStmt.getGeneratedKeys();
				
				if(rs.next()) {
					goalId = rs.getInt(1);
				}
			}
			
			if(goalId > 0) {
				
				
				LocalDate today = LocalDate.now();
                int currentMonth = today.getMonthValue();
                int currentYear = today.getYear();
				
				
				try(PreparedStatement  contributeStmt = conn.prepareStatement(ContributeSQL)){
					contributeStmt.setInt(1, goal.getUserId());
					contributeStmt.setInt(2, goalId);
					contributeStmt.setDouble(3, goal.getSaving_amount());
					
					contributeStmt.executeUpdate();
				}
				
				try (PreparedStatement transactionStmt = conn.prepareStatement(TransactionSQL)) {
                    transactionStmt.setInt(1, goal.getUserId());
                    transactionStmt.setInt(2, goalId);
                    transactionStmt.setString(3, "SAVINGS"); 
                    transactionStmt.setDouble(4, goal.getSaving_amount());
                    transactionStmt.setDate(5, java.sql.Date.valueOf(today)); //today date
                    transactionStmt.setInt(6, currentMonth); //this month
                    transactionStmt.setInt(7, currentYear); //this year
                    transactionStmt.executeUpdate();
                }
			}
			
			conn.commit();
			
		}
	}
	
	public List<Goal> getGoal(int userId) throws SQLException {
		List<Goal> goals = new ArrayList<>();
		
		String fetchGoal = "SELECT * FROM financial_goals WHERE user_id = ?";
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement fetchStmt = conn.prepareStatement(fetchGoal)) {
			
			fetchStmt.setInt(1,  userId);
			
			try(ResultSet rs = fetchStmt.executeQuery()){
				while(rs.next()) {
					Goal goal = new Goal();
					goal.setGoal_id(rs.getInt("goal_id"));
					goal.setGoal_name(rs.getString("goal_name"));
					goal.setTarget_amount(rs.getDouble("target_amount"));
					goal.setSaving_amount(rs.getDouble("current_savings"));
					goal.setStart_date(rs.getString("start_date"));
					goal.setEnd_date(rs.getString("end_date"));
					goal.setStatus(rs.getString("status"));
					
					goals.add(goal);
					
				}
			}
			return goals;
		}
	}

	public void updateGoalAmount(Goal goal) throws SQLException {
	    String updateSQL = "UPDATE financial_goals SET current_savings = current_savings + ? WHERE goal_id = ? AND user_id = ?";
	    String contributeSQL = "INSERT INTO goal_contributions (user_id, goal_id, amount) VALUES (?, ?, ?)";
	    String transactionSQL = "INSERT INTO transactions (user_id, goal_id, transaction_type, amount, date, month, year) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBConnection.getConnection()) {
	        conn.setAutoCommit(false);  

	        try (
	            PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
	            PreparedStatement contributeStmt = conn.prepareStatement(contributeSQL);
	            PreparedStatement transactionStmt = conn.prepareStatement(transactionSQL)
	        ) {
	            // 1. Update savings in financial_goals
	            updateStmt.setDouble(1, goal.getSaving_amount());
	            updateStmt.setInt(2, goal.getGoal_id());
	            updateStmt.setInt(3, goal.getUserId());
	            updateStmt.executeUpdate();

	            // 2. Insert into goal_contributions
	            contributeStmt.setInt(1, goal.getUserId());
	            contributeStmt.setInt(2, goal.getGoal_id());
	            contributeStmt.setDouble(3, goal.getSaving_amount());
	            contributeStmt.executeUpdate();

	            // 3. Insert into transactions
	            transactionStmt.setInt(1, goal.getUserId());
	            transactionStmt.setInt(2, goal.getGoal_id());
	            transactionStmt.setString(3, "SAVINGS");  
	            transactionStmt.setDouble(4, goal.getSaving_amount());
	            LocalDate today = LocalDate.now();
	            LocalDate currentDate = LocalDate.now();
	            transactionStmt.setDate(5, java.sql.Date.valueOf(today)); // date
	            transactionStmt.setInt(6, currentDate.getMonthValue()); // month
	            transactionStmt.setInt(7, currentDate.getYear()); // year
	            
	            transactionStmt.executeUpdate();

	            conn.commit();  
	        } catch (SQLException e) {
	            conn.rollback();  
	            throw e;
	        } finally {
	            conn.setAutoCommit(true);
	        }
	    }
	}

}
