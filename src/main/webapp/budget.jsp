<%@ page import="java.util.List" %>
<%@ page import="budget.DAO.BudgetDAO" %>
<%@ page import="budget.schema.Budget" %>
<%@ page import="budget.schema.User" %>
<%@ include file="header.jsp" %>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div class="container">
    <h2>Budget</h2>
    
    <input type="text" class="search form-control" placeholder="Pending">
    
    <button class="add-expense-btn" onclick="openBudgetModal()">+ Add Budget</button>
    

    <!-- budget Table -->
    <table class="expenses-table" id="userTbl">
        <thead>
            <tr>
                <th>Month - Year</th>
                <th>Category</th>
                <th>Amount</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody  id="myTable">
            <%
            	BudgetDAO budgetDAO = new BudgetDAO();
            	List<Budget> budgetList = budgetDAO.getBudget(user.getUserId());
            	for(Budget bud : budgetList) {
            		
            		String monthName = "";
                    switch (bud.getMonth()) {
                        case 1: monthName = "January"; break;
                        case 2: monthName = "February"; break;
                        case 3: monthName = "March"; break;
                        case 4: monthName = "April"; break;
                        case 5: monthName = "May"; break;
                        case 6: monthName = "June"; break;
                        case 7: monthName = "July"; break;
                        case 8: monthName = "August"; break;
                        case 9: monthName = "September"; break;
                        case 10: monthName = "October"; break;
                        case 11: monthName = "November"; break;
                        case 12: monthName = "December"; break;
                        default: monthName = "Unknown";
                    }
            %>
            <tr>
                <td><%= monthName %> - <%= bud.getYear() %></td>
                <td><%= bud.getCategory() %></td>
                <td><%= bud.getAmount() %></td>
                <td>
                    <button class="edit-btn">Edit</button>
                    <button class="delete-btn">Delete</button>
                </td>
            </tr>
            
            <% } %>
            
        </tbody>
    </table>
</div>



<div id="budgetModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeBudgetModal()">&times;</span>
        <h2>Add Budget</h2>
        <form action="AddBudgetServlet" method="post">
            <input type="hidden" name="user_id" value="<%= user.getUserId() %>" readonly>

            <label for="month">Month:</label>
            <select id="month" name="month" required>
                <option value="1">January</option>
                <option value="2">February</option>
                <option value="3">March</option>
                <option value="4">April</option>
                <option value="5">May</option>
                <option value="6">June</option>
                <option value="7">July</option>
                <option value="8">August</option>
                <option value="9">September</option>
                <option value="10">October</option>
                <option value="11">November</option>
                <option value="12">December</option>
            </select>
            
            <label for="year">Year:</label>
            <select id="year" name="year" required>
                <% 
                    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
                    for(int i = 0; i <= 2; i++) {
                %>
                <option value="<%= currentYear + i %>"><%= currentYear + i %></option>
                <% } %>
            </select>
            
            <label for="category">Category:</label>
            <input type="text" id="category" name="category" autocomplete="off" list="categoryList">
            <datalist id="categoryList"></datalist>

            <label for="amount">Limit Amount:</label>
            <input type="number" id="amount" name="amount" required>

            <button type="submit">Save Budget</button>
        </form>
    </div>
</div>




<%@ include file="footer.jsp" %>
