package budget.servlet;

import java.io.IOException;

import budget.DAO.UserDAO;
import budget.schema.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("uname");
		String email = request.getParameter("email");
		String password = request.getParameter("psw");
		
		User user = new User(name, email, password);
		
		if(UserDAO.registerUser(user)) {
			try {
				response.sendRedirect("index.jsp? msg=success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("Statement -> RegisterServlet -> doPost -> UserDAO -> if");
			}
		}
		else {
			try {
				response.sendRedirect("register.jsp?msg=failure");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				System.out.print("Statement -> RegisterServlet -> doPost -> UserDAO -> else");
			}
		}
	}
}
