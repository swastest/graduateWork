package modelPojo;

public class PaymentItem {
	private Integer amount;
	private String type;

	public PaymentItem(Integer amount, String type) {
		this.amount = amount;
		this.type = type;
	}

	public void setAmount(Integer amount){
		this.amount = amount;
	}

	public Integer getAmount(){
		return amount;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}
