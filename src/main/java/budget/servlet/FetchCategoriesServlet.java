package budget.servlet;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import budget.db.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FetchCategoriesServlet")
public class FetchCategoriesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<String> categoryList = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT category_name FROM categories WHERE category_name LIKE ?")) {
                
                ps.setString(1, query + "%"); // Correct parameterized query
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    categoryList.add(rs.getString("category_name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Convert the list to JSON using Gson
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(categoryList); // Proper JSON conversion
        
        System.out.println("JSON Response: " + jsonResponse); // Debugging log
        out.print(jsonResponse);
        out.flush();
    }
}
