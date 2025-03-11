package budget.servlet;

import budget.DAO.ExpenseDAO;
import budget.schema.Expense;
import budget.schema.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("index.jsp?msg=unauthorized-AddExpenseServlet");
            return;
        }

        int userId = user.getUserId();
        String date = request.getParameter("date");
        String category = request.getParameter("category"); // Category Name
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");
        
        int categoryId = 0;
        ExpenseDAO expenseDAO = new ExpenseDAO();
		try {
			categoryId = expenseDAO.getCategoryId(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


        int year = java.time.LocalDate.parse(date).getYear();
        int month = java.time.LocalDate.parse(date).getMonthValue();

        double monthlySpent = expenseDAO.getTotalSpentByUser(userId, year, month);
        double categorySpent = expenseDAO.getTotalSpentByCategory(userId, categoryId, year, month);

        double monthlyLimit = expenseDAO.getMonthlyLimit(userId, year, month);
        double categoryLimit = expenseDAO.getCategoryLimit(userId, categoryId, year, month);

        boolean exceedsMonthly = (monthlySpent + amount) > monthlyLimit;
        boolean exceedsCategory = (categorySpent + amount) > categoryLimit;

        if (exceedsMonthly || exceedsCategory) {
            request.setAttribute("exceedsMonthly", exceedsMonthly);
            request.setAttribute("exceedsCategory", exceedsCategory);
            request.setAttribute("amount", amount);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("date", date);
            request.setAttribute("description", description);

            request.getRequestDispatcher("budgetExceeded.jsp").forward(request, response);
        } else {
            // Directly insert the expense if no limits are exceeded
            Expense expense = new Expense(userId, date, categoryId, amount, description);
            expenseDAO.addExpense(expense);
            response.sendRedirect("expense?msg=expense-added");
        }
    }
}
