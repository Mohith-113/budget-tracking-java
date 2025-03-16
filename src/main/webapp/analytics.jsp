<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="container">
    <h2>Financial Analytics & Insights</h2>

    <div>
        <h4>Expenses Breakdown</h4>
        <canvas id="expenseChart" style="width: 50vw !important; height: 50vh !important;" ></canvas>
    </div>

    <div>
        <h4>Budget Utilization</h4>
        <canvas id="budgetChart" style="width: 30vw; height: auto;"></canvas>
    </div>

    <div>
        <h4>Goal Progress</h4>
        <canvas id="goalChart" style="width: 30vw; height: auto;"></canvas>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="analytics.js"></script>

<%@ include file="footer.jsp" %>

