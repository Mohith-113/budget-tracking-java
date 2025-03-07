package budget.servlet;

import java.io.IOException;
import java.util.List;
import budget.DAO.UserDAO;
import budget.schema.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<User> users = UserDAO.getAllUsers();
            request.setAttribute("users", users);
            response.setContentType("text/html; charset=UTF-8"); // Fix encoding issue
            request.getRequestDispatcher("user-list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve user list.");
        }
    }
}
