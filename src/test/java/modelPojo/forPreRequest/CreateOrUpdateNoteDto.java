package modelPojo.forPreRequest;

public class CreateOrUpdateNoteDto{
	private Long date;
	private Integer teamId;
	private Integer id;
	private String text;
	private Integer userId;

	public CreateOrUpdateNoteDto(Long date, Integer teamId, Integer id, String text, Integer userId) {
		this.date = date;
		this.teamId = teamId;
		this.id = id;
		this.text = text;
		this.userId = userId;
	}

	public void setDate(Long date){
		this.date = date;
	}

	public Long getDate(){
		return date;
	}

	public void setTeamId(Integer teamId){
		this.teamId = teamId;
	}

	public Integer getTeamId(){
		return teamId;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return userId;
	}
}
