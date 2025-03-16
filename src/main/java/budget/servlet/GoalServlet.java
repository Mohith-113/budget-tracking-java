package budget.servlet;

import java.io.IOException;

import budget.schema.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/goal")
public class GoalServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		if (user == null) {
			response.sendRedirect("index.jsp?msg=unauthorized");
			return;
		}
		request.getRequestDispatcher("goal.jsp").forward(request, response);
	}

}
