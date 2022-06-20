package modelPojo;

public class RestTasks{
	private Long jobEndDate;
	private Integer clientId;
	private Long jobStartDate;
	private Integer teamId;
	private Long customerAvailableEndDate;
	private String description;
	private Integer id;
	private Long customerAvailableStartDate;
	private Integer userId;

	public RestTasks(Long jobEndDate, Integer clientId, Long jobStartDate, Integer teamId,
					 Long customerAvailableEndDate, String description, Integer id,
					 Long customerAvailableStartDate, Integer userId)
	{
		this.jobEndDate = jobEndDate;
		this.clientId = clientId;
		this.jobStartDate = jobStartDate;
		this.teamId = teamId;
		this.customerAvailableEndDate = customerAvailableEndDate;
		this.description = description;
		this.id = id;
		this.customerAvailableStartDate = customerAvailableStartDate;
		this.userId = userId;
	}

	public void setJobEndDate(Long jobEndDate){
		this.jobEndDate = jobEndDate;
	}

	public Long getJobEndDate(){
		return jobEndDate;
	}

	public void setClientId(Integer clientId){
		this.clientId = clientId;
	}

	public Integer getClientId(){
		return clientId;
	}

	public void setJobStartDate(Long jobStartDate){
		this.jobStartDate = jobStartDate;
	}

	public Long getJobStartDate(){
		return jobStartDate;
	}

	public void setTeamId(Integer teamId){
		this.teamId = teamId;
	}

	public Integer getTeamId(){
		return teamId;
	}

	public void setCustomerAvailableEndDate(Long customerAvailableEndDate){
		this.customerAvailableEndDate = customerAvailableEndDate;
	}

	public Long getCustomerAvailableEndDate(){
		return customerAvailableEndDate;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setCustomerAvailableStartDate(Long customerAvailableStartDate){
		this.customerAvailableStartDate = customerAvailableStartDate;
	}

	public Long getCustomerAvailableStartDate(){
		return customerAvailableStartDate;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return userId;
	}
}
