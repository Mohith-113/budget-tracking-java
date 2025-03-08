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
        String category = request.getParameter("category");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");

        Expense expense = new Expense(userId, date, category, amount, description);
        ExpenseDAO expenseDAO = new ExpenseDAO();
        expenseDAO.addExpense(expense);

        response.sendRedirect("expenses.jsp");
    }
}
