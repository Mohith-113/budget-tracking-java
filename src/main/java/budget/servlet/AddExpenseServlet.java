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

        String categoryName = request.getParameter("category");

        if (categoryName == null || categoryName.trim().isEmpty()) {
            System.out.println("[DEBUG] Category is missing!");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category cannot be null or empty");
            return;
        }

        System.out.println("[DEBUG] Received category: " + categoryName);


        int userId = user.getUserId();
        String date = request.getParameter("date");
//        String category = request.getParameter("category"); // Category Name
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");
        
        ExpenseDAO expenseDAO = new ExpenseDAO();
        int categoryId = -1;

        
            System.out.println("[DEBUG] Fetching categoryId for category: " + categoryName);
            try {
            	System.out.println("[DEBUG] calling");
				categoryId = expenseDAO.getCategoryId(categoryName);
				System.out.println("[DEBUG] ended calling");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("[DEBUG] Retrieved categoryId: " + categoryId);
        

        if (categoryId == -1) {
            System.out.println("[DEBUG] categoryId is invalid!");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Invalid category");
            return;
        }
        System.out.println("[DEBUG] Moving to get year and month");

        int year = java.time.LocalDate.parse(date).getYear();
        int month = java.time.LocalDate.parse(date).getMonthValue();
        System.out.println("[DEBUG] Moving to get spents ");

        double monthlySpent = expenseDAO.getTotalSpentByUser(userId, year, month);
        double categorySpent = expenseDAO.getTotalSpentByCategory(userId, categoryId, year, month);
        
        System.out.println("[DEBUG] Moving to get limits");

        double monthlyLimit = expenseDAO.getMonthlyLimit(userId, year, month);
        double categoryLimit = expenseDAO.getCategoryLimit(userId, categoryId, year, month);
        
        System.out.println("[DEBUG] Validating spents and limits");

        boolean exceedsMonthly = (monthlySpent + amount) > monthlyLimit;
        boolean exceedsCategory = (categorySpent + amount) > categoryLimit;

        if (exceedsMonthly || exceedsCategory) {
            request.setAttribute("exceedsMonthly", exceedsMonthly);
            request.setAttribute("exceedsCategory", exceedsCategory);
            request.setAttribute("amount", amount);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("date", date);
            request.setAttribute("description", description);
            request.setAttribute("year", year);
            request.setAttribute("month", month);

            request.getRequestDispatcher("budgetExceeded.jsp").forward(request, response);
        } else {
            // Directly insert the expense if no limits are exceeded
            Expense expense = new Expense(userId, date, categoryId, amount, description, year, month);
            expenseDAO.addExpense(expense);
            response.sendRedirect("expense?msg=expense-added");
        }
    }
}
