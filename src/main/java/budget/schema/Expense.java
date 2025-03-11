package budget.schema;

public class Expense {
    private int id;
    private int userId;
    private String date;
    private String category;
    private double amount;
    private String description;
    private int categoryId;

    public Expense() {}

    public Expense(int userId, String date, String category, double amount, String description) {
        this.userId = userId;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    

	public Expense( int userId, String date, int categoryId, double amount, String description) {
		super();
		this.userId = userId;
		this.date = date;
		this.amount = amount;
		this.description = description;
		this.categoryId = categoryId;
	}

	public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
