package sb.dto;

// 재고
public class StockInDTO extends BreadDTO {
	private int stockId; // pk
	private String prodDate; // 생산일자, int -> db date
	private int expDate; // 유통기한
	private int quantity; // 수량
	private String outDate; // 출고일
	private int category; // 1 : 판매가능, 2 : 판매됨, 3 : 환불, 4 : 폐기

	public StockInDTO() {
	}

	public StockInDTO(String prodDate, int expDate, int quantity, String outDate, int category) {
		super();
		this.prodDate = prodDate;
		this.expDate = expDate;
		this.quantity = quantity;
		this.outDate = outDate;
		this.category = category;
	}

	public StockInDTO(int stockId, String prodDate, int expDate, int quantity, String outDate, int category) {
		super();
		this.stockId = stockId;
		this.prodDate = prodDate;
		this.expDate = expDate;
		this.quantity = quantity;
		this.outDate = outDate;
		this.category = category;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getProdDate() {
		return prodDate;
	}

	public void setProdDate(String prodDate) {
		this.prodDate = prodDate;
	}

	public int getExpDate() {
		return expDate;
	}

	public void setExpDate(int expDate) {
		this.expDate = expDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "StockInDTO [stockId=" + stockId 
				+ ", prodDate=" + prodDate 
				+ ", expDate=" + expDate 
				+ ", quantity="+ quantity 
				+ ", outDate=" + outDate 
				+ ", category=" + category + "]";
	}
}
