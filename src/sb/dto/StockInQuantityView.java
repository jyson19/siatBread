package sb.dto;

public class StockInQuantityView {

	private int stockId;
	private String name;
	private String expDate;
	private int quantity;

	public StockInQuantityView() {
		super();
	}

	public StockInQuantityView(int stockId, String name, String expDate, int quantity) {
		super();
		this.stockId = stockId;
		this.name = name;
		this.expDate = expDate;
		this.quantity = quantity;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
//		return "재고량은  [재고빵 ID : " + stockId 
//				+ ", 재고빵 : " + name 
//				+ ", 유통기한 : " + expDate 
//				+ ", 재고수량 : "				+ quantity + "]";
		StringBuilder builder = new StringBuilder();
		builder.append("StockInID : ");
		builder.append(stockId);
		builder.append(", 빵 이름 : ");
		builder.append(name);
		builder.append(", 유통기한 : ");
		builder.append(expDate);
		builder.append(", 수량 : ");
		builder.append(quantity);
		return builder.toString();
	}

}
