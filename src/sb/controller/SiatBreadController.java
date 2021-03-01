package sb.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sb.dto.BreadDTO;
import sb.dto.OrderView;
import sb.model.SiatBreadService;
import sb.view.EndView;
import sb.view.FailView;
import sb.view.SuccessView;

public class SiatBreadController {
	private static SiatBreadController instance = new SiatBreadController();
	private SiatBreadService service = SiatBreadService.getInstance();

	public SiatBreadController() {
	}

	public static SiatBreadController getInstance() {
		return instance;
	}

	// 빵 데이터 삽입
	public void injectionDB() {
		service.DBInjection();
	}

	// 생산 가능한 빵 목록 조회
	public void getAllBread() {
		try {
			EndView.breadListView(service.getBreadAll());
			SuccessView.result("\n만들 수 있는 빵이 이렇게나 많습니다..\n");
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	// 신제품 등록
	public void makeNewBread(String breadName, int expDate, int price) {
		BreadDTO bread = new BreadDTO(breadName, expDate, price);
		try {
			service.newBread(bread);
			SuccessView.result("\n신 제품 등록이 완성되었습니다.\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 제품 키워드 검색
	public void searchBread(String keyword) {
		try {
			if (service.getBreadKeyword(keyword).size() != 0) {
				EndView.breadListView(service.getBreadKeyword(keyword));
			} else {
				FailView.failMessage("\n검색 결과, 값이 조회되지 않습니다.\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 제품 변경
	public void updateBread(int breadId, String breadName, int expDate, int price) {
		try {
			BreadDTO bread = service.getBreadId(breadId);
			if (bread != null) {
				service.breadUpdate(bread.getBreadId(), breadName, expDate, price);
				SuccessView.result("\n변경이 완료되었습니다.\n");
			} else {
				FailView.failMessage("\n변경하고자하는 대상이 없습니다.\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			FailView.failMessage("\n변경 실패..!\n");
		}
	}

	// 빵 등록 취소 - 여기까지 체크
	public void deleteBread(int breadId) {
		try {
			if (service.deleteBreadId(breadId) == true) {
				SuccessView.result("\n해당 제품의 데이터가 삭제되었습니다.\n");
			} else {
				FailView.failMessage("\n해당 제품이 없거나 잘못된 입력 값입니다.\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 빵 제작(BreadDTO -> StockIn)
	public void createBread(String breadName) {
		try {
			service.fromStockin(breadName);
			SuccessView.result("\n뜨끈~하고 든든~한 신선한 빵이 나왔습니다!\n");
		} catch (SQLException e) {
			e.printStackTrace();
			FailView.failMessage("\n빵 제작에 실패하였습니다. 해당 비용은 추후 청구하도로..ㄱ..\n");
		}
	}

	// 빵 폐기처리(StockIn -> StockOut, 3)
	public void disposalStockIn() {
		try {
			if (service.stockInDisposal() == true) {
				SuccessView.result("\n폐기처리가 완료되었습니다.\n");
			}
			FailView.failMessage("\n폐기처리 할 빵이 존재하지 않습니다.\n깔끔하시네요..\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 빵 재고 조회
	public void breadStockIn() {
		try {
			if (service.breadStock().size() != 0) {
				EndView.breadListView(service.breadStock());
				SuccessView.result("\n재고 조회 결과 입니다.\n");
			} else {
				SuccessView.result("\n음.. 텅비었네요.. ....  일 합시다.\n");
			}
		} catch (SQLException s) {
			s.printStackTrace();
			FailView.failMessage("\n있었는데요? 없습니다.. 아무튼 없습니다.\n");
		}
	}

	// 매출 장부 조회
	public void saleBooks() {
		try {
			int accountBook[] = service.getSaleBooks();
			if (accountBook[0] >= accountBook[1]) {
				EndView.saleBooksPlus(accountBook);
			} else {
				EndView.saleBooksMinus(accountBook);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////

	// 주문 객체 생성(OrderDTO, CustomerDTO)
	public void createOrder() {
		try {
			HashMap<String, Integer> orderList = service.orderCreate();
			SuccessView.result("\n손님이 방문했습니다.\n");
			EndView.orderListView(orderList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 주문 조회
	public void selectOrder() {
		try {
			if (service.orderSelect().size() != 0) {
				EndView.orderSelectAll(service.orderSelect());
//			service.orderSelect();
				SuccessView.result("\n주문이 밀렸네요.. 어서 일합시다.\n");
			} else {
				FailView.failMessage("\n손님이 안오시네요.. 주문이 안들어옵니다..\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 빵 판매
	public boolean sellBread() {
		try {
			if (service.orderSelect().size() != 0) {
				ArrayList<OrderView> list = service.orderSelect();
				String bname = list.get(0).getName();

				if (service.breadSell() == true) {
					SuccessView.result("\n주문하신 『" + bname + "』 나왔습니다 !\n");
					return true;
				} else {
					FailView.failMessage("\n해당 빵의 수량이 부족합니다.\n");
				}
			} else {
				FailView.failMessage("\n장사가 안됩니다.. 지금 손님이 없네요..\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 주문 확인
	public boolean orderCheck() {
		try {
			if (service.orderSelect().size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

}
