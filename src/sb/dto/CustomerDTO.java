package sb.dto;

public class CustomerDTO {
	private int customerId; // pk
	private int priceSum; // 구매금액
	private String purDate; // 주문일
	
	// 동일한 customerId를 가지면
	// 주문횟수는 customerId를 기준으로 
	// index 중첩
	//
	// 주문 객체 생성시
	// 새로운 손님 -> 시퀀스로 customerId 생성
	// 기존 손님 -> 이미 생성한 customerId 사용
	//			 -> orderCount++;
	//
	// if. orderCount가 없으면
	// 새로운 손님-기존 손님 구분없이
	// 주문 객체만 생성함.
	//
	// then. customerId는 시퀀스로만 생성
	//
	// else if (새로운-기존 구분 없이)
	// 방문횟수 대신 구매금액 data 삽입시
	// 추후 매출액 추출 가능
	

	
	
	public CustomerDTO() {
	}

	public CustomerDTO(int priceSum, String purDate) {
		super();
		this.priceSum = priceSum;
		this.purDate = purDate;
	}

	public CustomerDTO(int customerId, int priceSum, String purDate) {
		super();
		this.customerId = customerId;
		this.priceSum = priceSum;
		this.purDate = purDate;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getPriceSum() {
		return priceSum;
	}

	public void setPriceSum(int priceSum) {
		this.priceSum = priceSum;
	}

	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}

	@Override
	public String toString() {
		return "CustomerDTO [customerId=" + customerId + ", priceSum=" + priceSum + ", purDate=" + purDate + "]";
	}
}
