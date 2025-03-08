<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="budget.schema.User" %>
<%@ page import="budget.DAO.ExpenseDAO" %>
<%@ page import="budget.schema.Expense" %>
<%@ page import="java.util.List" %>

<%@ include file="header.jsp" %>

<%
    // Session validation
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp?msg=unauthorized");
        return;
    }
%>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div class="container">
    <h2>Expenses</h2>
    <button class="add-expense-btn" onclick="openExpenseModal()">+ Add Expense</button>

    <!-- Expenses Table -->
    <table class="expenses-table">
        <thead>
            <tr>
                <th>Date</th>
                <th>Category</th>
                <th>Amount</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <%
                ExpenseDAO expenseDAO = new ExpenseDAO();
                List<Expense> expenseList = expenseDAO.getExpensesByUser(user.getUserId());
                for (Expense exp : expenseList) {
            %>
            <tr>
                <td><%= exp.getDate() %></td>
                <td><%= exp.getCategory() %></td>
                <td>$<%= exp.getAmount() %></td>
                <td><%= exp.getDescription() %></td>
                <td>
                    <button class="edit-btn">Edit</button>
                    <button class="delete-btn">Delete</button>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<!-- Add Expense Modal -->
<div id="expenseModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeExpenseModal()">&times;</span>
        <h2>Add Expense</h2>
        <form action="AddExpenseServlet" method="post">
            <input type="text" name="user_id" value="<%= user.getUserId() %>">

            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>
                        
            <label for="category">Category:</label>
			<input type="text" id="category" name="category" autocomplete="off" list="categoryList">
		    <datalist id="categoryList"></datalist>


            <label for="amount">Amount:</label>
            <input type="number" id="amount" name="amount" required>

            <label for="description">Description:</label>
            <textarea id="description" name="description"></textarea>

            <button type="submit">Save Expense</button>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>
