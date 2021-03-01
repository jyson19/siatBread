package sb.dto;

// 주문 객체
public class OrderDTO {
	private int orderId; // 주문번호
//	private int customerId;
//	private int stockId;
//	private String purDate; // 주문일
	private int breadId; // 빵 번호
	private int customerId; // 고객번호
	private int orderQuantity; // 구매수량
	private int sellCheck; // 1 : 주문 대기중, 2 : 판매완료

	public OrderDTO() {
	}

	public OrderDTO(int breadId, int customerId, int orderQuantity, int sellCheck) {
		super();
		this.breadId = breadId;
		this.customerId = customerId;
		this.orderQuantity = orderQuantity;
		this.sellCheck = sellCheck;
	}

	public OrderDTO(int orderId, int breadId, int customerId, int orderQuantity, int sellCheck) {
		super();
		this.orderId = orderId;
		this.breadId = breadId;
		this.customerId = customerId;
		this.orderQuantity = orderQuantity;
		this.sellCheck = sellCheck;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getBreadId() {
		return breadId;
	}

	public void setBreadId(int breadId) {
		this.breadId = breadId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public int getSellCheck() {
		return sellCheck;
	}

	public void setSellCheck(int sellCheck) {
		this.sellCheck = sellCheck;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderId=" + orderId + ", breadId=" + breadId + ", customerId=" + customerId
				+ ", orderQuantity=" + orderQuantity + ", sellCheck=" + sellCheck + "]";
	}
}
