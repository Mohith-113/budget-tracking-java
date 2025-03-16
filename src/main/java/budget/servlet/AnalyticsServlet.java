package budget.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import budget.DAO.AnalyticsDAO;
import budget.schema.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AnalyticsServlet")
public class AnalyticsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("index.jsp?msg=unauthorized");
            return;
        }

        int userId = user.getUserId();
        AnalyticsDAO analyticsDAO = new AnalyticsDAO();

        try (PrintWriter out = response.getWriter()) {
            Map<String, Object> analyticsData = analyticsDAO.getAnalytics(userId);
            String json = new Gson().toJson(analyticsData);
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving analytics data");
        }
    }
}
