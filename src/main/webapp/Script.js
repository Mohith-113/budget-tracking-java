// Sidebar Toggle
function toggleSidebar() {
    var sidebar = document.getElementById("sidebar");
    var content = document.getElementById("main-content");

    sidebar.classList.toggle("collapsed");
    content.classList.toggle("collapsed");
}


// Open Expense Modal
function openExpenseModal() {
    document.getElementById("expenseModal").style.display = "block";
}

// Close Expense Modal
function closeExpenseModal() {
    document.getElementById("expenseModal").style.display = "none";
}

// Close modal when clicking outside
window.onclick = function(event) {
    let modal = document.getElementById("expenseModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

$(document).ready(function() {
    $("#category").on("input", function() {
        let categoryInput = $(this).val();
        let dataList = $("#categoryList");

        if (categoryInput.length > 0) {
            $.ajax({
                url: "FetchCategoriesServlet",
                type: "GET",
                data: { query: categoryInput },
                dataType: "json", // Ensures automatic JSON parsing
                success: function(response) {
                    console.log("Received categories:", response); // Debugging

                    dataList.empty(); // Clear existing options
                    response.forEach(function(category) {
                        let option = $("<option>").attr("value", category);
                        dataList.append(option);
                    });

                    console.log("Options appended:", dataList.html()); // Debugging
                },
                error: function(xhr, status, error) {
                    console.error("Error fetching categories:", error);
                }
            });
        } else {
            dataList.empty(); // Clear suggestions if input is empty
        }
    });
});


// Open Budget Modal
function openBudgetModal() {
    document.getElementById("budgetModal").style.display = "block";
}

// Close Expense Modal
function closeBudgetModal() {
    document.getElementById("budgetModal").style.display = "none";
}

// Close modal when clicking outside
window.onclick = function(event) {
    let modal = document.getElementById("budgetModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}


// Open Goal Modal
    function openGoalModal() {
        document.getElementById("goalModal").style.display = "block";
    }

    // Close Goal Modal
    function closeGoalModal() {
        document.getElementById("goalModal").style.display = "none";
    }

    // Open Amount Modal with specific goal details
    function openAmountModal(goalId, goalName) {
        document.getElementById("goal_id").value = goalId;
        document.getElementById("goal_name_display").value = goalName;
        document.getElementById("amountModal").style.display = "block";
    }

    // Close Amount Modal
    function closeAmountModal() {
        document.getElementById("amountModal").style.display = "none";
    }

    // Close modals when clicking outside them
    window.onclick = function(event) {
        let amountModal = document.getElementById("amountModal");
        let goalModal = document.getElementById("goalModal");
        
        if (event.target == amountModal) {
            closeAmountModal();
        }
        if (event.target == goalModal) {
            closeGoalModal();
        }
    };




