<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List</title>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
            margin: 20px 0;
            font-size: 18px;
            text-align: left;
        }
        th, td {
            padding: 10px;
            border: 1px solid black;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

    <h2>User List</h2>

    <%
        // Database credentials
        String url = "jdbc:mysql://localhost:3308/Budgettracker?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "root";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);

            // SQL query to fetch users
            String query = "SELECT user_id, name, email FROM Users";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            // Display users in table
            if (!rs.isBeforeFirst()) {
    %>
                <p>No users found in the database.</p>
    <%
            } else {
    %>
                <table>
                    <tr>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                    </tr>
    <%
                while (rs.next()) {
    %>
                    <tr>
                        <td><%= rs.getInt("user_id") %></td>
                        <td><%= rs.getString("name") %></td>
                        <td><%= rs.getString("email") %></td>
                    </tr>
    <%
                }
    %>
                </table>
    <%
            }
        } catch (ClassNotFoundException e) {
    %>
            <p style='color:red;'>JDBC Driver Not Found! Add mysql-connector-java.jar</p>
    <%
        } catch (SQLException e) {
    %>
            <p style='color:red;'>Database Connection Failed!</p>
            <p>Error: <%= e.getMessage() %></p>
    <%
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException ignored) {}
            try { if (conn != null) conn.close(); } catch (SQLException ignored) {}
        }
    %>

</body>
</html>
