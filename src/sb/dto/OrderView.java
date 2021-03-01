package sb.dto;

public class OrderView {

	private int orderId; // 주문번호
	private int breadId; // 빵 번호
	private int customerId; // 고객번호
	private int orderQuantity; // 구매수량
	private int sellCheck; // 1 : 주문 대기중, 2 : 판매완료
	private String name; // 빵 이름

	public OrderView() {
	}

	public OrderView(int breadId, int customerId, int orderQuantity, int sellCheck, String name) {
		super();
		this.breadId = breadId;
		this.customerId = customerId;
		this.orderQuantity = orderQuantity;
		this.sellCheck = sellCheck;
		this.name = name;
	}
	public OrderView(int orderId, int breadId, int customerId, int orderQuantity, int sellCheck, String name) {
		super();
		this.orderId = orderId;
		this.breadId = breadId;
		this.customerId = customerId;
		this.orderQuantity = orderQuantity;
		this.sellCheck = sellCheck;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "OrderView [orderId=" + orderId + ", breadId=" + breadId + ", customerId=" + customerId
				+ ", orderQuantity=" + orderQuantity + ", sellCheck=" + sellCheck + ", name=" + name + "]";
	}
}
