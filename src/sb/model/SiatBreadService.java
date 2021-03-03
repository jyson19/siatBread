package sb.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sb.dto.BreadDTO;
import sb.dto.OrderView;
import sb.dto.StockInQuantityView;
import sb.exception.CustomException;

// DAO의 기능을 묶어
// Service를 통해 DAO에 접근함(중요)
public class SiatBreadService {

	private static SiatBreadService instance = new SiatBreadService();

	public SiatBreadService() {
	}

	public static SiatBreadService getInstance() {
		return instance;
	}

	// 빵 데이터 삽입
	public void DBInjection() {
		BreadDAO.injectionDB();
	}

	// 생산 가능한 빵 목록 조회
	public ArrayList<BreadDTO> getBreadAll() throws SQLException {
		try {
			if (BreadDAO.getAllBreadDAO().size() != 0) {
				return BreadDAO.getAllBreadDAO();
			} else {
				throw new CustomException("\n 현재 등록된 빵이 없습니다.");
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;

	}

	// 신제품 등록
	public boolean newBread(BreadDTO bread) throws SQLException {
		try {
			if (BreadDAO.newMakeBread(bread) == true) {
				return true;
			} else {
				throw new CustomException("신제품 등록 실패 !! . . .");
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 제품 키워드 검색
	public ArrayList<BreadDTO> getBreadKeyword(String keyword) throws SQLException {
		return BreadDAO.getBreadKeyword(keyword);
	}

	// ID로 제품 검색
	public BreadDTO getBreadId(int breadId) throws SQLException {
		return BreadDAO.getBreadId(breadId);
	}

	// 제품 변경
	public boolean breadUpdate(int breadId, String breadName, int expDate, int price) throws SQLException {
		return BreadDAO.breadUpdate(breadId, breadName, expDate, price);
	}

	// 빵 등록 취소
	public boolean deleteBreadId(int breadId) throws SQLException {
		return BreadDAO.breadDeleteId(breadId);
	}

	// 매출 장부 조회
	public int[] getSaleBooks() throws SQLException {
		int[] profitLoss = { getSalesProfit(), getSalesLoss() };
		return profitLoss;
	}

	// 매출 이익 조회
	public int getSalesProfit() throws SQLException {
		return StockInDAO.salesProfit();
	}

	// 총 매출 손실 조회
	public int getSalesLoss() throws SQLException {
		return StockInDAO.salesLoss();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	// 빵 제작(BreadDTO -> StockIn)
	public boolean fromStockin(String breadName) throws SQLException {
		if (getBreadKeyword(breadName).size() != 0) {
			return StockInDAO.breadCreate(getBreadKeyword(breadName));
		} else {
			return false;
		}
	}

	// 빵 폐기처리(StockIn -> StockOut, 3)
	public boolean stockInDisposal() throws SQLException {
		return StockInDAO.stockInDisposal();
	}

	// 재고 빵 조회
	public ArrayList<StockInQuantityView> breadStock() throws SQLException {
		return StockInDAO.breadStockInDAO();
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////

	// 주문 객체 생성 process
	// 1. customer 객체 생성
	// 2. order 객체 생성
	// 3. 랜덤 주문객체 생성(트랜잭션 처리)

	// customer 객체 생성
	public boolean customerCreate() throws SQLException {
		return CustomerDAO.customerCreate();
	}

	// order 객체 생성
	public HashMap<String, Integer> orderCreate() throws SQLException {
		boolean checkCustomer = customerCreate();
		if (checkCustomer == true) {
//			CustomerDAO.findCustomer();
			OrderDAO.orderCreate(CustomerDAO.findCustomer());
			return OrderDAO.findLastOrder();
		}
		return null;
	}

	// 주문 조회
	public ArrayList<OrderView> orderSelect() throws SQLException {
		return OrderDAO.orderSelectAll();
	}

	// 빵 판매
	public boolean breadSell() {

		try {
			if (OrderDAO.quantityCheck() != null) {
				// 조건1)
//			OrderDAO.quantityCheck(); // return list(int order_quan, int bread_id)
				List<Integer> qc = OrderDAO.quantityCheck();
				if (qc.size() != 0) {

					// 조건2) 재고 수량 조회
					// list(int order_quan, int bread_id, int sum(quantity))
					List<Integer> sc = OrderDAO.sumCheck(qc);
					if (sc.get(2) >= sc.get(0)) {
						// 조건3) sum(quantity) >= order_quan ? true : false
						OrderDAO.stockInRownum(sc);
						return true;
					}
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 판매한 빵의 이름 확인
	public BreadDTO getBreadName(int breadId) throws SQLException {
		return BreadDAO.getBreadId(breadId);
	}
}
