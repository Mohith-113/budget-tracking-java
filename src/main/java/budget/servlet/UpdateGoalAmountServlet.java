package budget.servlet;

import java.io.IOException;
import java.sql.SQLException;

import budget.DAO.GoalDAO;
import budget.schema.Goal;
import budget.schema.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UpdateGoalAmountServlet")
public class UpdateGoalAmountServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("index.jsp?msg=unauthorized");
			return;
		}
		
		int userId = user.getUserId();
		int goal_id = Integer.parseInt(request.getParameter("goal_id"));
		Double amount = Double.parseDouble(request.getParameter("current_savings"));
		
		Goal goal = new Goal(userId, goal_id, amount);
		
		
		GoalDAO goalDAO = new GoalDAO();
		
		try {
			goalDAO.updateGoalAmount(goal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("goal");
	}
	
	

}
