/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
    fetch("AnalyticsServlet")
        .then(response => response.json())
        .then(data => {
            renderExpenseChart(data.expenses);
            renderBudgetChart(data.budgets);
            renderGoalChart(data.goals);
        })
        .catch(error => console.error("Error fetching analytics:", error));
});

function getRandomColor(existingColors) {
    let color;
    do {
        color = `#${Math.floor(Math.random()*16777215).toString(16).padStart(6, '0')}`;
    } while (existingColors.includes(color)); // Ensure unique color
    return color;
}

function renderExpenseChart(expenses) {
    let labels = Object.keys(expenses);
    let values = Object.values(expenses);
    
    let colors = [];
    labels.forEach(() => {
        colors.push(getRandomColor(colors)); // Generate unique random colors
    });

    new Chart(document.getElementById("expenseChart"), {
        type: "pie",
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: colors
            }]
        }
    });
}


function renderBudgetChart(budgets) {
    let labels = Object.keys(budgets);
    let spent = labels.map(category => budgets[category].spent);
    let limits = labels.map(category => budgets[category].budget_limit);

    new Chart(document.getElementById("budgetChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [
                { label: "Spent", data: spent, backgroundColor: "rgba(255, 99, 132, 0.6)" },
                { label: "Budget Limit", data: limits, backgroundColor: "rgba(54, 162, 235, 0.6)" }
            ]
        },
        options: { responsive: true }
    });
}

function renderGoalChart(goals) {
    let labels = Object.keys(goals);
    let saved = labels.map(goal => goals[goal].saved);
    let targets = labels.map(goal => goals[goal].target);

    new Chart(document.getElementById("goalChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [
                { label: "Saved", data: saved, backgroundColor: "rgba(75, 192, 192, 0.6)" },
                { label: "Target", data: targets, backgroundColor: "rgba(255, 159, 64, 0.6)" }
            ]
        },
        options: { responsive: true }
    });
}
