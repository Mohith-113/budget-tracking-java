<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="header.jsp" %>

<%
    boolean exceedsMonthly = (boolean) request.getAttribute("exceedsMonthly");
    boolean exceedsCategory = (boolean) request.getAttribute("exceedsCategory");
    double amount = (double) request.getAttribute("amount");
    int categoryId = (int) request.getAttribute("categoryId");
    String date = (String) request.getAttribute("date");
    String description = (String) request.getAttribute("description");
    int month = (int) request.getAttribute("month");
    int year = (int) request.getAttribute("year");

    String message = "";
    if (exceedsMonthly && exceedsCategory) {
        message = "You are exceeding both the monthly limit and category limit. Do you want to proceed?";
    } else if (exceedsMonthly) {
        message = "You have exceeded the monthly limit. Do you want to proceed?";
    } else if (exceedsCategory) {
        message = "You are exceeding the category limit. Do you want to proceed?";
    }
%>

<script>
    function proceedExpense() {
        document.getElementById("confirmForm").submit();
    }

    function cancelExpense() {
        window.location.href = "expense";
    }
</script>

<div id="budgetExceededModal" class="modal">
    <div class="modal-content">
        <h2>Budget Exceeded</h2>
        <p><%= message %></p>
        <form id="confirmForm" action="ConfirmExpenseServlet" method="post">
            <input type="hidden" name="amount" value="<%= amount %>">
            <input type="hidden" name="categoryId" value="<%= categoryId %>">
            <input type="hidden" name="date" value="<%= date %>">
            <input type="hidden" name="year" value="<%= year %>">
            <input type="hidden" name="month" value="<%= month %>">
            <input type="hidden" name="description" value="<%= description %>">
            <button type="button" onclick="proceedExpense()">Proceed</button>
            <button type="button" onclick="cancelExpense()">Cancel</button>
        </form>
    </div>
</div>

<script>
    document.getElementById("budgetExceededModal").style.display = "block";
</script>

<%@ include file="footer.jsp" %>
