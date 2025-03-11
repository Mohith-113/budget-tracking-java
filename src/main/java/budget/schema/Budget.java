package budget.schema;

public class Budget {
	private int budget_id;
	private int userId;
	private int month;
	private int year;
	private double amount;
	private String category;
	

	public Budget( int userId, int month, int year, double amount, String category) {
		super();
		this.userId = userId;
		this.month = month;
		this.year = year;
		this.amount = amount;
		this.category = category;
	}


	public Budget() {
		// TODO Auto-generated constructor stub
	}


	public int getBudget_id() {
		return budget_id;
	}


	public void setBudget_id(int budget_id) {
		this.budget_id = budget_id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
