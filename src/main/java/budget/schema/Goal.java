package budget.schema;

public class Goal {
	private int goal_id;
	private int user_id;
	private String goal_name;
	private Double target_amount;
	private Double saving_amount;
	private String start_date;
	private String end_date;
	private String status;
	
	
	
	public Goal( int user_id, int goal_id, String goal_name, Double target_amount, Double saving_amount,
			String start_date, String end_date, String status) {
		super();
		this.goal_id = goal_id;
		this.user_id = user_id;
		this.goal_name = goal_name;
		this.target_amount = target_amount;
		this.saving_amount = saving_amount;
		this.start_date = start_date;
		this.end_date = end_date;
		this.status = status;
	}
	
	
	
	



	public Goal(int user_id, String goal_name, Double target_amount, Double saving_amount, String start_date,
			String end_date, String status) {
		super();
		this.user_id = user_id;
		this.goal_name = goal_name;
		this.target_amount = target_amount;
		this.saving_amount = saving_amount;
		this.start_date = start_date;
		this.end_date = end_date;
		this.status = status;
	}







	public Goal() {
		// TODO Auto-generated constructor stub
	}







	public Goal(int user_id, int goal_id, Double saving_amount) {
		
		super();
		this.user_id = user_id;
		this.goal_id = goal_id;
		this.saving_amount = saving_amount;
	}







	public int getGoal_id() {
		return goal_id;
	}
	public void setGoal_id(int goal_id) {
		this.goal_id = goal_id;
	}
	public int getUserId() {
		return user_id;
	}
	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	public String getGoal_name() {
		return goal_name;
	}
	public void setGoal_name(String goal_name) {
		this.goal_name = goal_name;
	}
	public Double getTarget_amount() {
		return target_amount;
	}
	public void setTarget_amount(Double target_amount) {
		this.target_amount = target_amount;
	}
	public Double getSaving_amount() {
		return saving_amount;
	}
	public void setSaving_amount(Double saving_amount) {
		this.saving_amount = saving_amount;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
