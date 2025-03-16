<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="budget.schema.User" %>
<%@ page import="budget.schema.Goal" %>
<%@ page import="budget.DAO.GoalDAO" %>
<%@ page import="java.util.List" %>

<%@ include file="header.jsp" %>


<div class="container">

	<h2>Goal Setter</h2>
	<input type="text" class="search form-control" placeholder="Pending">
    
    <button class="add-expense-btn" onclick="openGoalModal()">+ Add Expense</button>
    

    <!-- Expenses Table -->
    <table class="expenses-table" id="userTbl">
        <thead>
            <tr>
                <th>Goal Name</th>
                <th>Target Amount</th>
                <th>Reached Amount</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody  id="myTable">
        
        <%
        	GoalDAO goalDAO = new GoalDAO();
        	List<Goal> goalList = goalDAO.getGoal(user.getUserId());
        	for (Goal g : goalList) {
        %>
        <tr>
        <td><%= g.getGoal_name() %> </td>
        <td><%= g.getTarget_amount() %> </td>
        <td><%= g.getSaving_amount() %> </td>
        <td><%= g.getStart_date() %> </td>
        <td><%= g.getEnd_date() %> </td>
        <td><%= g.getStatus() %> </td>
        <td> 
        	<button class="edit-btn">Edit</button>
			<button class="add-expense-btn" onclick="openAmountModal('<%= g.getGoal_id() %>', '<%= g.getGoal_name() %>')">ADD Amount</button>
			
        </td>
           </tr>
           <% } %> 
        </tbody>
    </table>
</div>


<!-- Add Amount to Goal Modal -->
<div id="amountModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeAmountModal()">&times;</span>
        <h2>Add Amount</h2>
        
        <form action="UpdateGoalAmountServlet" method="post">
            <input type="hidden" name="user_id" value="<%= user.getUserId() %>" readonly>
            <input type="hidden" id="goal_id" name="goal_id" readonly>
            
            <label for="goal_name">Goal Name</label>
            <input type="text" id="goal_name_display" name="goal_name" readonly>
            
            <label for="current_savings">Saving Amount:</label>
            <input type="number" id="current_savings" name="current_savings" step="1" required>
            
            <button type="submit">Save Amount</button>
        </form>
    </div>
</div>

<!-- Add Expense Modal -->
<div id="goalModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="closeGoalModal()">&times;</span>
        <h2>Add Goal</h2>
        <form action="AddGoalServlet" method="post">
            <input type="hidden" name="user_id" value="<%= user.getUserId() %>" readonly>

            <label for="goal_name">Goal Name:</label>
        	<input type="text" id="goal_name" name="goal_name" required>
	
    	    <label for="target_amount">Target Amount:</label>
        	<input type="number" id="target_amount" name="target_amount" step="1" required>
	
    	    <label for="current_savings">Current Savings:</label>
        	<input type="number" id="current_savings" name="current_savings" step="1" required>
	
    	    <label for="start_date">Start Date:</label>
        	<input type="date" id="start_date" name="start_date" required>
	
    	    <label for="end_date">End Date:</label>
        	<input type="date" id="end_date" name="end_date" required>
	
            <button type="submit">Save Goal</button>
        </form>
    </div>

</div>

<%@ include file="footer.jsp" %>