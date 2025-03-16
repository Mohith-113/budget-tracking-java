package budget.servlet;

import java.io.IOException;

import budget.DAO.ExpenseDAO;
import budget.schema.Expense;
import budget.schema.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ConfirmExpenseServlet")
public class ConfirmExpenseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("index.jsp?msg=unauthorized-ConfirmExpenseServlet");
            return;
        }

        int userId = user.getUserId();
        String date = request.getParameter("date");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");
        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));

        ExpenseDAO expenseDAO = new ExpenseDAO();
        Expense expense = new Expense(userId, date, categoryId, amount, description, year, month);

        expenseDAO.addExpense(expense);

        response.sendRedirect("expense?msg=expense-added");
    }
}
