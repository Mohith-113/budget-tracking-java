package budget.servlet;

import java.io.IOException;

import budget.DAO.UserDAO;
import budget.schema.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("psw");

        User user = UserDAO.loginUser(email, password);
        
        if (user != null) {
            // Login successful - Create a session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            // Redirect to dashboard or home page
            response.sendRedirect("welcome.jsp");
        } else {
            // Login failed - Redirect back with error message
            response.sendRedirect("login.jsp?msg=failure");
        }
    }
}
