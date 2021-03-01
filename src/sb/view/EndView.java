package sb.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import sb.dto.BreadDTO;
import sb.dto.OrderDTO;
import sb.dto.OrderView;

public class EndView {

	// 제품 검색
	public static void breadListView(ArrayList allBread) {
		if (allBread.size() != 0) {
			for (int idx = 0; idx < allBread.size(); idx++) {
				System.out.println(allBread.get(idx));
			}
		}

	}

	// 신제품 등록
	public static void newBreadView(BreadDTO newBread) {
		System.out.println(newBread);
	}

	// 제품 변경
	public static void updateBreadView(BreadDTO breadId) {

	}

	// 주문한 음식 조회
	public static void orderListView(HashMap<String, Integer> orderList) {
		for (Entry<String, Integer> eo : orderList.entrySet()) {
			System.out.println("『" + eo.getKey() + "』 " + eo.getValue() + "개 주세요!");
		}

//		System.out.println(orderList.);
	}

	public static void orderSelectAll(ArrayList<OrderView> orderSelect) {
		for (int i = 0; i < orderSelect.size(); i++) {
			System.out.println("\n" + orderSelect.get(i).getOrderId() + "번 손님 『" + orderSelect.get(i).getName()+ "』 " + orderSelect.get(i).getOrderQuantity()+ "개 !");
		}
	}


	// 매출 장부 조회(이익)
	public static void saleBooksPlus(int[] accountBook) {
		System.out.println("\n매출액 : " + accountBook[0] + "원");
		System.out.println("매출손실 : " + accountBook[1] + "원");
		System.out.println("\n지금까지 " + (accountBook[0] - accountBook[1]) + "원 벌었네요.\n");
	}

	// 매출 장부 조회(손실)
	public static void saleBooksMinus(int[] accountBook) {
		System.out.println("\n매출액 : " + accountBook[0] + "원");
		System.out.println("매출손실 : " + accountBook[1] + "원");
		System.out.println("\n돈을 벌기는 커녕 " + (accountBook[1] - accountBook[0]) + "원이 증발했어요.. 제대로 일하고 계신가요?\n");		
	}
}
