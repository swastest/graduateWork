package modelPojo;

public class RootRequestItem{
	private Long date;
	private Integer distance;
	private Integer teamId;
	private String name;
	private Long time;
	private Integer userId;

	public void setDate(Long date){
		this.date = date;
	}

	public Long getDate(){
		return date;
	}

	public void setDistance(Integer distance){
		this.distance = distance;
	}

	public Integer getDistance(){
		return distance;
	}

	public void setTeamId(Integer teamId){
		this.teamId = teamId;
	}

	public Integer getTeamId(){
		return teamId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTime(Long time){
		this.time = time;
	}

	public Long getTime(){
		return time;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return userId;
	}
}
