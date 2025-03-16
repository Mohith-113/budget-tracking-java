package budget.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import budget.DAO.GoalDAO;
import budget.schema.Goal;
import budget.schema.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddGoalServlet")
public class AddGoalSservlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null) {
			response.sendRedirect("index.jsp?msg=unauthorized");
			return;
		}
		
		int userId = user.getUserId();
		String goal_name = request.getParameter("goal_name");
		Double target_amount = Double.parseDouble(request.getParameter("target_amount"));
		Double saveing_amount = Double.parseDouble(request.getParameter("current_savings"));
		String start_date = request.getParameter("start_date");
		String end_date = request.getParameter("end_date");
		
		
		LocalDate startDate = LocalDate.parse(start_date);
		LocalDate endDate = LocalDate.parse(end_date);
		LocalDate today = LocalDate.now();

		// Determine goal status
		String status = target_amount > saveing_amount ? "In Progress" : "Completed";
		
		System.out.println(status);
		// Check if the end date has already passed
		if (endDate.isBefore(today)) {
			status = "Failed";
		}
		System.out.println(endDate.isAfter(today));
		System.out.println(status);
		Goal goal = new Goal(userId, goal_name, target_amount, saveing_amount, start_date, end_date, status);
		
		GoalDAO goalDAO = new GoalDAO();
		
		try {
			goalDAO.addGoal(goal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("goal");
		
	}

}
