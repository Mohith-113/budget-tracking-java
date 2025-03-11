package budget.servlet;

import java.io.IOException;
import java.sql.SQLException;

import budget.DAO.BudgetDAO;
import budget.schema.Budget;
import budget.schema.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddBudgetServlet")
public class AddBudgetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("index.jsp?msg=unauthorized");
			return;
		}
		
		int userId = user.getUserId();
		String category = request.getParameter("category");
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		
		Budget budget = new Budget(userId, month, year, amount, category);
		BudgetDAO budgetDAO = new BudgetDAO();
		
		try {
			budgetDAO.addBudget(budget);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("budget");
	}

}
