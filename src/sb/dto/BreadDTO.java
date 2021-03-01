package sb.dto;

// 빵 DTO
public class BreadDTO {
	private int breadId; // Pk, 기본키, 빵 종류 별로 지정
	private String breadName; // 빵 이름
	private int expDate; // 유통기한, int 값 1 당 하루
	private int price; // 가격

	public BreadDTO() {
	}

	public BreadDTO(int breadId, String breadName, int expDate, int price) {
		super();
		this.breadId = breadId;
		this.breadName = breadName;
		this.expDate = expDate;
		this.price = price;
	}
	public BreadDTO(String breadName, int expDate, int price) {
		super();
		this.breadName = breadName;
		this.expDate = expDate;
		this.price = price;
	}

	public int getBreadId() {
		return breadId;
	}

	public void setBreadId(int breadId) {
		this.breadId = breadId;
	}

	public String getBreadName() {
		return breadName;
	}

	public void setBreadName(String breadName) {
		this.breadName = breadName;
	}

	public int getExpDate() {
		return expDate;
	}

	public void setExpDate(int expDate) {
		this.expDate = expDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BreadID : ");
		builder.append(breadId);
		builder.append(", 빵 이름 : ");
		builder.append(breadName);
		builder.append(", 유통기한 : ");
		builder.append(expDate + "일");
		builder.append(", 가격 : ");
		builder.append(price);
		return builder.toString();
	
	}
	
}